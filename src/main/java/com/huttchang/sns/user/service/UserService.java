package com.huttchang.sns.user.service;

import com.huttchang.global.exception.DataNotFoundException;
import com.huttchang.sns.user.model.User;
import com.huttchang.sns.user.model.UserRequest;
import com.huttchang.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserInfo(UserRequest request) throws DataNotFoundException {
        return userRepository.findById(request.getId()).orElseThrow(DataNotFoundException::new);
    }
}
