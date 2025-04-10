package com.example.demo.entity.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {
    @ExcelProperty("工号")
    private Integer id;
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("年龄")
    private Integer age;
    @ExcelProperty("地址")
    private String addr;
    @ExcelProperty("就职状态(0-停职,1-在职,2-离职)")
    private Integer state;
}
