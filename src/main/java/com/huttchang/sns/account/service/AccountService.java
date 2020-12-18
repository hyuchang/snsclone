package com.huttchang.sns.account.service;

import com.huttchang.global.exception.DataNotFoundException;
import com.huttchang.global.exception.DuplicationException;
import com.huttchang.sns.account.domain.Authorization;
import com.huttchang.sns.account.exception.InvalidPasswordException;
import com.huttchang.sns.account.exception.UserBlockException;
import com.huttchang.sns.account.domain.User;
import com.huttchang.sns.account.dto.AccountReq;
import com.huttchang.sns.account.dto.UserState;
import com.huttchang.sns.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;

    public Authorization authorization(AccountReq request) throws Exception {
        Authorization result = new Authorization();
        result.setUser(findAccountByIdAndPw(request));
        result.setAccessToken("asdfsadf");
        result.setRefreshToken("sdfasdfaaaaasdfsdf");
        result.setExpiration(1023102030100101012L);
        return result;
    }

    private User findAccountByIdAndPw(AccountReq request) throws DataNotFoundException, UserBlockException, InvalidPasswordException  {
        User user = userRepository.findByEmail(request.getEmail());
        if ( user == null) {
            throw new DataNotFoundException();
        }
        if ( !user.getPwd().equals(request.getPwd())) {
            throw new InvalidPasswordException();
        }
        if ( user.getStatus() == UserState.BLOCK) {
            throw new UserBlockException();
        }
        return user;
    }

    public User createAccount(AccountReq request) throws DuplicationException {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new DuplicationException("Email");
        }
        return userRepository.save(request.toEntity());
    }
}
