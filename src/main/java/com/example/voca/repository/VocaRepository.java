package com.example.voca.repository;

import com.example.voca.entity.VocaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VocaRepository extends JpaRepository<VocaEntity, Integer> {
    @Query("SELECT v.id FROM VocaEntity v ORDER BY v.id ASC")
    List<Integer> findAllIds();
}
