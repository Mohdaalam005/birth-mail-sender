package com.aalam.employee.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "employee_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long employeeId;
    private String name ;
    private String email ;
    private String dob;
    private Double salary;
}

