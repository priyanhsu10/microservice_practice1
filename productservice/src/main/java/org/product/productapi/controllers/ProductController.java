package org.product.productapi.controllers;

import lombok.RequiredArgsConstructor;
import org.product.productapi.dto.ProductDto;
import org.product.productapi.dto.ProductDtoResponse;
import org.product.productapi.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private  final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  String createProduct(@RequestBody ProductDto productDto){
        return productService.createProduct(productDto);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDtoResponse> get(){

        return  productService.getAllProduct();
    }
}
