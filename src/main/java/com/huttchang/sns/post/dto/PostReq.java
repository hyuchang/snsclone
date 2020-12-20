package com.huttchang.sns.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
public class PostReq extends PostDto {
    private List<MultipartFile> images;
}
