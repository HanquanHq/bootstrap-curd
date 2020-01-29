package cn.hanquan.bootstrapcurd.repository;


import cn.hanquan.bootstrapcurd.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//继承JpaRepository来完成对数据库的操作
public interface UserRepository extends JpaRepository<User, Integer> {
    //支持定义在Repository接口中的方法名来定义查询，方法名是根据实体类的属性来确定的
    User findByNameAndPassword(String name,String password);
}
