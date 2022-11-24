package com.aalam.employee.service;

import com.aalam.employee.entity.EmployeeEntity;
import com.aalam.employee.model.EmployeeRequest;
import com.aalam.employee.model.EmployeeResponse;
import com.aalam.employee.repository.EmployeeEntityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class EmployeeService {
    private final EmployeeEntityRepository employeeEntityRepository;

    @Autowired
    public EmployeeService(EmployeeEntityRepository employeeEntityRepository) {
        this.employeeEntityRepository = employeeEntityRepository;
    }


    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName(employeeRequest.getName());
        employeeEntity.setDob(employeeRequest.getDob());
        employeeEntity.setSalary(employeeRequest.getSalary());
        employeeEntity.setEmail(employeeRequest.getEmail());
        employeeEntityRepository.save(employeeEntity);
        log.info("employee data is created !!!!");
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setEmployeeId(employeeEntity.getEmployeeId());
        return employeeResponse;
    }



    public List<EmployeeEntity>getAllEmployees(){
        return employeeEntityRepository.findAll();
    }


}
