package com.huttchang.sns.relation.domain;

import com.huttchang.sns.relation.dto.RelationState;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RELATION")
@Getter
@IdClass(RelationId.class)
@NoArgsConstructor
public class SimpleRelation {
    @Id
    @Column(name = "requester_id")
    private Long requesterId;
    @Id
    @Column(name = "someone_id")
    private Long someoneId;
    @Column(name = "status")
    private RelationState status;
    @Column(name = "request_at")
    private Date requestAt;
    @Column(name = "related_at")
    private Date relatedAt;

    public void changeStatusAndRelatedAt(RelationState status){
        this.status = status;
        this.relatedAt = new Date();
    }

    public void changeStatusAndRequestAt(RelationState status){
        this.status = status;
        this.requestAt = new Date();
    }
}
