package cn.hanquan.bootstrapcurd.controller;

import cn.hanquan.bootstrapcurd.exception.UserNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HelloController {
    @ResponseBody //返回字符串
    @RequestMapping("/hello")
    public String hello(@RequestParam("user") String user) {
        if (user.equals("aaa")) {
            throw new UserNotExistException();
        }
        return "Hello World";
    }

    @RequestMapping("/login")
    public String index() {
        System.out.println("here");
        return "login";
    }

    @RequestMapping("/dashboard")
    public String dashboard() {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("dashboard");
        return "dashboard";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/druid")
    public String druid() {
        return "druid";
    }

}
