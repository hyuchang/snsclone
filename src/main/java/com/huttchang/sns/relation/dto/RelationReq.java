package com.huttchang.sns.relation.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
public class RelationReq {
    @Setter
    private Long userId;
    @NotNull
    private Long requesterId;
    @NotNull
    private Long someoneId;
    @NotNull
    private RelationState status;
}
