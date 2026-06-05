package com.ecommerce.product.service;

import com.ecommerce.product.exception.BadRequestException;
import com.ecommerce.product.exception.ResourceNotFoundException;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product){
        if(product.getProductName()==null || product.getProductName().trim().isEmpty()){
            throw new BadRequestException("Product name cannot be empty");
        }
        if(product.getPrice()==null || product.getPrice().signum()<=0){
            throw new BadRequestException("Price must be greater than 0");
        }
        return productRepository.save(product);
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found with id: "+id));
    }

    public Product getProductByCode(String code){
        return productRepository.findByProductCode(code).orElseThrow(()-> new ResourceNotFoundException("Product not found with code: "+code));
    }

    public List<Product> getProductsByCategory(String category){
//        return productRepository.findByCategory(category);
        List<Product> products=productRepository.findByCategory(category);
        if(products.isEmpty()){
            throw new ResourceNotFoundException("No products found in category: " + category);
        }
        return products;
    }

    public List<Product> getAllProducts(){
        return productRepository.findByIsActiveTrue();
    }

    public List<Product> searchProducts(String keyword){
//        return productRepository.findByProductNameContainingIgnoreCase(keyword);
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(keyword);
        if(products.isEmpty()){
            throw new ResourceNotFoundException("No products found matching keyword: " + keyword);
        }
        return products;
    }

    public Product updateProduct(Long id, Product productDetails){
        Product product=getProductById(id);

        if(productDetails.getProductName()!=null)
            product.setProductName(productDetails.getProductName());;
        if (productDetails.getDescription() != null)
            product.setDescription(productDetails.getDescription());
        if (productDetails.getPrice() != null)
            product.setPrice(productDetails.getPrice());
        if (productDetails.getQuantity() != null)
            product.setQuantity(productDetails.getQuantity());
        if (productDetails.getCategory() != null)
            product.setCategory(productDetails.getCategory());
        if (productDetails.getRating() != null)
            product.setRating(productDetails.getRating());

        return productRepository.save(product);

    }

    public void deleteProduct(Long id){
        Product product=getProductById(id);
        product.setIsActive(false);
        productRepository.save(product);
    }

    public boolean checkAvailability(Long productId, Integer quantity){
        Product product=getProductById(productId);
        return product.getQuantity()>=quantity && product.getIsActive();
    }

    public void reduceQuantity(Long productId, Integer quantity){
        Product product=getProductById(productId);

        if(product.getQuantity()<quantity){
            throw new BadRequestException("Insufficient quantity available");
        }
        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
    }

}
