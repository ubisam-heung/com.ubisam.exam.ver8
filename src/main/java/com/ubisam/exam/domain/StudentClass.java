package com.ubisam.exam.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "example_student_class")
public class StudentClass {

  @Id
  @GeneratedValue
  private Long id;
  private String className;
  private Integer stCount;


  @Transient
  private String classNameSearch;

  @Transient
  private Integer stCountSearch;


}
