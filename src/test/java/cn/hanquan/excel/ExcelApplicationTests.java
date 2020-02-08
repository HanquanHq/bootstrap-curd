package cn.hanquan.excel;

import cn.hanquan.excel.util.ExcelUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void TestCreateWorkbook(){
        ExcelUtil.CreateNewWorkbook();
    }

    @Test
    public void TestCreateSheet(){
        ExcelUtil.CreateNewSheet();
    }
    @Test
    public void TestCreateCell(){
        ExcelUtil.CreateNewCell();
    }

    @Test
    public void OpenAndReadExcel(){
        ExcelUtil.OpenExcelByFile();
        ExcelUtil.OpenExcelByFileInputStream();
    }

    /**
     * 应该串行执行吧，不能直接跑全部，顺序会乱
     * 所以写了这个
     */
    @Test
    public void TestAllInOrder(){
        ExcelUtil.CreateNewWorkbook();
        ExcelUtil.CreateNewSheet();
        ExcelUtil.CreateNewCell();
        ExcelUtil.OpenExcelByFile();
        ExcelUtil.OpenExcelByFileInputStream();
    }
}
