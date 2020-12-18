package com.huttchang.sns.user.service;

import com.huttchang.global.exception.DataNotFoundException;
import com.huttchang.global.exception.DuplicationException;
import com.huttchang.global.exception.InvalidPasswordException;
import com.huttchang.global.exception.UserBlockException;
import com.huttchang.sns.user.domain.User;
import com.huttchang.sns.user.dto.AccountReq;
import com.huttchang.sns.user.dto.UserState;
import com.huttchang.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;

    public User findAccountByIdAndPw(AccountReq request) throws DataNotFoundException, UserBlockException, InvalidPasswordException  {
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
        /// fixme 토큰 생성
        return user;
    }

    public User createAccount(AccountReq request) throws DuplicationException {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new DuplicationException("Email");
        }
        return userRepository.save(request.toEntity());
    }
}
