package cn.hanquan.bootstrapcurd.mapper;

import cn.hanquan.bootstrapcurd.entities.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper或者@MapperScan将接口扫描装配到容器中
@Mapper
public interface EmployeeMapper {
    @Select("select * from employee")
    public List<Employee> getEmployees();

    @Select("select * from employee where id=#{id}")
    public Employee getEmployeeById(Integer id);

    @Delete("delete from employee where id=#{id}")
    public int deleteEmployeeById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into employee(name,email,gender,department) values(#{name},#{email},#{gender},#{department})")
    public int insertEmployee(Employee employee);

    @Update("update employee set name=#{name}, email=#{email}, gender=#{gender}, department=#{department} where id=#{id}")
    public int updateEmployee(Employee employee);
}
