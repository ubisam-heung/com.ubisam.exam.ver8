package com.ubisam.exam.api.studentClasses;

import com.ubisam.exam.domain.StudentClass;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface StudentClassRepository extends RestfulJpaRepository<StudentClass, Long>{
  
}
