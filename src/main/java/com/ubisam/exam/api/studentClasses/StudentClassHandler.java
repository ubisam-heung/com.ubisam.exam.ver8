package com.ubisam.exam.api.studentClasses;

import java.io.Serializable;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.StudentClass;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class StudentClassHandler {


  @HandleBeforeRead
  public void beforeRead(StudentClass e, Specification spec) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandlebeforeRead] "+e);

    JpaSpecificationBuilder<StudentClass> query = JpaSpecificationBuilder.of(StudentClass.class);
    query.where()
      .and().eq("className", e.getClassNameSearch())
      .and().eq("stCount", e.getStCountSearch())
      .build(spec);
  }

  @HandleAfterRead
  public void afterRead(StudentClass e, Serializable r) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterRead] "+e);
    System.out.println("[HandleafterRead] "+r);
  }

  @HandleBeforeCreate
  public void beforeCreate(StudentClass e) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandlebeforeCreate] "+e);
  }

  @HandleBeforeSave
  public void beforeSave(StudentClass e) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandlebeforeSave] "+e);
  }
  
  @HandleBeforeDelete
  public void beforeDelete(StudentClass e) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandlebeforeDelete] "+e);
  }

  @HandleAfterCreate
  public void afterCreate(StudentClass e) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterCreate] "+e);
  }

  @HandleAfterSave
  public void afterSave(StudentClass e) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterSave] "+e);
  }
  
  @HandleAfterDelete
  public void afterDelete(StudentClass e) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterDelete] "+e);
  }
  
}

/*

의문점1: 아래 코드를 사용하면 단일 검색 중에서도 className을 사용한 검색밖에 하질 못한다.
       사용자는 여러 조건을 검색하거나, 한 번에 여러 검색을 처리하고 싶다. 방법은 무엇일까?

해결방안: 키워드만 넘겨주지 말고 검색조건을 넣어주어 같이 보내주는 방법을 처리한다.
         Class에 @Trainsient로 searchType을 추가한다. Docs와 Handler를 수정한다.

    //  select * from where name = '키워드'
    query.where()
        .and().eq("className", e.getKeyword())
        .build(spec);

*/

/*

의문점2: 위 의문점1을 해결한 후 테스트하니 도메인에 필드가 Integer와 String이라 아래 오류가 발생한다.
        Cannot compare left expression of type 'java.lang.Integer' with right expression of type 'java.lang.String'
        또한 전체 목록을 불러올 시 아무값도 보내주지 않으면 Request processing failed: java.lang.reflect.UndeclaredThrowableException
        오류가 난다. 해결방안은?

해결방안: 그럼 StudentClass.java에 설정한 필드의 타입을 불러온 후 가공하여 query에 집어넣는 방식을 찾아본다.
        아래 코드는 위 내용을 토대로 AI에게 부탁한 코드이다. 
        Object value = keyword;
        if (searchType != null && !searchType.isEmpty() && keyword != null && !keyword.isEmpty()) {
            Field field = StudentClass.class.getDeclaredField(searchType);
            Class<?> fieldType = field.getType();
            if (fieldType == Integer.class) {
                value = Integer.valueOf(keyword);
            }
        }

*/

/*

의문점3: 아래 코드를 사용하면 Long, Integer, Boolean 등 수많은 타입들에 대한 조건을 일일이 추가해야한다.
        한 번에 처리하는 방법이 없을까?

해결방안: https:// docs.spring.io/spring-framework/reference/6.2/core/validation/convert.html#core-convert-ConversionService-API
        위 사이트에 내용에 따르면 ConversionService 라는 것을 이용하여 한 번에 처리가 가능하다고 한다.
        코드는 ai에게 아래 코드를 ConversionService를 사용하여 가공해달라고 하니 처리해준 코드이다.

Object value = keyword;
if (searchType != null && !searchType.isEmpty() && keyword != null && !keyword.isEmpty()) {
    Field field = StudentClass.class.getDeclaredField(searchType);
    Class<?> fieldType = field.getType();
    if (fieldType == Integer.class) {
        value = Integer.valueOf(keyword);
    }
}
*/