package cn.edu.sjziei.lms.util;

import cn.edu.sjziei.lms.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OrderNoUtil {
    @Autowired
    OrderMapper orderMapper;

    public String generateOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        String maxOrderNo = orderMapper.getMaxOrderNoByDate(dateStr);
        int seq = 1;
        if (maxOrderNo != null && maxOrderNo.length() >= 13) {
            String lastSeq = maxOrderNo.substring(9);
            seq = Integer.parseInt(lastSeq) + 1;
        }
        return "ORD" + dateStr + String.format("%04d", seq);
    }

    /**
     * 检验手机号
     * */
    public boolean inspectionP(String phone){
        String regex="^1[3-9]\\d{9}$";
        return phone.matches(regex);
    }
}
