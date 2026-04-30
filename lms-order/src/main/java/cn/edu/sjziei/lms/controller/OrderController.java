package cn.edu.sjziei.lms.controller;

import cn.edu.sjziei.lms.dto.GetOrderDto;
import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 订单模块
 * */
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    /**
     * 获取列表
     * */
    @GetMapping
    public Result getOrderList(GetOrderDto getOrderDto,@RequestHeader("Authorization") String token){
        return orderService.getOrderList(getOrderDto,token);
    }


}
