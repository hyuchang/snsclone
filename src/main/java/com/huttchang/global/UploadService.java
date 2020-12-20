package com.huttchang.global;

import com.huttchang.global.model.Upload;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UploadService {

    String upload(String uploadPath, MultipartFile file) throws Exception;

    List<String> upload(String uploadPath, List<MultipartFile> files) throws Exception;
}
