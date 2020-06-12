package com.zhuhuix.startup.testuserjpa;

import com.zhuhuix.startup.security.domain.User;
import com.zhuhuix.startup.security.repository.UserRepository;
import com.zhuhuix.startup.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootTest
@Slf4j
public class UserJPATest {
    @Test
    void test() {
        UserRepository userRepository = SpringContextHolder.getBean(UserRepository.class);
        User user = userRepository.findById(7L).get();
        System.out.println(user.toString());
    }

    @Test
    void test2(){
        UserRepository userRepository = SpringContextHolder.getBean(UserRepository.class);
        User user = userRepository.findByIdNative(7L);
        System.out.println(user.toString());
    }

    @Test
    void test3(){
        UserRepository userRepository = SpringContextHolder.getBean(UserRepository.class);
        User user = userRepository.findByIdHql(7L);
        System.out.println(user.toString());
    }

    @Test
    void test4(){
        UserRepository userRepository = SpringContextHolder.getBean(UserRepository.class);
       Page<User> page1= userRepository.findAll( PageRequest.of(0,10));
       page1.forEach(user -> System.out.println(user.toString()));
       Page<User> page2= userRepository.findAll( PageRequest.of(0,10,Sort.by("id")));
       page2.forEach(user -> System.out.println(user.toString()));
    }
}
