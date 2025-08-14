package com.example.voca.controller;

import com.example.voca.entity.UserEntity;
import com.example.voca.entity.VocaEntity;
import com.example.voca.repository.VocaRepository;
import com.example.voca.service.UserContextService;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class LearnController {

    private final VocaRepository vocaRepository;
    private final UserContextService userContextService;
    public LearnController(VocaRepository vocaRepository, UserContextService userContextService){
        this.vocaRepository = vocaRepository;
        this.userContextService = userContextService;
    }

    @GetMapping("/select")
    public String selectP(Model model){
        List<VocaEntity> allVoca = vocaRepository.findAll();
        UserEntity user = userContextService.getCurrentUser();

        int learningDate = user.getLearningData();
        int startIndex = learningDate * 40;
        int endIndex = Math.min(startIndex + 40, allVoca.size());

        if(startIndex >= allVoca.size()){
            user.setLearningData(0);
        } //모두 학습한 경우 처음부터 다시 학습

        List<VocaEntity> todayVoca = allVoca.subList(startIndex, endIndex);

        model.addAttribute("voca", todayVoca);

        return "select";
    }

    @GetMapping("/voca/{id}")
    public String showVoca(@PathVariable Integer id, Model model){
        VocaEntity voca = vocaRepository.findById(id).orElse(null);
        List<VocaEntity> allVoca = vocaRepository.findAll();
        UserEntity user = userContextService.getCurrentUser();

        int learningDate = user.getLearningData();
        int startIndex = learningDate * 40;
        int endIndex = Math.min(startIndex + 40, allVoca.size());

        int nextId = voca.getId() + 1;
        if(nextId > endIndex){
            nextId--;
        }
        int prevId = voca.getId() - 1;
        if(prevId <= startIndex){
            prevId++;
        }

        model.addAttribute("voca", voca);
        model.addAttribute("next", nextId);
        model.addAttribute("prev", prevId);

        return "vocaId";
    }

    @GetMapping("/voca/list")
    public String showVocaList(Model model){

        List<VocaEntity> allVoca = vocaRepository.findAll();
        UserEntity user = userContextService.getCurrentUser();

        int learningDate = user.getLearningData();
        int startIndex = learningDate * 40;
        int endIndex = Math.min(startIndex + 40, allVoca.size());

        List<VocaEntity> todayVoca = allVoca.subList(startIndex, endIndex);

        model.addAttribute("voca", todayVoca);

        return "vocaList";
    }

    @GetMapping("/test/{learningDate}")
    public String testP(@PathVariable Integer learningDate){
        return "test";
    }
}
