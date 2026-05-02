package cn.edu.sjziei.lms.mapper;

import cn.edu.sjziei.lms.dto.CreateDispatchDto;
import cn.edu.sjziei.lms.dto.GetDispatchListDto;
import cn.edu.sjziei.lms.vo.DisPatchListVo;
import cn.edu.sjziei.lms.vo.GetOrderByIdVo;
import cn.edu.sjziei.lms.vo.GetVehicleByIdVo;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DispatchMapper {
    @Select("<script>" +
            "SELECT " +
            "dd.id, " +
            "dd.dispatch_no, " +
            "dd.status, " +
            "dd.current_location, " +
            "dd.estimated_departure_time, " +
            "dd.estimated_arrival_time, " +
            "o.order_no, " +
            "v.plate_number, " +
            "u.real_name as driver_name " +
            "FROM dis_dispatch dd " +
            "LEFT JOIN ord_order o ON dd.order_id = o.id " +
            "LEFT JOIN veh_vehicle v ON dd.vehicle_id = v.id " +
            "LEFT JOIN sys_user u ON dd.driver_id = u.id " +
            "WHERE 1=1 " +
            "<if test='dispatchNo != null and dispatchNo != \"\"'>" +
            " AND dd.dispatch_no = #{dispatchNo}" +
            "</if>" +
            "<if test='status != null and status != \"\"'>" +
            " AND dd.status = #{status}" +
            "</if>" +
            "<if test='driverId != null'>" +
            " AND dd.driver_id = #{driverId}" +
            "</if>" +
            "</script>")
    List<DisPatchListVo> getDispatchList(GetDispatchListDto getDispatchListDto);

    @Select("SELECT weight,volume,status FROM `ord_order`where id=#{orderId}")
    GetOrderByIdVo getOrderById(Long orderId);

    @Select("SELECT load_capacity,volume,driver_id,status FROM `veh_vehicle` where id=#{vehicleId}")
    GetVehicleByIdVo getVehicleById(Long vehicleId);

    /**
     * 存入数据库
     * */
    @Insert("INSERT INTO dis_dispatch (order_id, vehicle_id, driver_id, " +
            "estimated_departure_time, estimated_arrival_time, remark, " +
            "dispatch_no, status, create_time, update_time) " +
            "VALUES (#{orderId}, #{vehicleId}, #{driverId}, " +
            "#{estimatedDepartureTime}, #{estimatedArrivalTime}, #{remark}, " +
            "#{dispatchNo}, #{status}, NOW(), NOW())")
    void createDispatch(CreateDispatchDto createDispatchDto);

    /**
     * 反查id
     * */
    @Select("SELECT id FROM dis_dispatch WHERE dispatch_no = #{dispatchNo}")
    Long getIdByDispatchNo(@Param("dispatchNo") String dispatchNo);

    @Select("SELECT dispatch_no FROM dis_dispatch WHERE dispatch_no LIKE CONCAT('DIS', #{date}, '%') ORDER BY dispatch_no DESC LIMIT 1")
    String getMaxNoByDate(String dateStr);

    @Update("UPDATE ord_order SET status = #{status}, update_time = NOW() WHERE id = #{orderId}")
    void changeOrderStatus( @Param("orderId") Long orderId, @Param("status") String status);
}
