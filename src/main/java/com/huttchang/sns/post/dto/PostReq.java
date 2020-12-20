package com.huttchang.sns.post.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostReq extends PostDto {
    private List<MultipartFile> images;
}
