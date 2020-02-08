package cn.hanquan.bootstrapcurd.controller;

import cn.hanquan.bootstrapcurd.entities.User;
import cn.hanquan.bootstrapcurd.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;


    @PostMapping(value ="/user")
    public String insertUser(User user) {
        Logger logger = LoggerFactory.getLogger(getClass());
        User saveUser = userRepository.save(user); // 存到数据库
        logger.info("user=" + user);
        logger.info("saveUser=" + saveUser);
        return "login";
    }
}
