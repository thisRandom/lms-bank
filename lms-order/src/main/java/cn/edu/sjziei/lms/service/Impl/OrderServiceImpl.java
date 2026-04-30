package cn.edu.sjziei.lms.service.Impl;

import cn.edu.sjziei.lms.dto.GetOrderDto;
import cn.edu.sjziei.lms.mapper.OrderMapper;
import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.service.OrderService;
import cn.edu.sjziei.lms.util.TokenUtil;
import cn.edu.sjziei.lms.vo.GetOrderVo;
import cn.edu.sjziei.lms.vo.LoginVo;
import cn.edu.sjziei.lms.vo.RecordVo;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    TokenUtil tokenUtil;
    public Result getOrderList(GetOrderDto getOrderDto, String token) {
        //分权
        LoginVo loginVo = tokenUtil.analysisToken(token);
        String role = loginVo.getRole();
        if(StrUtil.equals("DRIVER",role)){
            //这里写逻辑把司机的id查出来
        } else if (StrUtil.equals("CUSTOMER",role)) {
            getOrderDto.setCustomerId(loginVo.getId());
        }

        //查询数据
        PageHelper.startPage(getOrderDto.getPage(),getOrderDto.getSize());
        List<RecordVo> list = orderMapper.getOrderList(getOrderDto);
        PageInfo<RecordVo> info = new PageInfo<>(list);
        return Result.success(200,new GetOrderVo(info.getTotal(),info.getPages(),info.getPageNum(),list));
    }
}
