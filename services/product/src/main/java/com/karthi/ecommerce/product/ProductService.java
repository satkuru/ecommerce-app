package com.karthi.ecommerce.product;

import com.karthi.ecommerce.category.Category;
import com.karthi.ecommerce.product.exception.ProductNotFoundException;
import com.karthi.ecommerce.product.exception.ProductPurchaseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
    public Integer createProduct(ProductRequest request) {

        var product = mapper.toProduct(request);
        return repository.save(product).getId().intValue();
    }

    public List<ProductResponse> getAllProducts() {
        return repository.findAll().stream().map(mapper::fromProduct).toList();
    }

    public void updateProduct(ProductRequest request) {
        var product = repository.findById(request.id()).orElseThrow(()->new ProductNotFoundException(
                String.format("Product with id=%d not found",request.id())
        ));
        mergeProduct(product,request);
        repository.save(product);

    }

    private void mergeProduct(Product product, ProductRequest request) {
        if(StringUtils.isNotBlank(request.name())){
            product.setName(request.name());
        }
        if(StringUtils.isNotBlank(request.description())){
            product.setDescription(request.description());
        }
        if(Objects.nonNull(request.categoryId())){
            product.setCategory(Category.builder().id(request.categoryId().longValue()).build());
        }
        if(Objects.nonNull(request.stockLevel())){
            product.setStockLevel(request.stockLevel());
        }
        if(Objects.nonNull(request.price())){
            product.setPrice(request.price());
        }
    }

    public ProductResponse findProductById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::fromProduct)
                .orElseThrow(() -> new ProductNotFoundException("Requested product with id:%d not found".formatted(productId)));
    }

    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<PurchaseResponse> purchaseProducts(@Valid List<PurchaseRequest> request) {
        List<Long> productIds = request.stream()
                .map(PurchaseRequest::productId)
                .collect(Collectors.toList());
        Map<Long, Product> productsById = repository.findAllByIdInOrderById(productIds).stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        if(productsById.size()!=productIds.size()){
            throw new ProductPurchaseException("One or more product requested not available ");
        }

        List<PurchaseResponse> purchaseResponses = request.stream().map(req -> {
            Product product = productsById.get(req.productId());
            if (product.getStockLevel() < req.quantity()) {
                throw new ProductPurchaseException("Requested product: %s is not in sufficient quantity to fulfil the request, quantity= %d".formatted(product.getName(), req.quantity()));
            }
            product.setStockLevel(product.getStockLevel() - req.quantity());
            return mapper.fromProduct(product,req.quantity())
        }).collect(Collectors.toList());

        repository.saveAll(productsById.values());

        return purchaseResponses;
    }
}
