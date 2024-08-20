package com.karthi.ecommerce.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;
    @PostMapping
    public ResponseEntity<Integer> createProduct(
            @RequestBody @Valid ProductRequest request
    ){
        return ResponseEntity.ok(service.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<PurchaseResponse>> purchaseProducts(
            @RequestBody @Valid List<PurchaseRequest> request
    ){
        return ResponseEntity.ok(service.purchaseProducts(request));
    }

    @PutMapping
    public ResponseEntity<Void> updateProduct(
            @RequestBody @Valid ProductRequest request
    ){
        service.updateProduct(request);
        return ResponseEntity.accepted().build();
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(){
        List<ProductResponse> products = service.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable Integer productId
    ){
        return ResponseEntity.ok(service.findProductById(productId));
    }
}
