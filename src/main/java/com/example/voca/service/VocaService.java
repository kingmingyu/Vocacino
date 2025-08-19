package com.example.voca.service;

import com.example.voca.entity.VocaEntity;
import com.example.voca.repository.VocaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VocaService {
    private final VocaRepository vocaRepository;
    private final UserContextService userContextService;

    public VocaService(VocaRepository vocaRepository, UserContextService userContextService){
        this.vocaRepository = vocaRepository;
        this.userContextService = userContextService;
    }

    public List<VocaEntity> findAllVoca(){
        List<VocaEntity> allVoca = vocaRepository.findAll();
        return allVoca;
    }

    public List<Integer> findAllVocaId(){
        List<Integer> vocaIds = vocaRepository.findAllIds();
        return vocaIds;
    }

    public VocaEntity findVocaById(int id){
        VocaEntity voca = vocaRepository.findById(id).orElseThrow(() -> new NoSuchElementException("ID: " + id + "에 해당하는 단어가 없습니다."));
        return voca;
    }
}
