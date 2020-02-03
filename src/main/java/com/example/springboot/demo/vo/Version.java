package com.example.springboot.demo.vo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("system.version")
public class Version {
    private String version;

    public Version(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
