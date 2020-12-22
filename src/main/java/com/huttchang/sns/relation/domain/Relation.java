package com.huttchang.sns.relation.domain;

import com.huttchang.sns.relation.dto.RelationState;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Getter
@IdClass(RelationId.class)
@NoArgsConstructor
public class Relation {
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

    @OneToOne
    @JoinColumn(name="requester_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RelationUser reqUser;

    @OneToOne
    @JoinColumn(name="someone_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RelationUser someoneUser;


}
