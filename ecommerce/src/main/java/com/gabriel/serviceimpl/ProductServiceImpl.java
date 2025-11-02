package com.gabriel.serviceimpl;

import com.gabriel.entity.SpaServiceData;
import com.gabriel.model.ProductCategory;
import com.gabriel.model.SpaService;
import com.gabriel.repository.SpaServiceDataRepository;
import com.gabriel.service.ProductService;
import com.gabriel.util.Transform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    SpaServiceDataRepository productDataRepository;

    Transform<SpaServiceData, SpaService> transformProductData = new Transform<>(SpaService.class);

    Transform<SpaService, SpaServiceData> transformProduct = new Transform<>(SpaServiceData.class);


    public List<SpaService> getAllProducts() {
    List<SpaServiceData>productDataRecords = new ArrayList<>();
        List<SpaService> products =  new ArrayList<>();

        productDataRepository.findAll().forEach(productDataRecords::add);
    Iterator<SpaServiceData> it = productDataRecords.iterator();

        while(it.hasNext()) {
            SpaService product = new SpaService();
            SpaServiceData productData = it.next();
            product = transformProductData.transform(productData);
            products.add(product);
        }
        return products;
    }
    @Override
    public List<ProductCategory> listProductCategories()
    {
        Map<String,List<SpaService>> mappedProduct = getCategoryMappedProducts();
        List<ProductCategory> productCategories = new ArrayList<>();
        for(String categoryName: mappedProduct.keySet()){
            ProductCategory productCategory =  new ProductCategory();
            productCategory.setCategoryName(categoryName);
            productCategory.setProducts(mappedProduct.get(categoryName));
            productCategories.add(productCategory);
        }
        return productCategories;
    }
    @Override
    public Map<String,List<SpaService>> getCategoryMappedProducts()
    {
        Map<String,List<SpaService>> mapProducts = new HashMap<String,List<SpaService>>();

    List<SpaServiceData>productDataRecords = new ArrayList<>();
        List<SpaService> products;

        productDataRepository.findAll().forEach(productDataRecords::add);
    Iterator<SpaServiceData> it = productDataRecords.iterator();

        while(it.hasNext()) {
            SpaService product = new SpaService();
            SpaServiceData productData = it.next();

            if(mapProducts.containsKey(productData.getCategoryName())){
                products = mapProducts.get(productData.getCategoryName());
            }
            else {
                products = new ArrayList<SpaService>();
                mapProducts.put(productData.getCategoryName(), products);
            }
            product = transformProductData.transform(productData);
            products.add(product);
        }
        return mapProducts;
    }

    @Override
    public SpaService[] getAll() {
        List<SpaServiceData> productsData = new ArrayList<>();
            List<SpaService> products = new ArrayList<>();
            productDataRepository.findAll().forEach(productsData::add);
            Iterator<SpaServiceData> it = productsData.iterator();
            while(it.hasNext()) {
                SpaServiceData productData = it.next();
                    SpaService product =  transformProductData.transform(productData);
                products.add(product);
            }
            SpaService[] array = new SpaService[products.size()];
            for  (int i=0; i<products.size(); i++){
                array[i] = products.get(i);
            }
            return array;
        }
    @Override
    public SpaService get(Integer id) {
        log.info(" Input id >> "+  Integer.toString(id) );
        SpaService product = null;
    Optional<SpaServiceData> optional = productDataRepository.findById(id);
        if(optional.isPresent()) {
            log.info(" Is present >> ");
            product = new SpaService();
            product.setId(optional.get().getId());
            product.setName(optional.get().getName());
        }
        else {
            log.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
        }
        return product;
    }
        @Override
        public SpaService create(SpaService product) {
            log.info(" add:Input " + product.toString());
            SpaServiceData productData = transformProduct.transform(product);
            SpaServiceData updatedProductData = productDataRepository.save(productData);
            log.info(" add:Input {}", productData.toString());
            return  transformProductData.transform(updatedProductData);
        }

        @Override
        public SpaService update(SpaService product) {
            Optional<SpaServiceData> optional  = productDataRepository.findById(product.getId());
            if(optional.isPresent()){
                SpaServiceData productData = transformProduct.transform(product);
                SpaServiceData updaatedProductData = productDataRepository.save( productData);
                return transformProductData.transform(updaatedProductData);
            }
            else {
                log.error("Product record with id: {} do not exist",product.getId());
            }
            return null;
        }
    @Override
    public void delete(Integer id) {
        log.info(" Input >> {}",id);
    Optional<SpaServiceData> optional = productDataRepository.findById(id);
        if( optional.isPresent()) {
            SpaServiceData productDatum = optional.get();
            productDataRepository.delete(optional.get());
            log.info(" Successfully deleted Product record with id: {}",id);
        }
        else {
            log.error(" Unable to locate product with id: {}", id);
        }
    }
}
