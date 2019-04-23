package com.feiniao.blog.service;

import com.feiniao.blog.entity.Reader;

import java.util.List;

public interface ReaderService {
    /**
     * 获得用户列表
     *
     * @return 用户列表
     */
    List<Reader> listReader();

    /**
     * 根据id查询用户信息
     *
     * @param id 用户ID
     * @return 用户
     */
    Reader getReaderById(Integer id);

    /**
     * 修改用户信息
     *
     * @param Reader 用户
     */
    void updateReader(Reader Reader);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteReader(Integer id);

    /**
     * 添加用户
     *
     * @param Reader 用户
     * @return 用户
     */
    Reader insertReader(Reader Reader);

    /**
     * 根据用户名和邮箱查询用户
     *
     * @param str 用户名或Email
     * @return 用户
     */
    Reader getReaderByNameOrEmail(String str);

    /**
     * 根据用户名查询用户
     *
     * @param name 用户名
     * @return 用户
     */
    Reader getReaderByName(String name);

    /**
     * 根据邮箱查询用户
     *
     * @param email Email
     * @return 用户
     */
    Reader getReaderByEmail(String email);
}
