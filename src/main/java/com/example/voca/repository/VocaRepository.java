package com.example.voca.repository;

import com.example.voca.entity.VocaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VocaRepository extends JpaRepository<VocaEntity, Integer> {
}
