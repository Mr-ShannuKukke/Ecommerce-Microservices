package com.ecommerce.product.controller;

import com.ecommerce.product.model.Product;
import com.ecommerce.product.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Product> getProductByCode(@PathVariable String code){
        return ResponseEntity.ok(productService.getProductByCode(code));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category){
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Product>> searchProducts(@PathVariable String keyword){
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, Product productDetails){
        return ResponseEntity.ok(productService.updateProduct(id,productDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product with id: "+id+" has been deleted successfully! ");
    }

    @PostMapping("/check-availability")
    public ResponseEntity<AvailabilityResponse> checkAvailability(@RequestBody AvailabilityRequest request){
        boolean available= productService.checkAvailability(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(new AvailabilityResponse(available));
    }

//    @PostMapping("/reduce-availability")
    @PostMapping("/reduce-quantity")
    public ResponseEntity<String> reduceAvailability(@RequestBody AvailabilityRequest request){
        productService.reduceQuantity(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok("Quantity has been reduced by: "+request.getQuantity());
    }
}

class AvailabilityRequest{
    private Long productId;
    private Integer quantity;

    public void setId(Long productId){
        this.productId=productId;
    }

    public Long getProductId(){
        return productId;
    }

    public void setQuantity(Integer quantity){
        this.quantity=quantity;
    }

    public Integer getQuantity(){
        return quantity;
    }
}

class AvailabilityResponse{
    private Boolean available;

    public AvailabilityResponse(Boolean available){
        this.available=available;
    }

    public void setAvailable(Boolean available){
        this.available=available;
    }

    public Boolean getAvailable(){
        return available;
    }
}