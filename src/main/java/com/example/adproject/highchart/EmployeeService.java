package com.example.adproject.highchart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	//trying out to figure out how highchart works
	
	@Autowired
	private EmployeeRepository repository;
	
	public String saveEmployee(Employee employee)
	{
		repository.save(employee);
		return "saved Employee Resource";
	}
	
	public List<Employee> getAllEntry(){
		return repository.findAll();
	}
	
}
