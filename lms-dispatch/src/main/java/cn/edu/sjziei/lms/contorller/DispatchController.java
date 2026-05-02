package cn.edu.sjziei.lms.contorller;

import cn.edu.sjziei.lms.annotation.RequiresPermissions;
import cn.edu.sjziei.lms.dto.CreateDispatchDto;
import cn.edu.sjziei.lms.dto.GetDispatchListDto;
import cn.edu.sjziei.lms.dto.SignDispatchDto;
import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.service.DispatchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 调度模块
 * */
@RestController
@RequestMapping("/dispatches")
public class DispatchController {
    @Autowired
    DispatchService dispatchService;

    /**
     * 调度列表
     * */
    @GetMapping
    @RequiresPermissions({"DISPATCHER","DRIVER"})
    public Result getDispatchList(GetDispatchListDto getDispatchListDto, @RequestHeader("Authorization") String token){
        return dispatchService.getDispatchList(getDispatchListDto,token);
    }

    @PostMapping
    @RequiresPermissions({"DISPATCHER"})
    public Result createDispatch(@Valid @RequestBody CreateDispatchDto createDispatchDto){
        return dispatchService.createDispatch(createDispatchDto);
    }

    @GetMapping("/{id}")
    @RequiresPermissions({"DISPATCHER","DRIVER"})
    public Result getDispatchDetail(@PathVariable("id") Long id, @RequestHeader("Authorization") String token){
        return dispatchService.getDispatchDetail(id,token);
    }

    @PutMapping("/{id}/sign")
    @RequiresPermissions({"CUSTOMER"})
    public Result signDispatch(@PathVariable("id") Long id, @Valid @RequestBody SignDispatchDto signDispatchDto){
        return dispatchService.signDispatch(id, signDispatchDto.getSignName());
    }
}
