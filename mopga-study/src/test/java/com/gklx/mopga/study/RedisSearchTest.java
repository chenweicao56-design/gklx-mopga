package com.gklx.mopga.study;

import com.gklx.mopga.study.redis.entity.Person;
import com.gklx.mopga.study.redis.entity.Person$;
import com.gklx.mopga.study.redis.manager.PeopleRepository;
import com.redis.om.spring.search.stream.EntityStream;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class RedisSearchTest {


    @Autowired
    EntityStream entityStream;

    @Resource
    private PeopleRepository peopleRepository;

    @Test
    void testSearchByStatementAndAge() {
        List<Person> results = peopleRepository.findByPersonalStatementAndAge("save the world", 42);
        System.out.println("查询结果: " + results);
    }

    @Test
    void testSearchByStatement() {
        // EntityStream 方式
        List<Person> streamResults = entityStream.of(Person.class)
                .filter(Person$.PERSONAL_STATEMENT.containing("Great"))
                .collect(Collectors.toList());
        System.out.println("EntityStream 结果: " + streamResults);

        // @Query 方式
        List<Person> queryResults = peopleRepository.findByPersonalStatement("save the world");
        System.out.println("@Query 结果: " + queryResults);
    }
}
