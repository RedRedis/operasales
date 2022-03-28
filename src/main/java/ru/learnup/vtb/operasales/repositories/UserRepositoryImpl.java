package ru.learnup.vtb.operasales.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.learnup.vtb.operasales.model.User;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private PasswordEncoder passwordEncoder;

    private Map<String, User> db = new HashMap<>();

    @Autowired
    public UserRepositoryImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        db.put("user", new User("user", passwordEncoder.encode("user123"), "ROLE_USER"));
        db.put("admin", new User("admin", passwordEncoder.encode("admin123"), "ROLE_ADMIN"));
    }

    @Override
    public User findByLogin(String login) {
        return db.get(login);
    }
}
