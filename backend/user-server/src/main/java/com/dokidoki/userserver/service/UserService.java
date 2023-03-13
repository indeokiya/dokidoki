package com.dokidoki.userserver.service;

import com.dokidoki.userserver.entity.UserEntity;
import com.dokidoki.userserver.enumtype.ProviderType;
import com.dokidoki.userserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity getUserFromSubAndProvider(String sub, ProviderType type){
        return userRepository.findBySubAndProviderType(sub,type);
    }
    public UserEntity saveUser(UserEntity user){
        return userRepository.save(user);
    }
}
