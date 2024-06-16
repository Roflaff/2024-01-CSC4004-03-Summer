package com.dgu_csc.akomanager_back.service;

import com.dgu_csc.akomanager_back.dto.MajorDto;
import com.dgu_csc.akomanager_back.jwt.JWTUtil;
import com.dgu_csc.akomanager_back.model.User;
import com.dgu_csc.akomanager_back.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    // POST : [/User/getAll] 을 위한 master 비밀번호
    private static final String MASTER_PASSWORD = "SUMMER";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // POST : [/User/add] : 유저 추가 회원 가입
    @Override
    @Transactional
    public void saveUser(User users) {
        if (userRepository.findByStudentId(users.getStudentId()).isPresent()) {
            throw new IllegalArgumentException("동일한 학번이 존재하는 유저가 존재합니다.");
        }
        // 암호화 후 저장
        String password =  users.getPassword();
        users.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(users);
    }

    // POST : [/User/getAll] => 전체 유저 반환 (master 전용)
    public List<User> getAllUsers(String masterPassword) {
        if (MASTER_PASSWORD.equals(masterPassword)) {
            return userRepository.findAll();
        } else {
            throw new SecurityException("마스터 비밀번호 오류");
        }
    }

    // POST : [/User/{studentId}/get] url의 studentId와 body의 password 정보로 유저 정보 반환
    @Override
    public Optional<User> searchUser(String studentId, String password) {
        Optional<User> user = userRepository.findByStudentId(studentId);
        if(bCryptPasswordEncoder.matches(password, user.get().getPassword())) {
            user.get().setPassword(password);
            return user;
        }
        else
            return null;
    }

    // PUT : [/User/{studentId}/update] url의 studentId와 body의 password 정보로 유저 정보 수정
    @Override
    public Optional<User> updateUser(String studentId, String password, User updatedUsers) {
        return userRepository.findByStudentId(studentId)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> {
                    user.setUniversity(updatedUsers.getUniversity());
                    user.setName(updatedUsers.getName());
                    user.setDate_of_birth(updatedUsers.getDate_of_birth());
                    user.setCollege(updatedUsers.getCollege());
                    user.setMajor(updatedUsers.getMajor());
                    user.setMinor(updatedUsers.getMinor());
                    user.setUsername(updatedUsers.getUsername());
                    // 암호화
                    user.setPassword(bCryptPasswordEncoder.encode(updatedUsers.getPassword()));
                    return userRepository.save(user);
                });
    }

    // DELETE : [/User/{studentId}/delete] url의 studentId와 body의 유저 개인 비밀번호나 마스터 비밀번호로 유저 정보 삭제
    public boolean deleteUser(String studentId, String password) {
        if (password.equals(MASTER_PASSWORD)) {
            return userRepository.findByStudentId(studentId)
                    .map(user -> {
                        userRepository.delete(user);
                        return true;
                    }).orElse(false);
        } else {
            return userRepository.findByStudentId(studentId)
                    .filter(user -> bCryptPasswordEncoder.matches(password, user.getPassword()))
                    .map(user -> {
                        userRepository.delete(user);
                        return true;
                    }).orElse(false);
        }
    }

    @Override
    public Optional<User> findByStudentId(String studentid) {
        return userRepository.findByStudentId(studentid);
    }

    // TODO : 유저 정보로 학과, 입학 연도 파싱 후 MajorDto 로 반환
    @Override
    public Optional<MajorDto> getMajorDto(String studentId) {
        String enterYear = studentId.substring(0, Math.min(studentId.length(), 4));

        return Optional.empty();
    }


}
