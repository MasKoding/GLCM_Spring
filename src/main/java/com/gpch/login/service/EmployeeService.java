package com.gpch.login.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gpch.login.model.EmployeeModel;
import com.gpch.login.repository.EmployeeRepository;

@Service("employeeService")
public class EmployeeService {
		private EmployeeRepository employeeRepo;
		
		
		public Object getAll() {
			String url = "http://localhost:18183/api/employee";
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, null, Object.class);
			return result.getBody();
		}
		public Object save(EmployeeModel employee) {
			String url = "http://localhost:18183/api/employee/add";
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<EmployeeModel> request = new HttpEntity<>(employee);
			ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
			return result.getBody();
		}
		public Object edit(EmployeeModel employee) {
			String url = "http://localhost:18183/api/employee/edit";
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<EmployeeModel> request = new HttpEntity<>(employee);
			ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.PUT, request, Object.class);
			return result.getBody();
		}
		public Object findId(long id) {
			String url = "http://localhost:18183/api/employee/findById/"+id;
			RestTemplate restTemplate = new RestTemplate();
			
			ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, null, Object.class);
			return result.getBody();
		}
		public Object delete(long id) {
			String url = "http://localhost:18183/api/employee/delete/"+id;
			RestTemplate restTemplate = new RestTemplate();
			
			ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.DELETE, null, Object.class);
			return result.getBody();
		}
}
