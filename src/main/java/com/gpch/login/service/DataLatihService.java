package com.gpch.login.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.gpch.login.model.DataLatih;
import com.gpch.login.model.Role;
import com.gpch.login.model.User;
import com.gpch.login.repository.DataLatihRepository;

@Service("DataLatihService")
public class DataLatihService {
	@Autowired
	private DataLatihRepository dataLatihRepo;
	
    protected JpaRepository<DataLatih, Long> getRepository() {
        return dataLatihRepo;
    }

    public Page<DataLatih> getList(String nama_penyakit,Integer pageNumber) {
        PageRequest pageRequest =
                PageRequest.of(pageNumber - 1, 10, Sort.Direction.ASC, "id");

        return dataLatihRepo.getByDisease(nama_penyakit, pageRequest);
    }

    public DataLatih save(DataLatih dataLatih) {
        return dataLatihRepo.save(dataLatih);
    }
    
    public DataLatih findById(long id) {
    	return dataLatihRepo.findById(id);
    }
    

    public DataLatih findByFileName(String fileName) {
    	return dataLatihRepo.getByFileName(fileName);
    }
    
    
    public void delete(long id) {
    	dataLatihRepo.deleteById(id);
    }
    public void deleteAll() {
    	dataLatihRepo.deleteAll();
    }

    

	public List<DataLatih> getAll(){
		return dataLatihRepo.findAll();
	}
}
