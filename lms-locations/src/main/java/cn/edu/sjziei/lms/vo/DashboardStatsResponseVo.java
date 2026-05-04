package cn.edu.sjziei.lms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponseVo {
    private Long totalOrders;
    private Long inProgressOrders;
    private Long signedOrders;
    private Long pendingOrders;
    private List<OrderStatusDistribution> orderStatusDistribution;
    private List<VehicleStatusDistribution> vehicleStatusDistribution;
    private Double totalWeight;
    private Double totalVolume;
    private List<GoodsTypeDistribution> goodsTypeDistribution;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderStatusDistribution {
        private String status;
        private Long count;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VehicleStatusDistribution {
        private String status;
        private Long count;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoodsTypeDistribution {
        private String goodsType;
        private Long count;
    }
}
