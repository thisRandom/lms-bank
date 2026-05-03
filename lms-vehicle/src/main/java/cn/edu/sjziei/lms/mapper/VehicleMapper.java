package cn.edu.sjziei.lms.mapper;

import cn.edu.sjziei.lms.dto.AddVehicleDto;
import cn.edu.sjziei.lms.dto.GetVehicleListDto;
import cn.edu.sjziei.lms.entity.Vehicle;
import cn.edu.sjziei.lms.vo.GetIdVehiclesVo;
import cn.edu.sjziei.lms.vo.VehicleVo;
import org.apache.ibatis.annotations.*;

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
            "    v.last_update_time AS lastUpdateTime," +
            "    v.volume as volume" +
            " FROM veh_vehicle v " +
            "LEFT JOIN sys_user u ON v.driver_id = u.id " +
            "<where> " +
            "    <if test='status != null and status != \"\"'> " +
            "        AND v.status = #{status} " +
            "    </if> " +
            "    <if test='vehicleType != null and vehicleType != \"\"'> " +
            "        AND v.vehicle_type = #{vehicleType} " +
            "    </if> " +
            "    <if test='driverId != null'> " +
            "        AND v.driver_id = #{driverId} " +
            "    </if> " +
            "    <if test='keyword != null and keyword != \"\"'> " +
            "        AND (v.plate_number LIKE CONCAT('%', #{keyword}, '%') " +
            "             OR u.real_name LIKE CONCAT('%', #{keyword}, '%')) " +
            "    </if> " +
            "</where>" +
            "</script>")
    List<VehicleVo> getVehicleList(GetVehicleListDto getVehicleListDto);

    @Select("SELECT COUNT(*) FROM veh_vehicle WHERE plate_number = #{plateNumber}")
    Integer countByPlateNumber(String plateNumber);

    @Insert("INSERT INTO veh_vehicle (driver_id, plate_number, vehicle_type, load_capacity, status, volume,create_time, update_time) " +
            "VALUES (#{driverId}, #{plateNumber}, #{vehicleType}, #{loadCapacity}, #{status},#{volume},NOW(), NOW())")
    void addVehicle(AddVehicleDto addVehicleDto);

    @Select("SELECT id FROM veh_vehicle WHERE plate_number = #{plateNumber} LIMIT 1")
    Long plateNumberToId(String plateNumber);

    @Select("SELECT * FROM veh_vehicle WHERE id = #{id}")
    Vehicle selectById(Long id);

    @Update("<script>" +
            "UPDATE veh_vehicle SET update_time = NOW() " +
            "<if test='vehicleType != null'>, vehicle_type = #{vehicleType}</if>" +
            "<if test='loadCapacity != null'>, load_capacity = #{loadCapacity}</if>" +
            "<if test='driverId != null'>, driver_id = #{driverId}</if>" +
            "<if test='status != null'>, status = #{status}</if>" +
            "<if test='volume != null'>, volume = #{volume}</if>" +
            " WHERE id = #{id}" +
            "</script>")
    int update(Vehicle vehicle);

    @Update("UPDATE veh_vehicle SET status = #{status}, update_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    @Delete("DELETE FROM veh_vehicle WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT v.*, u.real_name AS driverName " +
            "FROM veh_vehicle v " +
            "LEFT JOIN sys_user u ON v.driver_id = u.id " +
            "WHERE v.status = 'IDLE' and u.status=1")
    List<GetIdVehiclesVo> selectIdleVehicles();
}
