package com.huttchang.sns.post.repository;

import com.huttchang.sns.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(nativeQuery = true,value = "select ID,\n" +
            "USER_ID,\n" +
            "LIKE_CNT,\n" +
            "COMMENT_CNT,\n" +
            "CREATE_AT,\n" +
            "DESCRIPTION " +
            "from post p inner join\n" +
            "    (select\n" +
            "        SOMEONE_ID userid\n" +
            "    from RELATION where STATUS = 1 and REQUESTER_ID = ?1 or SOMEONE_ID = ?1\n" +
            "    union all\n" +
            "    select\n" +
            "        REQUESTER_ID userid\n" +
            "    from RELATION where STATUS = 1 and SOMEONE_ID = ?1 or SOMEONE_ID = ?1\n" +
            "        ) userId on p.USER_ID = userId.userid\n" +
            "order by ID desc\n" +
            "OFFSET ?2 ROWS\n" +
            "    FETCH NEXT ?3 ROWS ONLY\n" +
            "\n" +
            "\n")
    List<Post> findByRelationShip(Long userId, int offset, int limit);
    // 지정해서 찍고 들어간
}
