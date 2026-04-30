package cn.edu.sjziei.lms.mapper;

import cn.edu.sjziei.lms.dto.GetOrderDto;
import cn.edu.sjziei.lms.vo.RecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("<script>" +
            "SELECT " +
            "  id, order_no, shipper_name, shipper_phone, shipper_address, " +
            "  receiver_name, receiver_phone, receiver_address, goods_type, " +
            "  weight, volume, status, dispatch_id, create_time " +
            "FROM ord_order " +
            "<where>" +
            "  <if test='orderNo != null and orderNo != \"\"'>" +
            "    AND order_no = #{orderNo}" +
            "  </if>" +
            "  <if test='status != null and status != \"\"'>" +
            "    AND status = #{status}" +
            "  </if>" +
            "  <if test='customerId != null'>" +
            "    AND customer_id = #{customerId}" +
            "  </if>" +
            "  <if test='startDate != null and startDate != \"\"'>" +
            "    AND create_time &gt;= #{startDate}" +
            "  </if>" +
            "  <if test='endDate != null and endDate != \"\"'>" +
            "    AND create_time &lt;= #{endDate}" +
            "  </if>" +
            "</where>" +
            "ORDER BY create_time DESC" +
            "</script>")
    public List<RecordVo> getOrderList(GetOrderDto getOrderDto) ;
}
