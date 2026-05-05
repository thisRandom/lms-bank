package cn.edu.sjziei.lms.controller;

import cn.edu.sjziei.lms.annotation.RequiresPermissions;
import cn.edu.sjziei.lms.dto.CreateOrderDto;
import cn.edu.sjziei.lms.dto.GetOrderDto;
import cn.edu.sjziei.lms.dto.UpdateOrderDto;
import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单模块
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    /**
     * 获取列表
     */
    @GetMapping
    @RequiresPermissions({"ADMIN", "CUSTOMER","DISPATCHER"})
    public Result getOrderList(GetOrderDto getOrderDto, @RequestHeader("Authorization") String token) {
        return orderService.getOrderList(getOrderDto, token);
    }

    /**
     * 创建订单
     *
     */
    @PostMapping
    @RequiresPermissions({"ADMIN", "CUSTOMER"})
    public Result createOrder(@RequestBody CreateOrderDto createOrderDto, @RequestHeader("Authorization") String token) {
        return orderService.createOrder(createOrderDto, token);
    }

    /**
     * 编辑订单
     *
     */
    @PutMapping("/{id}")
    @RequiresPermissions({"ADMIN", "DISPATCHER", "CUSTOMER"})
    public Result updateOrder(@PathVariable("id") Long id, @RequestBody UpdateOrderDto updateOrderDto, @RequestHeader("Authorization") String token) {
        updateOrderDto.setId(id);
        return orderService.updateOrder(updateOrderDto, token);
    }

    /**
     * 取消订单
     *
     */
    @PutMapping("/{id}/cancel")
    @RequiresPermissions({"ADMIN", "DISPATCHER", "CUSTOMER"})
    public Result cancelOrder(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        return orderService.cancelOrder(id, token);
    }

    /**
     * 订单详情
     * */
    @GetMapping("/{id}")
    public Result orderDetails(@PathVariable("id") Long id, @RequestHeader("Authorization") String token){
        return orderService.orderDetails(id,token);
    }
}
