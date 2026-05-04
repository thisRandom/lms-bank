package cn.edu.sjziei.lms.mapper;

import cn.edu.sjziei.lms.vo.DashboardStatsResponseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DashboardMapper {
    @Select("SELECT COUNT(*) FROM ord_order")
    Long getTotalOrders();

    @Select("SELECT COUNT(*) FROM ord_order WHERE status IN ('DISPATCHED', 'IN_TRANSIT', 'ARRIVED')")
    Long getInProgressOrders();

    @Select("SELECT COUNT(*) FROM ord_order WHERE status = 'SIGNED'")
    Long getSignedOrders();

    @Select("SELECT COUNT(*) FROM ord_order WHERE status = 'PENDING'")
    Long getPendingOrders();

    @Select("SELECT status, COUNT(*) as count FROM ord_order GROUP BY status")
    List<DashboardStatsResponseVo.OrderStatusDistribution> getOrderStatusDistribution();

    @Select("SELECT status, COUNT(*) as count FROM veh_vehicle GROUP BY status")
    List<DashboardStatsResponseVo.VehicleStatusDistribution> getVehicleStatusDistribution();

    @Select("SELECT COALESCE(SUM(weight), 0) FROM ord_order")
    Double getTotalWeight();

    @Select("SELECT COALESCE(SUM(volume), 0) FROM ord_order")
    Double getTotalVolume();

    @Select("SELECT goods_type as goodsType, COUNT(*) as count FROM ord_order GROUP BY goods_type")
    List<DashboardStatsResponseVo.GoodsTypeDistribution> getGoodsTypeDistribution();
}
