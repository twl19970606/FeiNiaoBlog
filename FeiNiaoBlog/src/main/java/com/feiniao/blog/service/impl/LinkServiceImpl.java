package com.feiniao.blog.service.impl;

import com.feiniao.blog.entity.Link;
import com.feiniao.blog.mapper.LinkMapper;
import com.feiniao.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public class LinkServiceImpl implements LinkService {
	
	@Autowired(required = false)
	private LinkMapper linkMapper;
	
	@Override
	public Integer countLink(Integer status)  {
		return linkMapper.countLink(status);
	}
	
	@Override
	public List<Link> listLink(Integer status)  {
		return linkMapper.listLink(status);
	}

	@Override
	@CachePut(value = "default", key = "'link:'+#link.linkId")
	public void insertLink(Link link)  {
		linkMapper.insert(link);
	}

	@Override
	@CacheEvict(value = "default", key = "'link:'+#id")
	public void deleteLink(Integer id)  {
		linkMapper.deleteById(id);
	}

	@Override
	@CacheEvict(value = "default", key = "'link:'+#link.linkId")
	public void updateLink(Link link)  {
		linkMapper.update(link);
	}

	@Override
	@Cacheable(value = "default", key = "'link:'+#id")
	public Link getLinkById(Integer id)  {
		return linkMapper.getLinkById(id);
	}

}
