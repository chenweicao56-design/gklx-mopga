package com.gklx.mopga.study.redis.manager;

import com.gklx.mopga.study.redis.entity.Person;
import com.redis.om.spring.repository.RedisDocumentRepository;

import java.util.List;

public interface PeopleRepository extends RedisDocumentRepository<Person,String> {

    /**
     * 按 personalStatement 全文搜索 + age 精确匹配
     * 使用 RediSearch 语法:
     *   @personalStatement:(text)  -- 全文搜索
     *   @age:[42 42]              -- 数值精确匹配
     *   空格表示 AND 关系
     */
    @com.redis.om.spring.annotations.Query("@personalStatement:*?0* @age:[?1 ?1]")
    List<Person> findByPersonalStatementAndAge(String statement, Integer age);

    /**
     * 仅按 personalStatement 全文搜索
     */
    @com.redis.om.spring.annotations.Query("@personalStatement:*?0*")
    List<Person> findByPersonalStatement(String statement);
}