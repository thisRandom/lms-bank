package cn.edu.sjziei.lms.mapper;

import cn.edu.sjziei.lms.dto.ReportLocationDto;
import cn.edu.sjziei.lms.vo.DispatchToIdVo;
import cn.edu.sjziei.lms.vo.LocationTrackVo;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface LocationsMapper {
    @Insert("INSERT INTO dis_location_log (dispatch_id, latitude, longitude, location, record_time) " +
            "VALUES (#{dispatchId}, #{latitude}, #{longitude}, #{location},  NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void reportLocation(ReportLocationDto dto);

    @Select("SELECT driver_id,order_id FROM dis_dispatch WHERE id = #{dispatchId}")
    DispatchToIdVo getDriverIdByDispatchId(Long dispatchId);

    @Select("SELECT id, dispatch_id, latitude, longitude, location, record_time FROM dis_location_log WHERE dispatch_id = #{dispatchId} ORDER BY record_time ASC")
    List<LocationTrackVo> getLocationsByDispatchId(Long dispatchId);

    @Select("select customer_id from ord_order where id=#{orderId}")
    Long getOrderToUserId(Long orderId);

    @Select("select vehicle_id from dis_dispatch where id=#{driverId}")
    Long selectVehId(Long driverId);

    @Update("update veh_vehicle set last_location=#{location} where id=#{vehId}")
    void updateVehLastLocation(@Param("vehId") Long vehId,@Param("location") String location);

    @Update("update dis_dispatch set current_location=#{location} where id=#{id}")
    void updateDiscurrentLocation(@Param("id")Long id,@Param("location") String location);
    @Select("select status from dis_dispatch where id=#{dispatchId}")
    String getOrderStatusById(@Param("dispatchId") Long dispatchId);
}