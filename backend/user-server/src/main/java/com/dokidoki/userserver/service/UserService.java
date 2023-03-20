package com.dokidoki.userserver.service;

import com.dokidoki.userserver.entity.UserEntity;
import com.dokidoki.userserver.enumtype.ProviderType;
import com.dokidoki.userserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<UserEntity> getUserFromSubAndProvider(String sub, ProviderType type){
        return userRepository.findBySubAndProviderType(sub,type);
    }
    @Transactional
    public UserEntity saveUser(UserEntity user){
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> getUserById(Long id){
        return userRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
}
