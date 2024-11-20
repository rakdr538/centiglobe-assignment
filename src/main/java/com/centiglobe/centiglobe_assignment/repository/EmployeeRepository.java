package com.centiglobe.centiglobe_assignment.repository;

import com.centiglobe.centiglobe_assignment.model.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path="members")
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> findByFirstName(@Param("firstName") String firstName);
}