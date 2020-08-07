package com.gpch.login.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpch.login.model.DataLatih;
import com.gpch.login.model.Ekstraksi;
import com.gpch.login.repository.EkstraksiRepository;
import com.gpch.login.request.EkstraksiModel;

@Service("EkstraksiService")
public class EkstraksiService {
	@Autowired
	EkstraksiRepository ekstraksiRepo;
	
    public Ekstraksi save(Ekstraksi ekstraksi) {
        return ekstraksiRepo.save(ekstraksi);
    }
    
    public Ekstraksi findById(long id) {
    	return ekstraksiRepo.findById(id);
    }

    public List<Map<String,Object>> getResult(Double contrast){
    	return ekstraksiRepo.getResultExtraction(contrast);
    	
    }

    public Ekstraksi findByFileName(String fileName) {
    	return ekstraksiRepo.getByFileName(fileName);
    }
    
    
    public void delete(long id) {
    	ekstraksiRepo.deleteById(id);
    }
    public void deleteAll() {
    	ekstraksiRepo.deleteAll();
    }

    

	public List<Ekstraksi> getAll(){
		return ekstraksiRepo.findAll();
	}
}
