package com.huttchang.sns.account.service;

import com.huttchang.global.exception.DataNotFoundException;
import com.huttchang.global.exception.DuplicationException;
import com.huttchang.global.provider.AESUtils;
import com.huttchang.sns.account.domain.Authorization;
import com.huttchang.sns.account.domain.User;
import com.huttchang.sns.account.dto.AccountReq;
import com.huttchang.sns.account.dto.UserState;
import com.huttchang.sns.account.exception.InvalidPasswordException;
import com.huttchang.sns.account.exception.UserBlockException;
import com.huttchang.sns.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인 기능
     * @param request 로그인 요청파라미터
     * @return
     * @throws Exception
     */
    public Authorization signIn(AccountReq request) throws Exception {
        // 해당 유저가 존재 하는지 찾는다
//
        request.setPwd(AESUtils.decrypt(request.getPwd()));
        request.setEmail(AESUtils.decrypt(request.getEmail()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(DataNotFoundException::new);

        // 비밀번호가 맞는지 확인
        if (!passwordEncoder.matches(request.getPwd(), user.getPwd())) {
            // 틀리면 비밀번호 틀린 예외 발생
            throw new InvalidPasswordException();
        }
        // 상태 확인가 제재 상태인 경우
        if (user.getStatus() == UserState.BLOCK) {
            // 로그인하지 못하도록 막습니다
            throw new UserBlockException();
        }
        // 인증 정보 및 유저 정보 리턴 합니다
        return new Authorization(user, authService.createToken(user));
    }

    /**
     * 계정생성
     * @param request 계정생성에 필요한 정보
     * @return
     * @throws DuplicationException 계정중복에 대한 내용
     */
    @Transactional
    public User createAccount(AccountReq request) throws DuplicationException, Exception {
        request.setPwd(AESUtils.decrypt(request.getPwd()));
        request.setEmail(AESUtils.decrypt(request.getEmail()));

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicationException("Email");
        }
        request.setPwd(passwordEncoder.encode(request.getPwd()));
        return userRepository.save(request.toEntity());
    }

    /**
     * 사용자의 정보를 리턴
     *
     * @param id 사용자의 아이디
     * @return 사용자정보
     * @throws DataNotFoundException 사용자가 없을 때 발생하는 예외 (GlobalHandler에서 담당)
     */
    public User findById(long id) throws DataNotFoundException {
        return userRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    /**
     * 사용자의 정보를 수정
     *
     * @param id      사용자 고유아이디
     * @param request 수정할 사용자의 정보
     * @return
     * @throws DataNotFoundException 사용자가 없을 때 발생하는 예외 (GlobalHandler에서 담당)
     */
    public User modifyUser(long id, AccountReq request) throws DataNotFoundException {
        User user = userRepository.findById(id).orElseThrow(DataNotFoundException::new);
        request.setPwd(passwordEncoder.encode(request.getPwd()));
        user.modifyUser(request);
        return userRepository.save(user);
    }

    /**
     * UserName
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Data Not Found"));
    }
}
