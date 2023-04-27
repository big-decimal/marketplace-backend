package com.shoppingcenter.app.controller.misc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.misc.dto.CityDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/cities")
@Tag(name = "City")
public class CityController {

	@Autowired
	private CityFacade cityFacade;

	@Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void create(@RequestBody CityDTO dto) {
		cityFacade.save(dto);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
	@PutMapping
	public void update(@RequestBody CityDTO dto) {
		cityFacade.save(dto);
	}
	
	@Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
	@DeleteMapping("{id:\\d+}")
	public void delete(@PathVariable long id) {
		cityFacade.delete(id);
	}
	
	@GetMapping
	public List<CityDTO> findAll() {
		return cityFacade.findAll();
	}

}