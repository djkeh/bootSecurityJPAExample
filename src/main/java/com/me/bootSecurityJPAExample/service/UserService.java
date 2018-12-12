package com.me.bootSecurityJPAExample.service;

import com.me.bootSecurityJPAExample.domain.User;
import com.me.bootSecurityJPAExample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getList() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUser(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    @Transactional(readOnly = true)
    public boolean hasUser(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }
}
