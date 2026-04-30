package cn.edu.sjziei.lms.mapper;

import cn.edu.sjziei.lms.dto.GetOrderDto;
import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.vo.RecordVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    public List<RecordVo> getOrderList(GetOrderDto getOrderDto) ;
}
