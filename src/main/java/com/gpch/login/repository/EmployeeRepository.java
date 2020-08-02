package com.gpch.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gpch.login.model.EmployeeModel;



@Repository("employeeRepository")
public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long>{

}
