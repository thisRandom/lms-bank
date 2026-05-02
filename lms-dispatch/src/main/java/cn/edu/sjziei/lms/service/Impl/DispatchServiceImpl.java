package cn.edu.sjziei.lms.service.Impl;

import cn.edu.sjziei.lms.dto.CreateDispatchDto;
import cn.edu.sjziei.lms.dto.GetDispatchListDto;
import cn.edu.sjziei.lms.mapper.DispatchMapper;
import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.service.DispatchService;
import cn.edu.sjziei.lms.util.DispatchUtil;
import cn.edu.sjziei.lms.util.TokenUtil;
import cn.edu.sjziei.lms.vo.*;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Objects;

@Service
public class DispatchServiceImpl implements DispatchService {
    @Autowired
    DispatchMapper dispatchMapper;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    DispatchUtil dispatchUtil;

    @Override
    public Result getDispatchList(GetDispatchListDto getDispatchListDto,  String token) {
        getDispatchListDto.setPage(getDispatchListDto.getPage()==null?1:getDispatchListDto.getPage());
        getDispatchListDto.setSize(getDispatchListDto.getSize()==null?10:getDispatchListDto.getSize());

        LoginVo loginVo = tokenUtil.analysisToken(token);
        String role = loginVo.getRole();
        if(StrUtil.equals("DRIVER",role)){
            //把司机id传进去
            getDispatchListDto.setDriverId(loginVo.getId());
        }

        //查询数据
        PageHelper.startPage(getDispatchListDto.getPage(),getDispatchListDto.getSize());
        List<DisPatchListVo> list = dispatchMapper.getDispatchList(getDispatchListDto);
        PageInfo<DisPatchListVo> info = new PageInfo<>(list);
        return Result.success(200,new GetDisPatchListVo(info.getTotal(),info.getPages(),info.getPageNum(),list));
    }

    @Override
    @Transactional //事务
    public Result createDispatch(CreateDispatchDto createDispatchDto) {
        //先检验订单id是否存在，并且得到载重和体积
        GetOrderByIdVo orderById = dispatchMapper.getOrderById(createDispatchDto.getOrderId());
        if (orderById.getVolume()==null) return Result.error(400,"订单不存在");
        if(!"PENDING".equals(orderById.getStatus())) return Result.error(400,"订单异常");
        //车辆id是否存在车辆不在，并且绑定的司机id是否与司机id一致
        GetVehicleByIdVo vehicleById = dispatchMapper.getVehicleById(createDispatchDto.getVehicleId());
        if(vehicleById.getDriverId()==null) return Result.error(400,"车辆不存在");
        if (!Objects.equals(createDispatchDto.getDriverId(), vehicleById.getDriverId())) return Result.error(400, "车辆不属于该司机");
        if(!StrUtil.equals("IDLE",vehicleById.getStatus())||vehicleById.getVolume()< orderById.getVolume()||vehicleById.getLoadCapacity()<orderById.getWeight()) return Result.error(400,"该车不能运输此订单");

        //调度编号
        String no = dispatchUtil.generateNo();
        createDispatchDto.setDispatchNo(no);
        createDispatchDto.setStatus("ASSIGNED");
        //存入数据库
        dispatchMapper.createDispatch(createDispatchDto);
        //查id
        Long id = dispatchMapper.getIdByDispatchNo(no);
        //改订单状态
        dispatchMapper.changeOrderStatus(createDispatchDto.getOrderId(),"DISPATCHED");

        return Result.success(200,new CreateDispatchVo(id,no));
    }
}
