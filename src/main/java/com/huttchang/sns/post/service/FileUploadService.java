package com.huttchang.sns.post.service;

import com.huttchang.global.provider.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class FileUploadService implements UploadService {

    @Value("${path.host}")
    private String webpath;


    @Override
    public String upload(String uploadPath, String functionPath,MultipartFile file) throws Exception {
        try {
            if (!new File(uploadPath).exists()){
                new File(uploadPath).mkdirs();
            }
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = System.currentTimeMillis()+ext;
            file.transferTo(new File(uploadPath,  fileName));
            return webpath+"/files/"+functionPath+"/"+fileName;
        } catch (Exception e) {
            log.error("upload", e);
            throw new Exception("FileUploadException");
        }
    }

    @Override
    public List<String> upload(String uploadPath, String functionPath, List<MultipartFile> files) throws Exception {
        List<String> filePathList = new ArrayList<>();
        if ( files == null){
            return Collections.emptyList();
        }
        for (MultipartFile file : files) {
            filePathList.add(upload(uploadPath, functionPath, file));
        }
        return filePathList;
    }
}
