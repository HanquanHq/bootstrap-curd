package cn.hanquan.bootstrapcurd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

@SpringBootApplication
public class BootstrapCurdApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootstrapCurdApplication.class, args);
    }

    @Bean
    public ViewResolver MyViewResolver(){
        return new MyViewResolver();
    }

    public static class MyViewResolver implements ViewResolver{
        @Override
        public View resolveViewName(String viewName, Locale locale) {
            return null;
        }
    }
}
