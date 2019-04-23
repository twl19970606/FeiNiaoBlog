package com.feiniao.blog.service.impl;

import com.feiniao.blog.entity.Reader;
import com.feiniao.blog.mapper.ReaderMapper;
import com.feiniao.blog.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService {
    @Autowired
    private ReaderMapper readerMapper;

    @Override
    public List<Reader> listReader() {
        List<Reader> readerList =readerMapper.listReader();
        return readerList;
    }

    @Override
    @Cacheable(value = "default", key = "'reader:'+#id")
    public Reader getReaderById(Integer id) {
        Reader reader = readerMapper.getReaderById(id);
        return  reader;
    }

    @Override
    @CacheEvict(value = "default", key = "'reader:'+#reader.readerId")
    public void updateReader(Reader reader) {
        readerMapper.update(reader);
    }

    @Override
    @CacheEvict(value = "default", key = "'reader:'+#id")
    public void deleteReader(Integer id) {
        readerMapper.deleteById(id);
    }

    @Override
    @CachePut(value = "default", key = "'reader:'+#result.readerId")
    public Reader insertReader(Reader reader) {
        readerMapper.insert(reader);
        return  reader;
    }

    @Override
    public Reader getReaderByNameOrEmail(String str) {
        return readerMapper.getReaderByNameOrEmail(str);
    }

    @Override
    public Reader getReaderByName(String name) {
        return readerMapper.getReaderByName(name);
    }

    @Override
    public Reader getReaderByEmail(String email) {
        return readerMapper.getReaderByEmail(email);
    }
}
