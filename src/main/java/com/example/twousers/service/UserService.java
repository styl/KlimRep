package com.example.twousers.service;

import com.example.twousers.models.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.el.parser.BooleanNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final HashMap<UUID, User> db = new HashMap<>();

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

    public boolean transfer(UUID fromId, UUID toId, Double amount) {
        try {
            User fromUser = this.findUser(fromId);
            User toUser = this.findUser(toId);
            //пошла жара
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
