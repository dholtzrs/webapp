package br.com.sb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface BaseController<MODEL> {
	
	public List<MODEL> findByFilter( Map<String,String> query) throws Exception;
	public ResponseEntity<MODEL> findById(Long id);
	public List<MODEL> save(List<MODEL> list);
	
	
	

}
