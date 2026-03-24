package com.ubisam.exam.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "example_student")
public class Student {

  @Id
  @GeneratedValue
  private Long id;
  //테스트를 할 때 선언한 필드 
  private Integer stNumber;
  private String name;
  private String address;
  //1명의 학생이 1개의 반
  //1개의 반에는 여러명의 학생
  // @ManyToOne
  // private StudentClass studentClass;
}
