package cn.edu.sjziei.lms.util;

import cn.edu.sjziei.lms.mapper.DispatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DispatchUtil {
    @Autowired
    DispatchMapper dispatchMapper;
    /**
     * 生成调度编号
     * */
    public String generateNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        String maxNo = dispatchMapper.getMaxNoByDate(dateStr);
        int seq = 1;

        if (maxNo != null && maxNo.length() >= 11) {
            String lastSeq = maxNo.substring(11);
            seq = Integer.parseInt(lastSeq) + 1;
        }
        return "DIS" + dateStr + String.format("%04d", seq);
    }
}
