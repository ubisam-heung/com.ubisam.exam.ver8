package com.ubisam.exam.api.students;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.Student;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class StudentDocs extends MockMvcRestDocs{

  //목적: 학번, 이름, 주소를 사용하여 테스트하기 위한 메소드
  public Student newEntity(String... entity) {
    Student body = new Student();
    body.setStNumber(entity.length > 0 ? Integer.valueOf(entity[0]) : super.randomInt());
    body.setName(entity.length > 1 ? entity[1] : super.randomText("name"));
    body.setAddress(entity.length > 2 ? entity[2] : super.randomText("address"));
    return body;
  }
  
  //목적: 이름 변경 후 테스트를 위한 메소드
  public Map<String, Object> updateEntity(Map<String, Object> entity, String name){
    entity.put("name", name);
    return entity;
  }

}
