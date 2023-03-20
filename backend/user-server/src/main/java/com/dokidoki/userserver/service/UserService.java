package com.dokidoki.userserver.service;

import com.dokidoki.userserver.entity.UserEntity;
import com.dokidoki.userserver.enumtype.ProviderType;
import com.dokidoki.userserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<UserEntity> getUserFromSubAndProvider(String sub, ProviderType type){
        return userRepository.findBySubAndProviderType(sub,type);
    }
    public UserEntity saveUser(UserEntity user){
        return userRepository.save(user);
    }

    public Optional<UserEntity> getUserById(Long id){
        return userRepository.findById(id);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
}
