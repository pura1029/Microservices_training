package com.synechron.springbootdemo.service;

import com.synechron.springbootdemo.dao.UserRepository;
import com.synechron.springbootdemo.model.DomainUserDetails;
import com.synechron.springbootdemo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Invalid User"));
        return new DomainUserDetails(user);
    }
}