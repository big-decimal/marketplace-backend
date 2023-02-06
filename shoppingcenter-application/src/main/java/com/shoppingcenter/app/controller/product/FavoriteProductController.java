package com.shoppingcenter.app.controller.product;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.product.FavoriteProductService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/favorite-products")
@Tag(name = "FavoriteProduct")
public class FavoriteProductController {

    @Autowired
    private FavoriteProductService favoriteProductService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public PageData<ProductDTO> getFavoriteProducts(
            @RequestParam(required = false) Integer page,
            Authentication authentication) {
        return modelMapper.map(favoriteProductService.findByUser(authentication.getName(), page),
                ProductDTO.pageType());
    }

    @PostMapping
    public void addToFavorite(@RequestParam("product-id") long productId, Authentication authentication) {
        favoriteProductService.add(authentication.getName(), productId);
    }

    @GetMapping("check")
    public boolean checkFavorite(@RequestParam("product-id") long productId, Authentication authentication) {
        return favoriteProductService.checkFavorite(authentication.getName(), productId);
    }

    @DeleteMapping
    public void removeFromFavorite(@RequestParam("product-id") long productId, Authentication authentication) {
        favoriteProductService.remove(authentication.getName(), productId);
    }

}
