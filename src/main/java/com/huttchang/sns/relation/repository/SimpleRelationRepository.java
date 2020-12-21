package com.huttchang.sns.relation.repository;

import com.huttchang.sns.relation.domain.RelationId;
import com.huttchang.sns.relation.domain.SimpleRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleRelationRepository extends JpaRepository<SimpleRelation, RelationId> {

}
