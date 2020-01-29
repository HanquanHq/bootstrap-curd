package cn.hanquan.bootstrapcurd.controller;


import cn.hanquan.bootstrapcurd.entities.Department;
import cn.hanquan.bootstrapcurd.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    DepartmentMapper departmentMapper;


    /**
     * 查询所有部门
     */
    @GetMapping("/depts")
    public String list(Model model) {
        List<Department> departments = departmentMapper.getDept();
        System.out.println("查询所有部门");
        //放在请求域中
        model.addAttribute("depts", departments);
        // thymeleaf默认就会拼串
        // classpath:/tDepartmentlates/xxx.html
        return "dept/list";
    }


    /**
     * 来到部门添加页面
     */
    @GetMapping("/dept")
    public String toAddPage(Model model) {
        //来到添加页面,查出所有的部门，在页面显示
        List<Department> departments = departmentMapper.getDept();
        model.addAttribute("depts", departments);
        return "dept/add";
    }


    /**
     * 部门添加
     * SpringMVC自动将请求参数和入参对象的属性进行一一绑定
     * 要求请求参数的名字和javaBean入参的对象里面的属性名是一样的
     */
    @PostMapping("/dept")
    public String addDept(Department department) {
        //来到员工列表页面
        System.out.println("PostMapping 添加的部门信息：" + department);
        //保存部门
        departmentMapper.insertDept(department);
        // redirect: 表示重定向到一个地址  /代表当前项目路径
        // forward: 表示转发到一个地址
        return "redirect:/depts";
    }


    /**
     * 部门修改；需要提交部门id
     */
    @PutMapping("/dept")
    public String updateDepartment(Department department) {
        System.out.println("PutMapping 修改的部门数据：" + department);
        departmentMapper.updateDept(department);
        return "redirect:/depts";
    }

    /**
     * 来到修改页面，查出当前部门，在页面回显
     */
    @GetMapping("/dept/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model) {
        Department department = departmentMapper.getDeptById(id);
        model.addAttribute("depts", department);

        //页面要显示所有的部门列表
        model.addAttribute("dept", department);
        //回到修改页面(add是一个修改 / 添加二合一的页面);
        return "dept/add";
    }



    /**
     * 部门删除
     */
    @DeleteMapping("/dept/{id}")
    public String deleteDepartment(@PathVariable("id") Integer id) {
        System.out.println("删除部门id：" + id);
        departmentMapper.deleteDeptById(id);
        return "redirect:/depts";
    }


}
