package com.endre.java.springquizgame.backend.service;

import com.endre.java.springquizgame.backend.entity.Category;
import com.endre.java.springquizgame.backend.entity.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private EntityManager em;

    public Long createCategory(String name){
        Category category = new Category();
        category.setName(name);

        em.persist(category);
        return category.getId();
    }


    public Long createSubCategory(Long parentId, String name) {

        Category category = em.find(Category.class, parentId);
        if (category == null){
            throw new IllegalArgumentException("Could not find matching parent Category");
        }
        SubCategory subCategory = new SubCategory();
        subCategory.setParent(category);
        subCategory.setName(name);

        category.getSubCategories().add(subCategory);

        em.persist(subCategory);

        return subCategory.getId();
    }


    // Specify if you want to force load the subCategory of all categories
    public List<Category> getAllCategories(boolean withSub){
        TypedQuery<Category> query = em.createQuery("select c from Category c", Category.class);
        List<Category> categories = query.getResultList();

        if (withSub){
            //force loading
            categories.forEach(c -> c.getSubCategories().size());
        }

        return categories;
    }


    public Category getCategory(long id, boolean withSub){
        Category category = em.find(Category.class, id);
        if (withSub && category != null){
            category.getSubCategories().size();
        }
        return category;
    }


    public SubCategory getSubcategory(long id){
        return em.find(SubCategory.class, id);
    }


}
