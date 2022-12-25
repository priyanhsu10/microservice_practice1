package org.product.productapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.product.productapi.dto.ProductDto;
import org.product.productapi.dto.ProductDtoResponse;
import org.product.productapi.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {

    private  final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  String createProduct(@RequestBody ProductDto productDto) throws JsonProcessingException {
        return productService.createProduct(productDto);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDtoResponse> get(){
log.info("getting all product :" + LocalDate.now());
        return  productService.getAllProduct();
    }
}
