package cn.hanquan.bootstrapcurd.mapper;

import cn.hanquan.bootstrapcurd.entities.ExcelTable;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper或者@MapperScan将接口扫描装配到容器中
@Mapper
public interface ExcelTableMapper {
    @Select("select * from excel_table")
    public List<ExcelTable> getExcelTable();

    @Select("select * from excel_table where id=#{id}")
    public ExcelTable getExcelTableById(Integer id);

    @Delete("delete from excel_table where id=#{id}")
    public int deleteExcelTableById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")// 自动生成id
    @Insert("insert into excel_table(firstCell,secondCell,thirdCell,fourthCell) values(#{firstCell},#{secondCell},#{thirdCell},#{fourthCell})")
    public int insertExcelTable(ExcelTable excelTable);

    @Update("update excel_table set firstCell=#{firstCell}, secondCell=#{secondCell}, thirdCell=#{thirdCell}, fourthCell=#{fourthCell} where id=#{id}")
    public int updateExcelTableById(ExcelTable excelTable);
}
