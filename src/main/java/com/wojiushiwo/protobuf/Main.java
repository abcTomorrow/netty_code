package com.wojiushiwo.protobuf;


/**
 * Created by myk
 * 2020/1/23 下午10:46
 */
public class Main {
    public static void main(String[] args) {
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(1).setName("a").build();

        System.out.println(student.getId() + "===>" + student.getName());

    }
}
