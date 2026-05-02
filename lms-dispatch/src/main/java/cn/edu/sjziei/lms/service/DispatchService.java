package cn.edu.sjziei.lms.service;

import cn.edu.sjziei.lms.dto.CreateDispatchDto;
import cn.edu.sjziei.lms.dto.GetDispatchListDto;
import cn.edu.sjziei.lms.result.Result;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface DispatchService {
    Result getDispatchList(GetDispatchListDto getDispatchListDto, String token);

    Result createDispatch(CreateDispatchDto createDispatchDto);
}
