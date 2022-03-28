package ru.learnup.vtb.operasales.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.learnup.vtb.operasales.model.User;
import ru.learnup.vtb.operasales.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("No such user");
        }
        return user;
    }
}
