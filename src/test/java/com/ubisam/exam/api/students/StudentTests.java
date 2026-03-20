package com.ubisam.exam.api.students;

import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.get;
import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.print;
import static io.u2ware.common.docs.MockMvcRestDocs.put;
import static io.u2ware.common.docs.MockMvcRestDocs.result;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam.domain.Student;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private StudentDocs docs;

  @Autowired
  private StudentRepository studentRepository;

  //CRUD - 테스트
  @Test
  void contextLoads() throws Exception{
    //Crud - C | 학번을 지정
    mvc.perform(post("/api/students").content(docs::newEntity, "20260320"))
    .andDo(print()).andExpect(is2xx()).andDo(result(docs::context, "entity1"));

    //Crud - R
    String url = docs.context("entity1", "$._links.self.href");
    mvc.perform(get(url)).andExpect(is2xx());

    //Crud - U
    Map<String, Object> body = docs.context("entity1", "$");
    mvc.perform(put(url).content(docs::updateEntity, body, "20260322")).andExpect(is2xx());

    //Crud - D
    mvc.perform(delete(url)).andExpect(is2xx());
  }

  //Handler 테스트
  @Test
  void contextLoads2() throws Exception {

    Specification<Student> spec;
		List<Student> result;
		boolean hasResult;

		//40명의 유저 추가
		List<Student> userList = new ArrayList<>(); 
		for ( int i = 1; i <= 40; i++){
			userList.add(docs.newEntity("2026032"+i, "길동"+i, "서울시"+i));
		}
		studentRepository.saveAll(userList);

    //1) 학번 검색 - 20260322인 학번을 가진 학생
    JpaSpecificationBuilder<Student> query = JpaSpecificationBuilder.of(Student.class);
    spec = query.where().and().eq("stNumber", 20260322).build();
    result = studentRepository.findAll(spec);
    hasResult = result.stream().anyMatch(u -> 20260322 == u.getStNumber());
    assertEquals(true, hasResult);

    //2) 이름 검색 - 길동5 이름을 가진 학생
    JpaSpecificationBuilder<Student> query1 = JpaSpecificationBuilder.of(Student.class);
    spec = query1.where().and().eq("name", "길동5").build();
    result = studentRepository.findAll(spec);
    hasResult = result.stream().anyMatch(u -> "길동5".equals(u.getName()));
    assertEquals(true, hasResult);

    //3) 주소 검색 - 서울시22 주소를 가진 학생
    JpaSpecificationBuilder<Student> query2 = JpaSpecificationBuilder.of(Student.class);
    spec = query2.where().and().eq("address", "서울시22").build();
    result = studentRepository.findAll(spec);
    hasResult = result.stream().anyMatch(u -> "서울시22".equals(u.getAddress()));
    assertEquals(true, hasResult);

  }

  @Test
  void contextLoads3 () throws Exception{

		//40명의 유저 추가
		List<Student> userList = new ArrayList<>(); 
		for ( int i = 1; i <= 40; i++){
			userList.add(docs.newEntity("2026032"+i, "길동"+i, "서울시"+i));
		}
		studentRepository.saveAll(userList);

    //Search - 학번
    mvc.perform(get("/api/students/search/findByStNumber")
    .param("stNumber", "20260321")).andExpect(is2xx());
    //Search - 이름
    mvc.perform(get("/api/students/search/findByName")
    .param("name", "길동3")).andExpect(is2xx());
    //Search - 주소
    mvc.perform(get("/api/students/search/findByAddress")
    .param("address", "서울시5")).andExpect(is2xx());

    //Search - 페이지네이션 - 5개씩 8페이지
    mvc.perform(get("/api/students").param("size", "5")).andExpect(is2xx());

    //Search - 정렬 stNumber, desc
    mvc.perform(get("/api/students").param("sort", "stNumber,desc")).andExpect(is2xx());
  }

}
