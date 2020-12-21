package com.huttchang.sns.relation.repository;

import com.huttchang.sns.relation.domain.Relation;
import com.huttchang.sns.relation.domain.RelationId;
import com.huttchang.sns.relation.domain.SimpleRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimpleRelationRepository extends JpaRepository<SimpleRelation, RelationId> {

}
