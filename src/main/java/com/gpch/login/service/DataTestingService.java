package com.gpch.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.gpch.login.model.DataLatih;
import com.gpch.login.model.DataTesting;
import com.gpch.login.repository.DataLatihRepository;
import com.gpch.login.repository.DataTestingRepository;

@Service("DataTestingService")
public class DataTestingService{
	@Autowired
	private DataTestingRepository dataTestingRepo;
	
    protected JpaRepository<DataTesting, Long> getRepository() {
        return dataTestingRepo;
    }

    public Page<DataTesting> getList(String nama_penyakit,Integer pageNumber) {
        PageRequest pageRequest =
                PageRequest.of(pageNumber - 1, 10, Sort.Direction.ASC, "id");

        return dataTestingRepo.getByDisease(nama_penyakit, pageRequest);
    }

    public DataTesting save(DataTesting dataTesting) {
        return dataTestingRepo.save(dataTesting);
    }
    
    public DataTesting findById(long id) {
    	return dataTestingRepo.findById(id);
    }
    

    public DataTesting findByFileName(String fileName) {
    	return dataTestingRepo.getByFileName(fileName);
    }
    
    
    public void delete(long id) {
    	dataTestingRepo.deleteById(id);
    }

    public void deleteAll() {
    	dataTestingRepo.deleteAll();
    }

	public List<DataTesting> getAll(){
		return dataTestingRepo.findAll();
	}
}
