package com.netdisk.test;

import com.netdisk.entity.File;
import com.netdisk.entity.User;
import com.netdisk.mapper.UserMapper;
import com.netdisk.service.ShareFileService;
import com.netdisk.service.impl.FileServiceImpl;
import com.netdisk.service.impl.UserServiceImpl;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class
UserTest {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ShareFileService sharedFileService;

    @Autowired
    private RedisTemplate<String,String>redisTemplate;

    static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Test
    public void test1(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername("admin");
        User user2 = mapper.findUserByUsername(user);
        System.out.println(user2);
    }

    @Test
    public void test2(){
        User user = new User();
        user.setUsername("admin");
        System.out.println(userService.userExist(user));
    }

    @Test
    public void test3(){
        User user = new User();
        user.setUsername("guest");
        List<File> files = fileService.findAllByUsername(user);
        for (File file : files) {
            System.out.println(file);
        }
    }

    @Test
    public void test4(){
        User user = new User();
        user.setUsername("admin");
        user.setToken("853037487113633792");
        boolean checkToken = userService.checkToken(user);
        System.out.println(checkToken);
    }

    @Test
    public void test5(){
        User user = new User();
        user.setNickname("测试账号");
        user.setId("858008356588093440");
        int flag = userService.updateUserInfo(user);
        System.out.println(flag);
    }

    @Test
    public void test6(){
        User user = new User();
        user.setId("1水dasae放到各省市的广泛地23");
        int i = userService.insertUserInfo(user);
        System.out.println(i);
    }

    @Test
    public void test7(){
        File file = new File();
        file.setId("1234");
        file.setName("123.JPG");
        file.setUsername("4515");
        file.setDetail("detail");
        System.out.println(fileService.deleteFileInfo(file));
    }

    @Test
    public void test8(){
        User user = new User();
        user.setUsername("guest");
        user.setPassword("guest");
        System.out.println(userService.updateUserInfo(user));
    }

    @Test
    public void test9(){
        User user = new User();
        user.setLevel(100);
        List<User> userList = userService.findUsers(user);
        for (User userInfo : userList) {
            System.out.println(userInfo);
        }
    }

    @Test
    public void test10(){
        User user = new User();
        user.setUsername("test");
        System.out.println(userService.userExist(user));
    }

    @Test
    public void test11(){
        UserMapper mapper = context.getBean(UserMapper.class);
        User user = new User();
        user.setUsername("test");
        System.out.println(mapper.findUserByUsername(user));

    }

    @Test
    public void test12(){
        File file = new File();
        file.setPath("/netdisk\\upload\\admin\\a.txt");
        System.out.println(fileService.deleteFileInfo(file));
    }

    @Test
    public void test13(){
        UUID uuid = UUID.randomUUID();

        System.out.println(uuid.toString());
    }

    @Test
    public void test14(){
//        String add = sharedFileService.add("123", ,"1234",1);
//        System.out.println(add);
    }

    @Test
    public void test15(){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("abc","abc");
    }

    @Test
    public void test16(){

    }

    public void AA(Integer i){
        i++;
    }

    @Test
    public void test(){



    }
}
