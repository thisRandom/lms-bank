package cn.edu.sjziei.lms.service.Impl;

import cn.edu.sjziei.lms.dto.CreateOrderDto;
import cn.edu.sjziei.lms.dto.GetOrderDto;
import cn.edu.sjziei.lms.dto.UpdateOrderDto;
import cn.edu.sjziei.lms.mapper.OrderMapper;
import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.service.OrderService;
import cn.edu.sjziei.lms.util.OrderNoUtil;
import cn.edu.sjziei.lms.util.TokenUtil;
import cn.edu.sjziei.lms.vo.CreateOrderVo;
import cn.edu.sjziei.lms.vo.GetOrderVo;
import cn.edu.sjziei.lms.vo.LoginVo;
import cn.edu.sjziei.lms.vo.GetRecordVo;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    OrderNoUtil orderNoUtil;

    public Result getOrderList(GetOrderDto getOrderDto, String token) {
        getOrderDto.setPage(getOrderDto.getPage()==null?1:getOrderDto.getPage());
        getOrderDto.setSize(getOrderDto.getSize()==null?10:getOrderDto.getSize());

        //分权
        LoginVo loginVo = tokenUtil.analysisToken(token);
        String role = loginVo.getRole();
        if (StrUtil.equals("CUSTOMER",role)) {
            getOrderDto.setCustomerId(loginVo.getId());
        }

        //查询数据
        PageHelper.startPage(getOrderDto.getPage(),getOrderDto.getSize());
        List<GetRecordVo> list = orderMapper.getOrderList(getOrderDto);
        PageInfo<GetRecordVo> info = new PageInfo<>(list);
        return Result.success(200,new GetOrderVo(info.getTotal(),info.getPages(),info.getPageNum(),list));
    }

    @Transactional
    public Result createOrder(CreateOrderDto createOrderDto, String token) {
        // 参数校验
        if (!orderNoUtil.inspectionP(createOrderDto.getShipperPhone())) {
            return Result.error(400, "发货人手机号格式不正确");
        }
        if (!orderNoUtil.inspectionP(createOrderDto.getReceiverPhone())) {
            return Result.error(400, "收货人手机号格式不正确");
        }
        if (createOrderDto.getWeight() == null || createOrderDto.getWeight().doubleValue() <= 0) {
            return Result.error(400, "重量必须大于0");
        }
        if (createOrderDto.getVolume() != null && createOrderDto.getVolume().doubleValue() <= 0) {
            return Result.error(400, "体积必须大于0");
        }

        LoginVo loginVo = tokenUtil.analysisToken(token);
        String role = loginVo.getRole();

        // 生成订单号: ORD + yyyyMMdd + 4位序号
        String orderNo = orderNoUtil.generateOrderNo();

        // 根据角色确定客户ID
        Long customerId;
        if (StrUtil.equals("CUSTOMER", role)) {
            customerId = loginVo.getId();
        } else {
            customerId = createOrderDto.getCustomerId();
        }

        // 设置订单号和客户ID
        createOrderDto.setOrderNo(orderNo);
        createOrderDto.setCustomerId(customerId);

        // 插入订单
        orderMapper.createOrder(createOrderDto);

        // 获取刚插入的订单ID
        Long id = orderMapper.getLastInsertId();

        return Result.success(200, new CreateOrderVo(id, orderNo));
    }

    public Result updateOrder(UpdateOrderDto updateOrderDto, String token) {
        // 参数校验
        if (StrUtil.isNotEmpty(updateOrderDto.getShipperPhone()) &&
                !orderNoUtil.inspectionP(updateOrderDto.getShipperPhone())) {
            return Result.error(400, "发货人手机号格式不正确");
        }
        if (StrUtil.isNotEmpty(updateOrderDto.getReceiverPhone()) &&
                !orderNoUtil.inspectionP(updateOrderDto.getReceiverPhone())) {
            return Result.error(400, "收货人手机号格式不正确");
        }
        if (updateOrderDto.getWeight() != null && updateOrderDto.getWeight().doubleValue() <= 0) {
            return Result.error(400, "重量必须大于0");
        }
        if (updateOrderDto.getVolume() != null && updateOrderDto.getVolume().doubleValue() <= 0) {
            return Result.error(400, "体积必须大于0");
        }

        LoginVo loginVo = tokenUtil.analysisToken(token);
        String role = loginVo.getRole();
        Long orderId = updateOrderDto.getId();

        // 获取订单状态和客户ID
        String status = orderMapper.getOrderStatusById(orderId);
        Long orderCustomerId = orderMapper.getOrderCustomerIdById(orderId);

        // 权限校验
        if (StrUtil.equals("CUSTOMER", role)) {
            // 客户只能编辑自己的订单
            if (!loginVo.getId().equals(orderCustomerId)) {
                return Result.error(403, "无权限编辑此订单");
            }
            // 客户只能编辑PENDING状态的订单
            if (!StrUtil.equals("PENDING", status)) {
                return Result.error(400, "只有待调度状态的订单可以编辑");
            }
        }

        // 更新订单
        orderMapper.updateOrder(updateOrderDto);

        return Result.success(200, null);
    }

    public Result cancelOrder(Long id, String token) {
        LoginVo loginVo = tokenUtil.analysisToken(token);
        String role = loginVo.getRole();

        // 获取订单状态和客户ID
        String status = orderMapper.getOrderStatusById(id);
        Long orderCustomerId = orderMapper.getOrderCustomerIdById(id);

        // 客户只能取消自己的订单
        if (StrUtil.equals("CUSTOMER", role)) {
            if (!loginVo.getId().equals(orderCustomerId)) {
                return Result.error(403, "无权限取消此订单");
            }
        }

        // 根据状态判断能否取消
        if (StrUtil.equals("PENDING", status)) {
            orderMapper.updateOrderStatus(id, "CANCELLED");
            return Result.success(200, null);
        } else if (StrUtil.equals("DISPATCHED", status)) {
            return Result.error(400, "该订单已调度，请联系调度员处理取消");
        } else if (StrUtil.equals("IN_TRANSIT", status)) {
            return Result.error(400, "该订单运输中，请联系调度员处理取消");
        }

        return Result.error(400, "无法取消此订单");
    }
}
