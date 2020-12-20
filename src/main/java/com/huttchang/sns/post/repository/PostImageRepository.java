package com.huttchang.sns.post.repository;

import com.huttchang.sns.post.domain.PostImage;
import com.huttchang.sns.post.dto.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {


}
