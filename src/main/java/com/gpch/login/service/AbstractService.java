package com.gpch.login.service;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gpch.login.request.AbstractModel;


public abstract class AbstractService<T extends AbstractModel<Long>, Long extends Serializable> {

private static final int PAGE_SIZE = 5;
	    protected abstract JpaRepository<T, Long> getRepository();
	
	    public Page<T> getList(Integer pageNumber) {
	        PageRequest pageRequest =
	                PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "id");
	
	        return getRepository().findAll(pageRequest);
	    }
	
}

