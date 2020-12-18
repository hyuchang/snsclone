package com.huttchang.sns.user.service;

import com.huttchang.global.exception.DataNotFoundException;
import com.huttchang.sns.user.domain.User;
import com.huttchang.sns.user.dto.AccountReq;
import com.huttchang.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 사용자의 정보를 리턴
     * @param id 사용자의 아이디
     * @return 사용자정보
     * @throws DataNotFoundException 사용자가 없을 때 발생하는 예외 (GlobalHandler에서 담당)
     */
    public User findById(long id) throws DataNotFoundException {
        return userRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    /**
     * 사용자의 정보를 수정
     * @param id 사용자 고유아이디
     * @param request 수정할 사용자의 정보
     * @return
     * @throws DataNotFoundException 사용자가 없을 때 발생하는 예외 (GlobalHandler에서 담당)
     */
    public User modifyUser(long id, AccountReq request) throws DataNotFoundException {
        User user = userRepository.findById(id).orElseThrow(DataNotFoundException::new);
        user.modifyUser(request);
        return userRepository.save(user);
    }
}
