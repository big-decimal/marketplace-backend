package com.marketplace.api.admin.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/admin/markets")
@Tag(name = "Admin")
public class MarketController {

	@Autowired
	private MarketControllerFacade marketControllerFacade;

	@PreAuthorize("hasPermission('MARKET', 'WRITE')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void create(@RequestBody MarketEditDTO body) {
		marketControllerFacade.save(body);
	}

	@PreAuthorize("hasPermission('MARKET', 'WRITE')")
	@PutMapping
	public void update(@RequestBody MarketEditDTO body) {
		marketControllerFacade.save(body);
	}

	@PreAuthorize("hasPermission('MARKET', 'WRITE')")
	@DeleteMapping("{id:\\d+}")
	public void delete(@PathVariable long id) {
		marketControllerFacade.delete(id);
	}

}
