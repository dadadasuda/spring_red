package com.dl.controller;

import com.dl.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
public class StudentController {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/set")
    public void set(@RequestBody Student stu){
        System.out.println("11111111");
        redisTemplate.opsForValue().set("student",stu);
    }

    @GetMapping("/get/{key}")
    public Student get(@PathVariable("key") String key){
        return (Student) redisTemplate.opsForValue().get(key);
    }

    @DeleteMapping("/delete/{key}")
    public boolean delete(@PathVariable("key") String key){
        redisTemplate.delete(key); // 删除key
        return redisTemplate.hasKey(key);  // 判断key 是否存在,所以返回false,因为删除了
    }

    @GetMapping("/string")
    public String stringTest(){
        redisTemplate.opsForValue().set("str","Hellow World");
        String str = (String) redisTemplate.opsForValue().get("str");
        return str;
    }

    @GetMapping("/list")
    public List<String> listTest(){
        ListOperations<String,String> list=redisTemplate.opsForList();
        list.leftPush("list","Hello");
        list.leftPush("list","World");
        list.leftPush("list","Java");
        List<String> liststr=list.range("list",0,2);
        return liststr;
    }

    /*无序集合*/
    @GetMapping("/set")
    public Set<String> setTest(){
        SetOperations<String,String> setOperations=redisTemplate.opsForSet();
        setOperations.add("set","HelloSet");
        setOperations.add("set","WorldSet");
        setOperations.add("set","JavaSet");
        setOperations.add("set","CSet");
        setOperations.add("set","C#Set");
        Set<String> set=setOperations.members("set");
        return set;
    }

    /*有序集合*/
    @GetMapping("/zset")
    public Set<String> zsetTest(){
        ZSetOperations<String,String> zSetOperations=redisTemplate.opsForZSet();
        zSetOperations.add("zset","HelloZSet1",1);
        zSetOperations.add("zset","WorldZSet3",3);
        zSetOperations.add("zset","JavaZSet2",2);
        zSetOperations.add("zset","CZSet",4);
        zSetOperations.add("zset","C#ZSet",5);
        Set<String> zset=zSetOperations.range("zset",0,20);
        return zset;
    }

    /*哈希*/
    @GetMapping("/hash")
    public void hashTest(){
        HashOperations<String,String,String> hashOperations=redisTemplate.opsForHash();
        hashOperations.put("key","hashkey","hello");
        System.out.println(hashOperations.get("key","hashkey"));
    }
}
