package com.mssngvwls.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mssngvwls.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
