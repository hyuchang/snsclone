package com.huttchang.sns.relation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@NoArgsConstructor
public class RelationId implements Serializable {
    private Long requesterId;
    private Long someoneId;

    public RelationId(Long requesterId, Long someoneId) {
        this.requesterId = requesterId;
        this.someoneId = someoneId;
    }
}
