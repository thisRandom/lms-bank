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
}