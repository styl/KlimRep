package com.example.twousers.controllers;

import com.example.twousers.models.User;
import com.example.twousers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired //приинжектим
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/create")
    public String createUser() {
        try {
            return userService.addUser().getAccountId().toString();
        } catch (Exception e) {
//           throw new RuntimeException(e);
            return e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/givemoney")
    public ResponseEntity<Void> giveMoney(@RequestParam(value = "user") UUID userId,
                                          @RequestParam(value = "amount") Double amount) {
        try {
            User user = userService.findUser(userId);
            user.plusBalance(BigDecimal.valueOf(amount));
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/delete")
    public String deleteUser(@RequestParam(value = "id") UUID id) {
        try {
            return String.valueOf(userService.deleteUser(id));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/showall")
    public String showAll() {
        try {
            return userService.getAllUsers().toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/transfer")
    public ResponseEntity<String> transfer(@RequestParam(value = "from") UUID fromId,
                                         @RequestParam(value = "to") UUID toId,
                                         @RequestParam(value = "amount") BigDecimal amount) {
        try {
            return ResponseEntity.ok(String.valueOf(userService.transfer(fromId, toId, amount)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}


//java -jar target/twousers-0.0.1-SNAPSHOT.jar
