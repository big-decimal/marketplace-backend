package com.marketplace.api.consumer.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.PageDataDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/content/markets")
@Tag(name = "Consumer")
public class MarketController {

	@Autowired
	private MarketControllerFacade marketControllerFacade;

	@GetMapping
	public PageDataDTO<MarketDTO> findAll(
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer limit
			) {
		return marketControllerFacade.findAll(page, limit);
	}

}
