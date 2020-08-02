package com.gpch.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gpch.login.model.Ekstraksi;

@Repository("EkstraksiRepository")
public interface EkstraksiRepository extends  JpaRepository<Ekstraksi, Long>{
	Ekstraksi findById(long id);
	
	@Query(nativeQuery = true,value = "SELECT * FROM ekstraksi where file_name=:file_name")
	Ekstraksi getByFileName(@Param("file_name") String file_name);

}
