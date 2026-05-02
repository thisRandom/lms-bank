package cn.edu.sjziei.lms.mapper;

import cn.edu.sjziei.lms.dto.CreateOrderDto;
import cn.edu.sjziei.lms.dto.GetOrderDto;
import cn.edu.sjziei.lms.dto.UpdateOrderDto;
import cn.edu.sjziei.lms.vo.DispatchVo;
import cn.edu.sjziei.lms.vo.GetRecordVo;
import cn.edu.sjziei.lms.vo.OrderDetailsVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("<script>" +
            "SELECT " +
            "  id, order_no, shipper_name, shipper_phone, shipper_address, " +
            "  receiver_name, receiver_phone, receiver_address, goods_type, " +
            "  weight, volume, status,  create_time " +
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
    public List<GetRecordVo> getOrderList(GetOrderDto getOrderDto);

    @Insert("INSERT INTO ord_order (order_no, shipper_name, shipper_phone, shipper_address, " +
            "receiver_name, receiver_phone, receiver_address, goods_type, weight, volume, remark, customer_id, status) " +
            "VALUES (#{orderNo}, #{shipperName}, #{shipperPhone}, #{shipperAddress}, " +
            "#{receiverName}, #{receiverPhone}, #{receiverAddress}, #{goodsType}, #{weight}, #{volume}, #{remark}, #{customerId}, 'PENDING')")
    public void createOrder(CreateOrderDto createOrderDto);

    @Select("SELECT order_no FROM ord_order WHERE order_no LIKE CONCAT('ORD', #{date}, '%') ORDER BY order_no DESC LIMIT 1")
    public String getMaxOrderNoByDate(String date);

    @Select("SELECT LAST_INSERT_ID()")
    public Long getLastInsertId();

    @Select("SELECT status FROM ord_order WHERE id = #{id}")
    public String getOrderStatusById(Long id);

    @Select("SELECT customer_id FROM ord_order WHERE id = #{id}")
    public Long getOrderCustomerIdById(Long id);

    @Update("<script>" +
            "UPDATE ord_order " +
            "<set>" +
            "  <if test='shipperName != null and shipperName != \"\"'>shipper_name = #{shipperName},</if>" +
            "  <if test='shipperPhone != null and shipperPhone != \"\"'>shipper_phone = #{shipperPhone},</if>" +
            "  <if test='shipperAddress != null and shipperAddress != \"\"'>shipper_address = #{shipperAddress},</if>" +
            "  <if test='receiverName != null and receiverName != \"\"'>receiver_name = #{receiverName},</if>" +
            "  <if test='receiverPhone != null and receiverPhone != \"\"'>receiver_phone = #{receiverPhone},</if>" +
            "  <if test='receiverAddress != null and receiverAddress != \"\"'>receiver_address = #{receiverAddress},</if>" +
            "  <if test='goodsType != null and goodsType != \"\"'>goods_type = #{goodsType},</if>" +
            "  <if test='weight != null'>weight = #{weight},</if>" +
            "  <if test='volume != null'>volume = #{volume},</if>" +
            "  <if test='remark != null'>remark = #{remark},</if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    public void updateOrder(UpdateOrderDto updateOrderDto);

    @Update("UPDATE ord_order SET status = #{status} WHERE id = #{id}")
    public void updateOrderStatus(@Param("id") Long id, @Param("status") String status);

    @Select("select * from ord_order where id=#{id}")
    public OrderDetailsVo orderDetails(Long id);

    @Select("SELECT " +
            "d.id, " +
            "d.dispatch_no AS dispatchNo, " +
            "v.plate_number AS plateNumber, " +
            "u.real_name AS driverName, " +
            "u.phone AS driverPhone, " +
            "d.status, " +
            "d.current_location AS currentLocation " +
            "FROM dis_dispatch d " +
            "LEFT JOIN `ord_order` o ON d.order_id = o.id " +
            "LEFT JOIN veh_vehicle v ON d.vehicle_id = v.id " +
            "LEFT JOIN sys_user u ON d.driver_id = u.id " +
            "WHERE order_id=#{orderId}")
    public DispatchVo orderIdToDispatch(Long orderId);

    @Select("select driver_id from dis_dispatch where order_id=#{dispatchId}")
    public Long orderIdToDispatchToDriver(Long dispatchId);

    @Select("select customer_id from ord_order where id=#{id}")
    public Long customerIdByOrderId(Long id);
}
