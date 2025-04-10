package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.pojo.Employee;
import com.example.demo.service.EmpService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/getUser")
public class EmpController {
    private final EmpService service;

    /*分页查询*/
    @GetMapping(value = "/getEmpPageInfo/{page}/{size}")
    public Page<Employee> getEmpPageInfo(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return service.getEmpPageInfo(page, size);
    }

    /*查询全部在用/停用*/
    @GetMapping(value = "/getAllEmpInfo")
    public List<Employee> getAllEmpInfo() {
        return service.getAllEmpInfo();
    }
    /*查询已经删除的*/
    @GetMapping(value = "/getDelEmpInfo")
    public List<Employee> getDelEmpInfo() {
        return service.getDelEmpInfo();
    }

    /*导出功能*/
    @GetMapping(value = "/empFillSimple")
    public void downExcel(String name) {
        service.downExcel(name);
    }

    /*修改个人信息*/
    @PostMapping(value = "/updateNewEmp")
    public void updateNewEmp(@RequestBody Employee emp) {
        service.updateNewEmp(emp);
    }

    /*删除/停用个人信息*/
    @GetMapping(value = "/upStateEmp")
    public void upStateEmp(Integer id, Integer state) {
        service.upStateEmp(id, state);
    }

    /*保存个人信息*/
    @PostMapping(value = "/saveNewEmp")
    public void saveNewEmp(@RequestBody Employee emp) {
        service.saveNewEmp(emp);
    }

}
