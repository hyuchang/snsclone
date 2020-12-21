package com.huttchang.sns.relation.controller.v1;

import com.huttchang.global.model.ResponseBody;
import com.huttchang.sns.account.domain.User;
import com.huttchang.sns.account.dto.AccountReq;
import com.huttchang.sns.account.service.UserService;
import com.huttchang.sns.relation.dto.RelationReq;
import com.huttchang.sns.relation.service.RelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/relations")
public class RelationController {

    private final RelationService relationService;

    @GetMapping
    public ResponseBody<List<User>> findById(@AuthenticationPrincipal User user, AccountReq req) throws Exception {
        req.setId(user.getId());
        return new ResponseBody(relationService.findByRelation(req));
    }

    @PutMapping
    public ResponseBody<Boolean> updateStatus(@AuthenticationPrincipal User user,@Valid @RequestBody RelationReq req) throws Exception {
        req.setUserId(user.getId());
        return new ResponseBody(relationService.updateByRelation(req));
    }


}
