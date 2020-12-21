package com.huttchang.sns.relation.repository;

import com.huttchang.sns.account.domain.User;
import com.huttchang.sns.relation.domain.Relation;
import com.huttchang.sns.relation.domain.RelationId;
import com.huttchang.sns.relation.domain.RelationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationRepository extends JpaRepository<Relation, RelationId> {

    /**
     * 나와 친구 관계인 사람의 게시물을 가져오는 쿼리
     *
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    @Query(nativeQuery = true, value =
        "select distinct \n" +
        "    r.requester_id,r.someone_id,u.id, u.email, u.nickname, r.status, r.related_at, r.request_at \n" +
        "from Users u inner join RELATION r \n" +
        "on (u.ID = r.SOMEONE_ID or u.ID = r.REQUESTER_ID) and u.id != ?1\n" +
        "OFFSET ?2 ROWS\n" +
        "FETCH NEXT ?3 ROWS ONLY")
    List<Relation> findRelationByCondition(Long userId, int offset, int limit);

}
