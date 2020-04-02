package tech.openchat.telestore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.openchat.telestore.entity.Product;
import tech.openchat.telestore.repository.ProductRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

/**
 * @author vgorin
 * file created on 2020-03-30 15:15
 */

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(long id) {
        return productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Page<Product> listAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
