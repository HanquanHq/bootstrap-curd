package cn.hanquan.bootstrapcurd.controller;

import cn.hanquan.bootstrapcurd.entities.User;
import cn.hanquan.bootstrapcurd.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    //    @DeleteMapping
    //    @PutMapping
    //    @GetMapping
    //    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @PostMapping(value = "/user/login")
    public String login(@RequestParam("username") String name, @RequestParam("password") String password, Map<String, Object> map, HttpSession session) {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("name=" + name + ", password=" + password);

        User user = userRepository.findByNameAndPassword(name, password);

        if (null != user) {//登录成功
            session.setAttribute("loginUser", name);
            logger.info("成功登录：user=" + user);
            return "redirect:/dashboard";//重定向 避免重复提交表单
        } else {
            logger.info("登录失败：name=" + name + " password=" + password);
            map.put("msg", "用户名或密码错误");
            return "login";
        }
    }

    @RequestMapping(value = "/user/register")
    public String register(){
        System.out.println("redirect register");
        return "register";
    }
}
