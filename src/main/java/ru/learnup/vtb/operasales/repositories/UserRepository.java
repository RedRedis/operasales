package ru.learnup.vtb.operasales.repositories;

import ru.learnup.vtb.operasales.model.User;

public interface UserRepository {

    User findByLogin(String login);
}
