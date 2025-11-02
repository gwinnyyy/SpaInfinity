package com.nicco.controller;

import com.nicco.model.ProductCategory;
import com.nicco.model.SpaService;
import com.nicco.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RequestMapping("/api/services")
public class SpaServiceController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?>  getProductCategories()
    {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            List<ProductCategory> mappedProducts = productService.listProductCategories();
            //Map<String,List<SpaService>> mappedProducts = productService.getCategoryMappedProducts();
            log.warn("Product Categories Count:::::::" + mappedProducts.size());
            response = ResponseEntity.ok(mappedProducts);
        }
        catch( Exception ex)
        {
            log.error("Failed to retrieve product with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<?> add(@RequestBody SpaService product){
        log.info("Input >> " + product.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            SpaService newProduct = productService.create(product);
            log.info("created product >> " + newProduct.toString() );
            response = ResponseEntity.ok(newProduct);
        }
        catch( Exception ex)
        {
            log.error("Failed to retrieve product with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
    @PostMapping
    public ResponseEntity<?> update(@RequestBody SpaService product){
        log.info("Update Input >> product.toString() ");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            SpaService newProduct = productService.update(product);
            response = ResponseEntity.ok(product);
        }
        catch( Exception ex)
        {
            log.error("Failed to retrieve product with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable final Integer id){
        log.info("Input product id >> " + Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            SpaService product = productService.get(id);
            response = ResponseEntity.ok(product);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id){
        log.info("Input >> " + Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            productService.delete(id);
            response = ResponseEntity.ok(null);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
}
