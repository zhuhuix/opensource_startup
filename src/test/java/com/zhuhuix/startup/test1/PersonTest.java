package com.zhuhuix.startup.test1;

import com.zhuhuix.startup.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class PersonTest {

    @Test
    void test() {
        Person person = SpringContextHolder.getBean(Person.class);
        person.setName("张三");
        person.setAge("20");
        person.setSex("male");
        log.info(person.toString());
    }
}
