package com.example.demo.service;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.mapper.EmpMapper;
import com.example.demo.entity.pojo.Employee;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class EmpService {
    private final EmpMapper empMapper;

    /*分页查询*/
    public Page<Employee> getEmpPageInfo(Integer page, Integer size) {
        return empMapper.selectPage(new Page<Employee>(page, size), new LambdaQueryWrapper<Employee>().ne(Employee::getState,2).orderBy(true, true, Employee::getId));
    }

    /*查询全部*/
    public List<Employee> getAllEmpInfo() {
        return empMapper.selectList(new LambdaQueryWrapper<Employee>().ne(Employee::getState, 2).orderBy(true, false, Employee::getId));
    }

    /*查询已经删除的*/
    public List<Employee> getDelEmpInfo() {
        return empMapper.selectList(new LambdaQueryWrapper<Employee>().eq(Employee::getState, 2).orderBy(true, false, Employee::getId));
    }

    /*查询当前最大ID*/
    public Integer selEmpMaxID() {
        return empMapper.selEmpMaxID();
    }

    /*
     * 保存个人信息
     * */
    @Transactional
    public void saveNewEmp(Employee emp) {
        if (emp == null) {
            log.error("入参为空");
            throw new RuntimeException("入参为空");
        }
        Integer i = this.selEmpMaxID();
        if (i == null) {
            i = 1;
        }
        emp.setId(i);
        empMapper.insert(emp);
    }

    /*
     * 个人信息状态修改
     * */
    @Transactional
    public void upStateEmp(Integer id, Integer state) {
        if (id == null) {
            log.error("ID入参为空");
            throw new RuntimeException("ID入参为空");
        }
        if (state == null) {
            log.error("STATE入参为空");
            throw new RuntimeException("STATE入参为空");
        }
        empMapper.update(null, new LambdaUpdateWrapper<Employee>().eq(Employee::getId, id).set(Employee::getState, state));
    }

    /*
     * 修改个人信息
     * */
    @Transactional
    public void updateNewEmp(Employee emp) {
        if (emp == null) {
            log.error("入参为空");
        }
        empMapper.update(null, new LambdaUpdateWrapper<Employee>()
                .eq(Employee::getId, emp.getId())
                .set(Employee::getName, emp.getName())
                .set(Employee::getAge, emp.getAge())
                .set(Employee::getAddr, emp.getAddr())
        );
    }

    /*
     * 导出EXECL文件
     * */
    public void downExcel(String name) {
        File path = new File("C:\\Users\\Admin\\Desktop");
        String fileName = path.getPath() + "\\" + name + ".xlsx";
        List<Employee> records = this.getAllEmpInfo();
        EasyExcel.write(fileName, Employee.class).sheet("模板").doWrite(records);
    }

}
