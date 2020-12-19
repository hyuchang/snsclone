package com.huttchang.sns.account.service;

import com.huttchang.global.exception.DataNotFoundException;
import com.huttchang.global.exception.DuplicationException;
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

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Authorization signIn(AccountReq request) throws Exception {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(DataNotFoundException::new);

        if (!passwordEncoder.matches(request.getPwd(), user.getPwd())) {
            throw new InvalidPasswordException();
        }
        if (user.getStatus() == UserState.BLOCK) {
            throw new UserBlockException();
        }
        return new Authorization(user, authService.createToken(user));
    }

    /**
     * 계정생성
     *
     * @param request 계정생성에 필요한 정보
     * @return
     * @throws DuplicationException 계정중복에 대한 내용
     */
    public User createAccount(AccountReq request) throws DuplicationException {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Data Not Found"));
    }
}
