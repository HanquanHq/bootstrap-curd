package cn.hanquan.bootstrapcurd.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ExcelUtil {
    /**
     * 在application.properties中配置uploadPath，部署的时候方便随时修改
     */
    @Value("${hanquan.uploadPath}")
    private String uploadPath;


    public ExcelUtil() {
    }

    /**
     * ## 3.1 创建 Workbook
     * - `HSSFWorkbook` 是操作 Excel2003 以前（包括2003）的版本，扩展名是.xls；
     * - `XSSFWorkbook` 是操作 Excel2007 后的版本，扩展名是.xlsx；
     * - `SXSSFWorkbook` 是操作 Excel2007 后的版本，扩展名是.xlsx；
     * <p>
     * 返回空 输出 workbook.xls workbook.xlsx 注意此时 excel元素不全，还不能打开
     */
    public void CreateNewWorkbook(Integer id) {
        Workbook wb = new HSSFWorkbook();
        OutputStream fileOut = null;
        try {
            System.out.println("创建workbook, id = "+id);
            fileOut = new FileOutputStream(uploadPath + "workbook" + id + ".xls");
            wb.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        Workbook wb2 = new XSSFWorkbook();
//        try (OutputStream fileOut = new FileOutputStream(uploadPath + "workbook" + id + ".xlsx")) {
//            wb2.write(fileOut);
//        } catch (Exception e) {
//            System.out.println("创建workbook.xlsx");
//            e.printStackTrace();
//        }

    }

    /**
     * ## 3.2 创建工作表 Sheet
     * - 工作表名称不要超过 31 个字符
     * - 名称不能含有特殊字符
     * - 可以使用 WorkbookUtil.createSafeSheetName 来创建安全的工作表名称
     * 返回空 输出 workbook.xls  注意此时 excel元素不全，还不能打开
     */
    public void CreateNewSheet(Integer id) {
        Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
        Sheet sheet1 = wb.createSheet("new sheet");
        Sheet sheet2 = wb.createSheet("new second sheet");
        String safeName = WorkbookUtil.createSafeSheetName("[O'Brien's sales*?]"); // returns " O'Brien's sales   "
        Sheet sheet3 = wb.createSheet(safeName);
        OutputStream fileOut = null;
        try {
            System.out.println("创建工作表");
            fileOut = new FileOutputStream(uploadPath + "workbook" + id + ".xls");
            wb.write(fileOut);
            System.out.println("创建工作表成功");
        } catch (Exception e) {
            System.out.println("创建工作表失败");
            e.printStackTrace();
        } finally {
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 静态方法中不能调用非静态方法，所以删掉了static
     *
     * @param id
     * @param firstCell
     * @param secondCell
     * @param thirdCell
     * @param fourthCell
     */
    public void CreateNewCell(Integer id, String firstCell, String secondCell, String thirdCell, String fourthCell) {
        Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");
        //先行后列：先创建i行
        Row row_0 = sheet.createRow(0);
        Row row_1 = sheet.createRow(1);
        Row row_2 = sheet.createRow(2);
        Row row_3 = sheet.createRow(3);
        Row row_4 = sheet.createRow(4);
        Row row_5 = sheet.createRow(5);
        Row row_6 = sheet.createRow(6);
        Row row_7 = sheet.createRow(7);
        Row row_8 = sheet.createRow(8);

        //第0行第i列：标题
        Cell cell1 = row_0.createCell(1);
        Cell cell2 = row_0.createCell(3);
        Cell cell3 = row_2.createCell(1);
        Cell cell4 = row_2.createCell(3);

        cell1.setCellValue("firstCell");
        cell2.setCellValue("secondCell");
        cell3.setCellValue("thirdCell");
        cell4.setCellValue("fourthCell");

        //创建列
        row_1.createCell(1).setCellValue(firstCell);
        row_1.createCell(3).setCellValue(secondCell);

        row_3.createCell(1).setCellValue(thirdCell);
        row_3.createCell(3).setCellValue(fourthCell);

        // 边框样式
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.MEDIUM_DASHED);//底部边框
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.MEDIUM_DASHED);//左边
        style.setLeftBorderColor(IndexedColors.GREEN.getIndex());
        style.setBorderRight(BorderStyle.MEDIUM_DASHED);//右边
        style.setRightBorderColor(IndexedColors.BLUE.getIndex());
        style.setBorderTop(BorderStyle.MEDIUM_DASHED);//上边
        style.setTopBorderColor(IndexedColors.BLUE.getIndex());//颜色

        cell1.setCellStyle(style);
        cell2.setCellStyle(style);
        cell3.setCellStyle(style);
        cell4.setCellStyle(style);


        try {
            OutputStream fileOut = new FileOutputStream(uploadPath + "workbook" + id + ".xls");
            wb.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用 File 的方式读取 Excel
     * 读取单元格
     */
    public void OpenExcelByFile() {
        Workbook wb = null;
        try {
            System.out.println("使用 File 的方式读取 Excel");
            wb = WorkbookFactory.create(new File("workbook.xls"));
            //读取
            Sheet sheet = wb.getSheetAt(0);//第一个
            Sheet sheet1 = wb.getSheet("sheet1");//根据名称读取
            Row row = sheet.getRow(0);//获取行
            Cell cell = row.getCell(0);//获取第一行

            System.out.println("In OpenExcelByFile, cell = " + cell);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 关闭流
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 使用 FileInputStream
     */
    public void OpenExcelByFileInputStream() {
        Workbook wb = null;
        try {
            System.out.println("使用 FileInputStream 的方式读取 Excel");
            wb = WorkbookFactory.create(new FileInputStream("workbook.xls"));
            //读取
            Sheet sheet = wb.getSheetAt(0);//第一个
            Sheet sheet1 = wb.getSheet("sheet1");//根据名称读取
            Row row = sheet.getRow(0);//获取行
            Cell cell = row.getCell(1);
            System.out.println("In OpenExcelByFile, OpenExcelByFileInputStream = " + cell);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 关闭流
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
