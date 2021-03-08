package com.jj.sbp.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * NOT IMPLEMENTED
 * boiler class for using mybatis
 */

//@Mapper       // uncomment after adding pom dependency
//@Repository   // uncomment after adding pom dependency
public interface Mapper {

    List<?> getList(Map<String, ?> params);

    Integer getCount(Map<String, ?> params);
}