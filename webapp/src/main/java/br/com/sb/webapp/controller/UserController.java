package br.com.sb.webapp.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sb.controller.BaseController;
import br.com.sb.controller.BaseRestController;
import br.com.sb.webapp.model.Endereco;
import br.com.sb.webapp.model.User;

@RestController
@RequestMapping("/rest/user")
public class UserController extends BaseRestController<User> implements BaseController<User> {



	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable(value = "id") Long id) {
		User user = new User();
		user.setId((Long)id);
		user.setName("Luciano Wild");
		
//		return user;
		return ResponseEntity.ok().body(user);

	}

	@PostMapping
	public  List<User> save(@RequestBody List<User> list) {
		Long cont = 1L;
		for (User user : list) {
			user.setId(cont);
			for(Endereco e: user.getEnderecos()) {
				e.setId(cont);
			}			
			cont++;
		}	
		
		return list;
	}
	
	@GetMapping
	public List<User> findByFilter(@RequestParam Map<String, String> query) throws Exception{
		List<User> list = new ArrayList<User>();
		User user = new User();
		user.setId(1L);
		user.setName("Luciano Wild");
		Endereco end = new Endereco();
		end.setPais("Brasil");
		end.setEstado("RS");
		end.setCidade("Santa Cruz");
		user.getEnderecos().add(end);
		list.add(user);

		user = new User();
		user.setId(2L);
		user.setName("Luis Fernando");
		end = new Endereco();
		end.setPais("Brasil");
		end.setEstado("RS");
		end.setCidade("Sao Leopoldo");
		user.getEnderecos().add(end);
		list.add(user);
		
		
		for(Entry<String, String> entry: query.entrySet()) {
			list = list.stream()
						.filter(u -> {
							try {
								
								if(PropertyUtils.getProperty(u, entry.getKey()).getClass().equals(String.class))
									return BeanUtils.getProperty(u, entry.getKey()).startsWith(entry.getValue());
								else
									return BeanUtils.getProperty(u, entry.getKey()).equals(entry.getValue());
							} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
								throw new RuntimeException();

							}
						})
						.collect(Collectors.toList());
		}
		
		
		return list;
	}



}