package cn.edu.sjziei.lms.service;

import cn.edu.sjziei.lms.dto.CreateOrderDto;
import cn.edu.sjziei.lms.dto.GetOrderDto;
import cn.edu.sjziei.lms.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    public Result getOrderList(GetOrderDto getOrderDto, String token);

    public Result createOrder(CreateOrderDto createOrderDto, String token);
}
