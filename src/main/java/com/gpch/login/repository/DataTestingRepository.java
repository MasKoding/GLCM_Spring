package com.gpch.login.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gpch.login.model.DataLatih;
import com.gpch.login.model.DataTesting;

@Repository("DataTestingRepository")
public interface DataTestingRepository extends JpaRepository<DataTesting, Long>{
	DataTesting findById(long id);
	@Query(nativeQuery = true,value = "SELECT * FROM data_testing where nama_penyakit=:nama_penyakit")
	Page<DataTesting> getByDisease(@Param("nama_penyakit") String nama_penyakit,Pageable page);
	
	@Query(nativeQuery = true,value = "SELECT * FROM data_testing where file_name=:file_name")
	DataTesting getByFileName(@Param("file_name") String file_name);
}
