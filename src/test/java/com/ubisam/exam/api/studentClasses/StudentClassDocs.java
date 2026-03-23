package com.ubisam.exam.api.studentClasses;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.StudentClass;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class StudentClassDocs extends MockMvcRestDocs{

  //목적: 반 이름, 학생 수 필드를 사용하여 테스트하기 위한 메소드
  public StudentClass newEntity(String... entity) {
    StudentClass body = new StudentClass();
    body.setClassName(entity.length > 0 ? entity[0] : super.randomText("className"));
    body.setStCount(entity.length > 1 ? Integer.valueOf(entity[1]) : super.randomInt());
    return body;
  }
  
  //목적: 반 이름 변경 후 테스트를 위한 메소드
  public Map<String, Object> updateEntity(Map<String, Object> entity, String name){
    entity.put("className", name);
    return entity;
  }

  //목적: classNameSearch을 통한 검색
  public Map<String, Object> setSearch(String search){
    Map<String, Object> entity = new HashMap<>();
    entity.put("classNameSearch", search);
    return entity;
  }

  //목적: stCountSearch를 통한 검색
  public Map<String, Object> setSearch2(Integer search){
    Map<String, Object> entity = new HashMap<>();
    entity.put("stCountSearch", search);
    return entity;
  }

}
