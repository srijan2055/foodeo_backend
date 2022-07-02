package com.foodeo.backend.repository;

import com.foodeo.backend.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface DishRepository extends JpaRepository<Dish,Long> {


}
