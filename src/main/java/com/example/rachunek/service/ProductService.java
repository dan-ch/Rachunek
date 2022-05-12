package com.example.rachunek.service;

import com.example.rachunek.dto.ProductAddDTO;
import com.example.rachunek.model.Product;
import com.example.rachunek.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> getByProductName(String name){
        return productRepository.findAllByNameContaining(name);
    }

    public Product getProductById(Long id){
        return productRepository.getById(id);
    }

    public void addProduct(ProductAddDTO product){
        productRepository.save(product.toProduct());
    }

}
