package com.ecommerce.sbecom.services;

import com.ecommerce.sbecom.exceptions.APIException;
import com.ecommerce.sbecom.exceptions.ResourceNotFoundException;
import com.ecommerce.sbecom.models.Category;
import com.ecommerce.sbecom.models.Product;
import com.ecommerce.sbecom.payloads.ProductDTO;
import com.ecommerce.sbecom.payloads.ProductResponse;
import com.ecommerce.sbecom.repositories.CategoryRepository;
import com.ecommerce.sbecom.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if (existingCategory.isPresent()) {
            Product product = modelMapper.map(productDTO, Product.class);

            product.setCategory(existingCategory.get());
            product.setSpecialPrice(product.getPrice(), product.getDiscount());

            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        } else {
            throw new ResourceNotFoundException("Category", "id", categoryId);
        }
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new APIException("No product exists!!!");
        }

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            List<Product> products = productRepository.findByCategory(category.get());
            if (products.isEmpty()) {
                throw new APIException("No product exists!!!");
            }
            List<ProductDTO> productDTOS = products.stream()
                    .map(product -> modelMapper.map(product, ProductDTO.class))
                    .toList();
            ProductResponse productResponse = new ProductResponse();
            productResponse.setContent(productDTOS);
            return productResponse;
        } else {
            throw new ResourceNotFoundException("Category", "id", categoryId);
        }
    }

    @Override
    public ProductResponse searchByKeyword(String keyword) {
        List<Product> products = productRepository.findByNameLikeIgnoreCase("%" + keyword + "%");
        if (products.isEmpty()) {
            throw new APIException("No product exists!!!");
        }

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Optional<Product> existingProductWithId = productRepository.findById(productId);
        if (existingProductWithId.isPresent()) {
            Product updatedProduct = modelMapper.map(productDTO, Product.class);
            updatedProduct.setId(productId);
            updatedProduct.setSpecialPrice(updatedProduct.getPrice(), updatedProduct.getDiscount());
            productRepository.save(updatedProduct);
            return modelMapper.map(updatedProduct, ProductDTO.class);
        } else {
            throw new ResourceNotFoundException("Product", "id", productId);
        }
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Optional<Product> existingProductWithId = productRepository.findById(productId);
        if (existingProductWithId.isPresent()) {
            Product deletedProduct = existingProductWithId.get();
            productRepository.delete(deletedProduct);
            return modelMapper.map(deletedProduct, ProductDTO.class);
        } else {
            throw new ResourceNotFoundException("Product", "id", productId);
        }
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        // Check if the product exists or not
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        // Upload image to server and get the filename of the uploaded image
        String fileName = uploadImage(image, productId);
        // Update the product
        product.setImage(fileName);
        // Save the updated product
        Product savedProduct = productRepository.save(product);
        // Return productDTO
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    private String uploadImage(MultipartFile image, Long productId) throws IOException {
        // Define a directory to upload the image
        String uploadDir = "images/";
        String originalFileName = image.getOriginalFilename();

        // Generate a unique file name and file path
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = uploadDir + File.separator + fileName;

        // Create the directory if it does not exist
        File folder = new File(uploadDir);
        if (!folder.exists()) folder.mkdir();

        // Upload to server
        Files.copy(image.getInputStream(), Paths.get(filePath));

        // Return file name
        return fileName;
    }

}
