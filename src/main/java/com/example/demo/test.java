package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:wuyuzi
 * @date:2020-06-10 14:18
 */
public class test {
    public static void main(String[] args) {
        List<Long> lists = new ArrayList<>();
        lists.add(1L);
        lists.add(2L);
        lists.add(3L);
        lists.add(4L);
        List<Long> newLong = lists.subList(0,4);
        List<Long> longs = newLong.stream().filter(Long -> Long.longValue() >= 3).collect(Collectors.toList());
        for (Long l:longs) {
            System.out.println(l);
        }
    }
}
