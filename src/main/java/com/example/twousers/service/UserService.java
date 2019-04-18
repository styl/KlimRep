package com.example.twousers.service;

import com.example.twousers.models.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final Map<UUID, User> db = new ConcurrentHashMap<>(); //в неск. потоков можем читать

    public User addUser() {
        User user = new User();
        this.db.put(user.getAccountId(), user);
        return user;
    }

    public User findUser(UUID id) {
        return db.get(id);
    }

    public List<User> getAllUsers() { //на всякий
        return new ArrayList<>(db.values());
    }

    public boolean deleteUser(UUID id) {
        return db.remove(id) != null;
    }

    public boolean transfer(UUID fromId, UUID toId, BigDecimal amount) {
        try {
            final User fromUser = this.findUser(fromId);
            final User toUser = this.findUser(toId);

            User firstUser = fromUser;
            User secondUser = toUser;

            //пошла жара
            if (toUser != null && fromUser != null) {
                //мб дедлок -> сортируем по ID

                if (fromId.compareTo(toId) < 0) {
                    firstUser = toUser;
                    secondUser = fromUser;
                }

                synchronized (firstUser) {
                    synchronized (secondUser) {
                        fromUser.minusBalance(amount);
                        toUser.plusBalance(amount);
                    }
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


}
