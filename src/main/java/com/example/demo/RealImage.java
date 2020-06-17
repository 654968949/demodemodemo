package com.example.demo;

/**
 * @author:wuyuzi
 * @date:2020-06-01 15:17
 */
public class RealImage implements Image {

    private String fileName;

    public RealImage(String fileName){
        this.fileName = fileName;
        loadFromDisk(fileName);
    }

    @Override
    public void display() {
        System.out.println("Display:"+fileName);
    }

    private void loadFromDisk(String fileName){
        System.out.println("Loading "+fileName);
    }
}
