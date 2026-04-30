package cn.edu.sjziei.lms.mapper;

import cn.edu.sjziei.lms.dto.CreateOrderDto;
import cn.edu.sjziei.lms.dto.GetOrderDto;
import cn.edu.sjziei.lms.vo.RecordVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Options;

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
    public List<RecordVo> getOrderList(GetOrderDto getOrderDto);

    @Insert("INSERT INTO ord_order (order_no, shipper_name, shipper_phone, shipper_address, " +
            "receiver_name, receiver_phone, receiver_address, goods_type, weight, volume, remark, customer_id, status) " +
            "VALUES (#{orderNo}, #{shipperName}, #{shipperPhone}, #{shipperAddress}, " +
            "#{receiverName}, #{receiverPhone}, #{receiverAddress}, #{goodsType}, #{weight}, #{volume}, #{remark}, #{customerId}, 'PENDING')")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void createOrder(CreateOrderDto createOrderDto, String orderNo, Long customerId);

    @Select("SELECT order_no FROM ord_order WHERE order_no LIKE CONCAT('ORD', #{date}, '%') ORDER BY order_no DESC LIMIT 1")
    public String getMaxOrderNoByDate(String date);

    @Select("SELECT LAST_INSERT_ID()")
    public Long getLastInsertId();
}
