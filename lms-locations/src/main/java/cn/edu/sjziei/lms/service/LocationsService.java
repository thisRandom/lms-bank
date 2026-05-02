package cn.edu.sjziei.lms.service;

import cn.edu.sjziei.lms.dto.ReportLocationDto;
import cn.edu.sjziei.lms.result.Result;

public interface LocationsService {
    Result reportLocation(ReportLocationDto dto, String token);

    Result getDispatchLocations(Long dispatchId, String token);
}