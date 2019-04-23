package com.feiniao.blog.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Reader implements Serializable {

    private static final long serialVersionUID = 1216140340780214479L;

    private String readerId;

    private String readerName;

    private String readerPass;

    private String readerAvatar;

    private Integer readerStatus;

    private String readerEmail;

}
