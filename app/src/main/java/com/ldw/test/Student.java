package com.ldw.test;

/**
 * @date 2020/5/28 14:10
 * @user 威威君
 */
public class Student {

    /**
     * sex : 男
     * name : XX
     * age : 19
     */

    public String sex;
    public String name;
    public int age;

    @Override
    public String toString() {
        return "Student{" +
                "sex='" + sex + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
