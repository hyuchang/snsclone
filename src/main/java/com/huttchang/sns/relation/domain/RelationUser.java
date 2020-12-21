package com.huttchang.sns.relation.domain;

import com.huttchang.sns.relation.dto.RelationState;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="users")
@Getter
@NoArgsConstructor
public class RelationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email, nickname;
    private RelationState status;

}
