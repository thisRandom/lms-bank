package cn.edu.sjziei.lms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVehicleListVo {
    Long total; //总记录数
    Integer pages; //总页数
    Integer current; //当前页
    List<VehicleVo> records; //车辆列表
}
