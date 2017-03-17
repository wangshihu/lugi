package com.benchu.lu.test.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author benchu
 * @version 2017/3/16.
 */
public class App {
    @JSONField(name = "$type")
    private String sharpType = "Lutty_Test.Test+Account, Lutty_Test";

    private String name = "hello";

    public String getSharpType() {
        return sharpType;
    }

    public String getName() {
        return name;
    }

    public void setSharpType(String sharpType) {
        this.sharpType = sharpType;
    }

    public void setName(String name) {
        this.name = name;
    }


}
