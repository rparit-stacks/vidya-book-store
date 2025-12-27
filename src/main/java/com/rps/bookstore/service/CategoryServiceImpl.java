package com.rps.bookstore.service;

import com.rps.bookstore.dto.request.CreateCategoryRequest;
import com.rps.bookstore.dto.request.UpdateCategoryRequest;
import com.rps.bookstore.dto.response.CategoryDTO;
import com.rps.bookstore.dto.response.CategoryTreeDTO;
import com.rps.bookstore.entity.Category;
import com.rps.bookstore.exception.CategoryHasProductsException;
import com.rps.bookstore.exception.CategoryHasSubcategoriesException;
import com.rps.bookstore.exception.CategoryNotFoundException;
import com.rps.bookstore.repository.CategoryRepository;
import com.rps.bookstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of CategoryService for category management operations.
 * 
 * <p>This service implements all category-related business logic including:
 * <ul>
 *   <li>Hierarchical category management</li>
 *   <li>Category slug generation</li>
 *   <li>Category tree building</li>
 *   <li>Category validation and constraints</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    
    /**
     * Gets all categories.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories(boolean includeSubcategories) {
        List<Category> categories;
        if (includeSubcategories) {
            categories = categoryRepository.findAll();
        } else {
            categories = categoryRepository.findByParentCategoryIsNull();
        }
        
        return categories.stream()
                .map(category -> mapToDTO(category, includeSubcategories))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets category by ID with subcategories.
     */
    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        
        return mapToDTOWithSubcategories(category);
    }
    
    /**
     * Gets complete category tree structure.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryTreeDTO> getCategoryTree() {
        List<Category> rootCategories = categoryRepository.findByParentCategoryIsNull();
        return rootCategories.stream()
                .map(this::buildCategoryTree)
                .collect(Collectors.toList());
    }
    
    /**
     * Creates a new category.
     */
    @Override
    public CategoryDTO createCategory(CreateCategoryRequest request) {
        Category parentCategory = null;
        if (request.getParentCategoryId() != null) {
            parentCategory = categoryRepository.findById(request.getParentCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(
                            "Parent category not found with id: " + request.getParentCategoryId()));
        }
        
        // Check if category with same name exists at the same level
        if (categoryRepository.existsByNameAndParentCategory(request.getName(), parentCategory)) {
            throw new RuntimeException("Category with name '" + request.getName() + 
                    "' already exists at this level");
        }
        
        String slug = generateSlug(request.getName(), parentCategory);
        
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .parentCategory(parentCategory)
                .slug(slug)
                .build();
        
        Category savedCategory = categoryRepository.save(category);
        return mapToDTO(savedCategory, false);
    }
    
    /**
     * Updates an existing category.
     */
    @Override
    public CategoryDTO updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        
        Category parentCategory = null;
        if (request.getParentCategoryId() != null) {
            parentCategory = categoryRepository.findById(request.getParentCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(
                            "Parent category not found with id: " + request.getParentCategoryId()));
            
            // Check for circular reference
            if (isCircularReference(category, parentCategory)) {
                throw new RuntimeException("Cannot set parent category: circular reference detected");
            }
        }
        
        // Check if another category with same name exists at the same level
        if (!category.getName().equals(request.getName()) || 
            (category.getParentCategory() != parentCategory && 
             (category.getParentCategory() == null || !category.getParentCategory().getId().equals(request.getParentCategoryId())))) {
            if (categoryRepository.existsByNameAndParentCategory(request.getName(), parentCategory)) {
                throw new RuntimeException("Category with name '" + request.getName() + 
                        "' already exists at this level");
            }
        }
        
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setParentCategory(parentCategory);
        
        // Regenerate slug if name or parent changed
        if (!category.getName().equals(request.getName()) || category.getParentCategory() != parentCategory) {
            category.setSlug(generateSlug(request.getName(), parentCategory));
        }
        
        Category updatedCategory = categoryRepository.save(category);
        return mapToDTO(updatedCategory, false);
    }
    
    /**
     * Deletes a category.
     */
    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        
        // Check if category has subcategories
        if (!category.getSubcategories().isEmpty()) {
            throw new CategoryHasSubcategoriesException(
                    "Cannot delete category: it has subcategories. Delete subcategories first.");
        }
        
        // Check if category has products
        long productCount = productRepository.countByCategoryId(id);
        if (productCount > 0) {
            throw new CategoryHasProductsException(
                    "Cannot delete category: it has " + productCount + " associated products.");
        }
        
        categoryRepository.delete(category);
    }
    
    /**
     * Gets category path as string.
     */
    @Override
    @Transactional(readOnly = true)
    public String getCategoryPath(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + categoryId));
        
        List<String> path = new ArrayList<>();
        Category current = category;
        while (current != null) {
            path.add(0, current.getName());
            current = current.getParentCategory();
        }
        
        return String.join(" > ", path);
    }
    
    /**
     * Checks if category exists.
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }
    
    /**
     * Finds category by ID.
     */
    @Override
    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
    }
    
    /**
     * Validates that category exists.
     */
    @Override
    @Transactional(readOnly = true)
    public void validateCategoryExists(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException("Category not found with id: " + categoryId);
        }
    }
    
    /**
     * Builds category tree recursively.
     */
    private CategoryTreeDTO buildCategoryTree(Category category) {
        List<Category> subcategories = categoryRepository.findByParentCategory(category);
        
        CategoryTreeDTO.CategoryTreeDTOBuilder builder = CategoryTreeDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .slug(category.getSlug())
                .level(calculateLevel(category));
        
        List<CategoryTreeDTO> subcategoryTrees = subcategories.stream()
                .map(this::buildCategoryTree)
                .collect(Collectors.toList());
        
        builder.subcategories(subcategoryTrees);
        
        return builder.build();
    }
    
    /**
     * Maps Category entity to CategoryDTO.
     */
    private CategoryDTO mapToDTO(Category category, boolean includeSubcategories) {
        CategoryDTO.CategoryDTOBuilder builder = CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .slug(category.getSlug())
                .level(calculateLevel(category))
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt());
        
        if (category.getParentCategory() != null) {
            builder.parentCategoryId(category.getParentCategory().getId())
                   .parentCategoryName(category.getParentCategory().getName());
        }
        
        if (includeSubcategories && category.getSubcategories() != null) {
            List<CategoryDTO> subcategoryDTOs = category.getSubcategories().stream()
                    .map(sub -> mapToDTO(sub, true))
                    .collect(Collectors.toList());
            builder.subcategories(subcategoryDTOs);
        }
        
        // Get product count
        long productCount = productRepository.countByCategoryId(category.getId());
        builder.productCount(productCount);
        
        return builder.build();
    }
    
    /**
     * Maps Category entity to CategoryDTO with subcategories.
     */
    private CategoryDTO mapToDTOWithSubcategories(Category category) {
        CategoryDTO dto = mapToDTO(category, true);
        
        // Load subcategories explicitly
        List<Category> subcategories = categoryRepository.findByParentCategory(category);
        List<CategoryDTO> subcategoryDTOs = subcategories.stream()
                .map(sub -> mapToDTOWithSubcategories(sub))
                .collect(Collectors.toList());
        dto.setSubcategories(subcategoryDTOs);
        
        return dto;
    }
    
    /**
     * Calculates category level (0 for root categories).
     */
    private int calculateLevel(Category category) {
        int level = 0;
        Category current = category.getParentCategory();
        while (current != null) {
            level++;
            current = current.getParentCategory();
        }
        return level;
    }
    
    /**
     * Generates URL-friendly slug from category name.
     */
    private String generateSlug(String name, Category parentCategory) {
        String baseSlug = name.toLowerCase()
                .trim()
                .replaceAll("\\s+", "-")
                .replaceAll("[^a-z0-9-]", "");
        
        // Remove diacritics
        baseSlug = Normalizer.normalize(baseSlug, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        
        if (parentCategory != null) {
            baseSlug = parentCategory.getSlug() + "-" + baseSlug;
        }
        
        // Ensure uniqueness
        String finalSlug = baseSlug;
        int counter = 1;
        while (categoryRepository.findBySlug(finalSlug).isPresent()) {
            finalSlug = baseSlug + "-" + counter;
            counter++;
        }
        
        return finalSlug;
    }
    
    /**
     * Checks for circular reference when setting parent category.
     */
    private boolean isCircularReference(Category category, Category potentialParent) {
        Category current = potentialParent;
        while (current != null) {
            if (current.getId().equals(category.getId())) {
                return true;
            }
            current = current.getParentCategory();
        }
        return false;
    }
}

