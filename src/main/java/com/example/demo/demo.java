package com.example.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:wuyuzi
 * @date:2020-05-28 10:14
 */
public class demo {



    public static void main(String[] args) {
        Map<person,Integer> man = new HashMap<>();
        man.put(new person("张三",12),111);
        man.put(new person("李四",14),222);
        man.put(new person("张三",12),333);
        man.put(new person("王五",15),444);
        man.forEach((key,value)->{
            System.out.println(key+"::"+value);
        });
    }

}
class person{

    public person(String name,Integer age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    private String name;
    private Integer age;
    public String toString(){
        return "{"+name+","+age+"}";
    }

    @Override
    public int hashCode() {
        return name.hashCode()+age*10;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof person)){
            throw new ClassCastException("类型不匹配");
        }
        person man = (person)obj;
        return this.name.equals(man.getName())&&this.age==man.getAge();
    }
}
