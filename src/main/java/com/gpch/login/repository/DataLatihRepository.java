package com.gpch.login.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gpch.login.model.DataLatih;


@Repository("DataLatihRepository")
public interface DataLatihRepository extends JpaRepository<DataLatih, Long>{
		DataLatih findById(long id);
		@Query(nativeQuery = true,value = "SELECT * FROM data_latih where nama_penyakit=:nama_penyakit")
		Page<DataLatih> getByDisease(@Param("nama_penyakit") String nama_penyakit,Pageable page);
		
		@Query(nativeQuery = true,value = "SELECT * FROM data_latih where file_name=:file_name")
		DataLatih getByFileName(@Param("file_name") String file_name);
}
