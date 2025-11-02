package com.nicco.service;

import com.nicco.model.ProductCategory;
import com.nicco.model.SpaService;

import java.util.*;

public interface ProductService {

    List<SpaService> getAllProducts();
    SpaService[] getAll();
    SpaService get(Integer id);
    SpaService create(SpaService product);
    SpaService update(SpaService product);
    void delete(Integer id);
    Map<String, List<SpaService>> getCategoryMappedProducts();
    List<ProductCategory> listProductCategories();
}
