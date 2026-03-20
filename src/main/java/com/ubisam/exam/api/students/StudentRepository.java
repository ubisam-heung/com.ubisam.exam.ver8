package com.ubisam.exam.api.students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ubisam.exam.domain.Student;
import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student>{
  
  List<Student> findByName(String name);
  List<Student> findByStNumber(Integer stNumber);
  List<Student> findByAddress(String address);
}
