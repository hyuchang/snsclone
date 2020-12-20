package com.huttchang.sns.post.service;

import com.huttchang.global.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class FileUploadService implements UploadService {

    @Override
    public String upload(String uploadPath, MultipartFile file) throws Exception {
        try {
            if (!new File(uploadPath).exists()){
                new File(uploadPath).mkdirs();
            }
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            file.transferTo(new File(uploadPath,  System.currentTimeMillis()+ext));
            return uploadPath;
        } catch (Exception e) {
            log.error("upload", e);
            throw new Exception("FileUploadException");
        }
    }

    @Override
    public List<String> upload(String uploadPath, List<MultipartFile> files) throws Exception {
        List<String> filePathList = new ArrayList<>();
        if ( files == null){
            return Collections.emptyList();
        }
        for (MultipartFile file : files) {
            filePathList.add(upload(uploadPath, file));
        }
        return filePathList;
    }
}
