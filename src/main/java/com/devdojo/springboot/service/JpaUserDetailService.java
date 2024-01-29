package com.devdojo.springboot.service;

import com.devdojo.springboot.domain.User;
import com.devdojo.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        User u = user.orElseThrow(() -> new UsernameNotFoundException("User can't be found"));
        return new SecurityUser(u);
    }
}
