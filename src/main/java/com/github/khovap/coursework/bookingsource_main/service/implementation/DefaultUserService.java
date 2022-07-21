package com.github.khovap.coursework.bookingsource_main.service.implementation;


import com.github.khovap.coursework.bookingsource_main.entity.Role;
import com.github.khovap.coursework.bookingsource_main.entity.UserEntity;
import com.github.khovap.coursework.bookingsource_main.mapper.ClientToEntityMapper;
import com.github.khovap.coursework.bookingsource_main.model.Client;
import com.github.khovap.coursework.bookingsource_main.repository.ClientRepository;
import com.github.khovap.coursework.bookingsource_main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder cryptPasswordEncoder;

    private final ClientRepository clientRepository;
    
    private final ClientToEntityMapper clientMapper;


    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() ->new UsernameNotFoundException("Пользователь не найден"));
        return user;
    }

    public List<UserEntity> allUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public boolean saveUser(UserEntity user, Client client) {
        if(userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent())
            return false;

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        
        if (clientRepository.findByPhoneNumberEquals(user.getPhoneNumber()) == null) {
            client.setPhoneNumber(user.getPhoneNumber());
            clientRepository.save(clientMapper.clientToClientEntity(client));
        }
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
