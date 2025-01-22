package com.ecommerce.sbecom.services;

import com.ecommerce.sbecom.exceptions.APIException;
import com.ecommerce.sbecom.exceptions.ResourceNotFoundException;
import com.ecommerce.sbecom.models.Category;
import com.ecommerce.sbecom.payloads.CategoryDTO;
import com.ecommerce.sbecom.payloads.CategoryResponse;
import com.ecommerce.sbecom.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();

        if (categories.isEmpty()) {
            throw new APIException("No Category exists!!!");
        }

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

//        List<CategoryDTO> categoryDTOS = new ArrayList<>();
//        for (Category category: categories) {
//            CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
//            categoryDTOS.add(categoryDTO);
//        }

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);

        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            throw new APIException("Category with name " + category.getName() + " already exists!!!");
        }

        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category deletedCategory = optionalCategory.get();
            categoryRepository.deleteById(id);
            return modelMapper.map(deletedCategory, CategoryDTO.class);
        }
        else {
            throw new ResourceNotFoundException("Category", "id", id);
        }
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> existingCategoryWithId = categoryRepository.findById(id);
        if (existingCategoryWithId.isPresent()) {
            Category category = modelMapper.map(categoryDTO, Category.class);
            Optional<Category> existingCategoryWithName = categoryRepository.findByName(category.getName());
            if (existingCategoryWithName.isPresent()) {
                throw new APIException("Category with name " + category.getName() + " already exists!!!");
            } else {
                category.setId(id);
                Category updatedCategory = categoryRepository.save(category);
                return modelMapper.map(updatedCategory, CategoryDTO.class);
            }
        } else {
            throw new ResourceNotFoundException("Category", "id", id);
        }
    }
}
