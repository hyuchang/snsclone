package com.huttchang.sns.post.repository;

import com.huttchang.sns.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 나와 친구 관계인 사람의 게시물을 가져오는 쿼리
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    @Query(nativeQuery = true,value =
            "select\n" +
            "    p.ID,\n" +
            "    p.USER_ID,\n" +
            "    p.LIKE_CNT,\n" +
            "    p.COMMENT_CNT,\n" +
            "    p.CREATE_AT,\n" +
            "    p.DESCRIPTION,\n" +
            "    p.ACL\n" +
            "from post p inner join\n" +
            "    (\n" +
            "        select\n" +
            "            SOMEONE_ID user_id\n" +
            "        from RELATION where STATUS = 2 and (REQUESTER_ID = ?1 or SOMEONE_ID = ?1)\n" +
            "        union\n" +
            "        select\n" +
            "            REQUESTER_ID user_id\n" +
            "        from RELATION where STATUS = 2 and (REQUESTER_ID = ?1 or SOMEONE_ID = ?1)\n" +
                    "union\n" +
                    "select ?1\n" +
            "    ) users on p.USER_ID = users.user_id\n" +
            "order by p.ID desc\n" +
            "OFFSET ?2 ROWS\n" +
            "FETCH NEXT ?3 ROWS ONLY")
    List<Post> findPostByRelationShip(Long userId, int offset, int limit);

    @Query(nativeQuery = true,value =
            "select\n" +
                    "    p.ID,\n" +
                    "    p.USER_ID,\n" +
                    "    p.LIKE_CNT,\n" +
                    "    p.COMMENT_CNT,\n" +
                    "    p.CREATE_AT,\n" +
                    "    p.DESCRIPTION,\n" +
                    "    p.ACL\n" +
                    "from post p\n" +
                    "where p.acl = 0\n" +
                    "and p.user_id = ?1\n" +
                    "order by p.ID desc\n" +
                    "OFFSET ?2 ROWS\n" +
                    "FETCH NEXT ?3 ROWS ONLY")
    List<Post> findPostNONERelationByUserId(Long userId, int offset, int limit);
    @Query(nativeQuery = true,value =
            "select\n" +
                    "    p.ID,\n" +
                    "    p.USER_ID,\n" +
                    "    p.LIKE_CNT,\n" +
                    "    p.COMMENT_CNT,\n" +
                    "    p.CREATE_AT,\n" +
                    "    p.DESCRIPTION,\n" +
                    "    p.ACL\n" +
                    "from post p\n" +
                    "where p.user_id = ?1\n" +
                    "order by p.ID desc\n" +
                    "OFFSET ?2 ROWS\n" +
                    "FETCH NEXT ?3 ROWS ONLY")
    List<Post> findPostRelationByUserId(Long userId, int offset, int limit);


    //
}
