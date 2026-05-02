package cn.edu.sjziei.lms.service;

import cn.edu.sjziei.lms.dto.CreateDispatchDto;
import cn.edu.sjziei.lms.dto.GetDispatchListDto;
import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.vo.DispatchDetailVo;
import org.springframework.stereotype.Service;

@Service
public interface DispatchService {
    Result getDispatchList(GetDispatchListDto getDispatchListDto, String token);

    Result createDispatch(CreateDispatchDto createDispatchDto);

    Result getDispatchDetail(Long id, String token);

    Result signDispatch(Long id, String signName);

    Result updateDispatchStatus(Long id, String status, String token);
}
