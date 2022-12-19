package org.product.productapi.services;

import lombok.AllArgsConstructor;
import org.product.productapi.dto.ProductDto;
import org.product.productapi.dto.ProductDtoResponse;
import org.product.productapi.models.Product;
import org.product.productapi.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public String createProduct(ProductDto productDto) {

        Product p = Product.builder().id(UUID.randomUUID().toString()).description(productDto.getDescription()).name(productDto.getName()).price(productDto.getPrice()).build();

        productRepository.save(p);

        return "product created id:" + p.getId();
    }

    public List<ProductDtoResponse> getAllProduct() {
return         productRepository.findAll().stream().map(x->ProductDtoResponse.builder().
        id(x.getId()).description(x.getDescription()).price(x.getPrice()).name(x.getName()).build()).collect(Collectors.toList());
    }
}
