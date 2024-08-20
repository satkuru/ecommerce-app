package com.karthi.ecommerce.product;

import com.karthi.ecommerce.category.Category;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .id(Long.valueOf(request.id()))
                .name(request.name())
                .description(request.description())
                .stockLevel(request.stockLevel())
                .price(request.price())
                .category(Category.builder().id(Long.valueOf(request.categoryId())).build())
                .build();
    }

    public ProductResponse fromProduct(Product product) {
        return new ProductResponse(product.getId().intValue(),
                product.getName(),
                product.getDescription(),
                product.getStockLevel(),
                product.getPrice(),
                product.getCategory().getId().intValue(),
                product.getCategory().getName(),
                product.getCategory().getDescription());
    }

    public PurchaseResponse fromProduct(Product product, Integer quantity) {
        return new PurchaseResponse(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity);
    }
}
