package com.feiniao.blog.mapper;

import com.feiniao.blog.entity.Reader;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReaderMapper {
    /**
     * 根据ID删除
     *
     * @param readerId 用户ID
     * @return 影响行数
     */
    int deleteById(Integer readerId);

    /**
     * 添加
     *
     * @param reader 用户
     * @return 影响行数
     */
    int insert(Reader reader);

    /**
     * 根据ID查询
     *
     * @param readerId 用户ID
     * @return 用户
     */
    Reader getReaderById(Integer readerId);

    /**
     * 更新
     *
     * @param reader 用户
     * @return 影响行数
     */
    int update(Reader reader);


    /**
     * 获得用户列表
     *
     * @return  用户列表
     */
    List<Reader> listReader() ;


    /**
     * 根据用户名或Email获得用户
     *
     * @param str 用户名或Email
     * @return 用户
     */
    Reader getReaderByNameOrEmail(String str) ;

    /**
     * 根据用户名查用户
     *
     * @param name 用户名
     * @return 用户
     */
    Reader getReaderByName(String name) ;

    /**
     * 根据Email查询用户
     *
     * @param email 邮箱
     * @return 用户
     */
    Reader getReaderByEmail(String email) ;
}
