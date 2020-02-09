package cn.hanquan.bootstrapcurd.controller;

import cn.hanquan.bootstrapcurd.entities.ExcelTable;
import cn.hanquan.bootstrapcurd.mapper.ExcelTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class ExcelTableController {

    @Autowired
    ExcelTableMapper excelTableMapper;


    /**
     * 查询所有ExcelTable
     */
    @GetMapping("/excelTables")
    public String list(Model model) {
        Collection<ExcelTable> excelTables = excelTableMapper.getExcelTable();
        //放在请求域中
        model.addAttribute("excelTables", excelTables);
        // thymeleaf默认就会拼串
        // classpath:/templates/xxx.html
        return "excel/list";
    }


    /**
     * 来到ExcelTable添加页面
     */
    @GetMapping("/excelTable")
    public String toAddPage(Model model) {
        return "excel/add";
    }


    /**
     * excelTable添加
     * SpringMVC自动将请求参数和入参对象的属性进行一一绑定
     * 要求请求参数的名字和javaBean入参的对象里面的属性名是一样的
     */
    @PostMapping("/excelTable")
    public String addExcelTable(ExcelTable excelTable) {
        //来到ExcelTable列表页面
        System.out.println("In addExcelTable, excelTable = " + excelTable);
        //保存ExcelTable
        excelTableMapper.insertExcelTable(excelTable);
        // redirect: 表示重定向到一个地址  /代表当前项目路径
        // forward: 表示转发到一个地址
        return "redirect:/excelTables";
    }


    /**
     * excelTable修改；需要提交excelTable id
     */
    @PutMapping("/excelTable")
    public String updateExcelTable(ExcelTable excelTable) {
        System.out.println("In updateExcelTable, excelTable = " + excelTable);
        excelTableMapper.updateExcelTableById(excelTable);
        return "redirect:/excelTables";
    }

    /**
     * 来到修改页面，查出当前excelTable，在页面回显
     */
    @GetMapping("/excelTable/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model) {
        ExcelTable excelTable = excelTableMapper.getExcelTableById(id);
        model.addAttribute("excelTable", excelTable);

        //回到修改页面(add是一个修改 / 添加二合一的页面);
        return "excel/add";
    }


    /**
     * 删除一个 ExcelTable
     */
    @DeleteMapping("/excelTable/{id}")
    public String deleteExcelTable(@PathVariable("id") Integer id) {
        System.out.println("In deleteExcelTable, id = " + id);
        excelTableMapper.deleteExcelTableById(id);
        return "redirect:/excelTables";
    }
}
