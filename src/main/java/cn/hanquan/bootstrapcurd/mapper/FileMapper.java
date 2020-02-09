package cn.hanquan.bootstrapcurd.mapper;

import cn.hanquan.bootstrapcurd.entities.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper或者@MapperScan将接口扫描装配到容器中
@Mapper
public interface FileMapper {
    @Select("select * from file")
    public List<File> getFile();

    @Select("select * from file where id=#{id}")
    public File getFileById(Integer id);

    @Delete("delete from file where id=#{id}")
    public int deleteFileById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")// 自动生成id
    @Insert("insert into file(name,user_name,upload_time) values(#{name},#{userName},#{uploadTime})")
    public int insertFile(File excelTable);

    @Update("update file set name=#{name}, user_name=#{userName}, upload_time=#{uploadTime} where id=#{id}")
    public int updateFileById(File excelTable);
}
