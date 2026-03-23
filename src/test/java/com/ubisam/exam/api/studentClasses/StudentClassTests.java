package com.ubisam.exam.api.studentClasses;

import static io.u2ware.common.docs.MockMvcRestDocs.delete;
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
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam.domain.StudentClass;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentClassTests {
  
  @Autowired
  private MockMvc mvc;

  @Autowired
  private StudentClassDocs docs;
  @Autowired
  private StudentClassRepository studentClassRepository;

  @Test
  void contextLoads() throws Exception{

    //Crud - C
    mvc.perform(post("/api/studentClasses").content(docs::newEntity, "1반"))
    .andDo(print()).andExpect(is2xx()).andDo(result(docs::context, "entity1"));

    //Crud - R
    String url = docs.context("entity1", "$._links.self.href");
    mvc.perform(post(url)).andExpect(is2xx());

    //Crud - U
    Map<String, Object> body = docs.context("entity1", "$");
    mvc.perform(put(url).content(docs::updateEntity, body, "5반")).andExpect(is2xx());

    //Crud - D
    mvc.perform(delete(url)).andExpect(is2xx());

  }

    //Handler 테스트
  @Test
  void contextLoads2() throws Exception{

		List<StudentClass> result;
		boolean hasResult;

		//40개 엔티티 추가
		List<StudentClass> studentClassLists = new ArrayList<>(); 
		for ( int i = 1; i <= 40; i++){
			studentClassLists.add(docs.newEntity( i+"반", i+""));
		}
		studentClassRepository.saveAll(studentClassLists);

    //1) 반 이름으로 검색
    JpaSpecificationBuilder<StudentClass> query = JpaSpecificationBuilder.of(StudentClass.class);
		query.where().and().eq("className", "7반");
		result = studentClassRepository.findAll(query.build());
    //쿼리 결과 행 1개를 찾고 그 행의 className필드가 "7반" 인지 검사
    hasResult = result.stream().anyMatch(u -> "7반".equals(u.getClassName()));
    assertEquals(true, hasResult);

    //2) 학생 수 으로 검색
    JpaSpecificationBuilder<StudentClass> query1 = JpaSpecificationBuilder.of(StudentClass.class);
		query1.where().and().eq("stCount", 20);
		result = studentClassRepository.findAll(query1.build());
    //쿼리 결과 행 1개를 찾고 그 행의 className필드가 "7반" 인지 검사
    hasResult = result.stream().anyMatch(u -> 20 == u.getStCount());
    assertEquals(true, hasResult);
    
  }

  @Test
  void contextLoads3 () throws Exception{

		//40명의 유저 추가
		List<StudentClass> userList = new ArrayList<>(); 
		for ( int i = 1; i <= 40; i++){
			userList.add(docs.newEntity( i+"반", i+""));
		}
		studentClassRepository.saveAll(userList);

    String url = "/api/studentClasses/search";

    //Search - 반 이름
    mvc.perform(post(url).content(docs::setSearch, "className", "5반"))
    .andExpect(is2xx());
    
    //Search - 학생 수
    mvc.perform(post(url).content(docs::setSearch, "stCount", "8"))
    .andExpect(is2xx());

    //Search - 페이지네이션 - 5개씩 8페이지
    mvc.perform(post(url).content(docs::setSearch, "className", "")
    .param("size", "5")).andExpect(is2xx()).andDo(print());

    //Search - 정렬 className, desc
    mvc.perform(post(url).content(docs::setSearch, "", "")
    .param("sort", "className,desc")).andExpect(is2xx());
    
  }

}
