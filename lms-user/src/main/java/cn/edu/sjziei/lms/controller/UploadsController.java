package cn.edu.sjziei.lms.controller;

import cn.edu.sjziei.lms.mapper.UserMapper;
import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.util.TokenUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;

/**
 * 上传头像
 * */
@RestController
public class UploadsController {
    @Value("${file.upload-path}")
    private String uploadPath;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    UserMapper userMapper;

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String token) throws IOException {
        String fileName = IdUtil.simpleUUID() + "." + FileUtil.extName(file.getOriginalFilename());
        File destFile = FileUtil.file(uploadPath, fileName);
        FileUtil.writeFromStream(file.getInputStream(), destFile);
        String url = "/" + fileName;

        //用token来存路径
        Long id = tokenUtil.analysisToken(token).getId();
        userMapper.resetHeadshot(id,url);

        return Result.success(200,url);
    }
}
