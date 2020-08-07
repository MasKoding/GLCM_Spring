package com.gpch.login.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gpch.login.model.Ekstraksi;
import com.gpch.login.request.EkstraksiModel;

@Repository("EkstraksiRepository")
public interface EkstraksiRepository extends  JpaRepository<Ekstraksi, Long>{
	Ekstraksi findById(long id);
	
	@Query(nativeQuery = true,value = "SELECT * FROM ekstraksi where file_name=:file_name")
	Ekstraksi getByFileName(@Param("file_name") String file_name);
	@Query(nativeQuery =true,value="SELECT DISTINCT l.nama_penyakit AS nama_latih,t.nama_penyakit AS nama_testing,e.contrast as contrast,e.homogenity as homogenity,e.energy as energy FROM ekstraksi e left JOIN data_latih l ON e.contrast = l.contrast \r\n" + 
			"left JOIN data_testing t ON l.contrast = t.contrast where e.contrast=:contrast")
	List<Map<String,Object>> getResultExtraction(@Param("contrast") Double contrast);
}
