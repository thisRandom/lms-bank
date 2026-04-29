package cn.edu.sjziei.lms.mapper;

import cn.edu.sjziei.lms.dto.AddVehicleDto;
import cn.edu.sjziei.lms.dto.GetVehicleListDto;
import cn.edu.sjziei.lms.vo.VehicleVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VehicleMapper {
    @Select("<script>" +
            "SELECT " +
            "    v.id, " +
            "    v.plate_number AS plateNumber, " +
            "    v.vehicle_type AS vehicleType, " +
            "    v.load_capacity AS loadCapacity, " +
            "    v.driver_id AS driverId, " +
            "    u.real_name AS driverName, " +
            "    u.phone AS driverPhone, " +
            "    v.status, " +
            "    v.last_location AS lastLocation, " +
            "    v.last_update_time AS lastUpdateTime " +
            "FROM veh_vehicle v " +
            "LEFT JOIN sys_user u ON v.driver_id = u.id " +
            "<where> " +
            "    <if test='status != null and status != \"\"'> " +
            "        AND v.status = #{status} " +
            "    </if> " +
            "    <if test='vehicleType != null and vehicleType != \"\"'> " +
            "        AND v.vehicle_type = #{vehicleType} " +
            "    </if> " +
            "    <if test='keyword != null and keyword != \"\"'> " +
            "        AND (v.plate_number LIKE CONCAT('%', #{keyword}, '%') " +
            "             OR u.real_name LIKE CONCAT('%', #{keyword}, '%')) " +
            "    </if> " +
            "</where>" +
            "</script>")
    List<VehicleVo> getVehicleList(GetVehicleListDto getVehicleListDto);

    /**
     * 用于查询数据库中是否有一样的车牌
     * */
    @Select("SELECT COUNT(*) FROM veh_vehicle WHERE plate_number = #{plateNumber}")
    Integer countByPlateNumber(String plateNumber);

    /**
     * 增加车辆
     * */
    @Insert("INSERT INTO veh_vehicle (driver_id, plate_number, vehicle_type, load_capacity, status, create_time, update_time) " +
            "VALUES (#{driverId}, #{plateNumber}, #{vehicleType}, #{loadCapacity}, #{status}, NOW(), NOW())")
    void addVehicle(AddVehicleDto addVehicleDto);

    /**
     * 通过车牌查id
     * */
    @Select("SELECT id FROM veh_vehicle WHERE plate_number = #{plateNumber} LIMIT 1")
    Long plateNumberToId(String plateNumber);
}
