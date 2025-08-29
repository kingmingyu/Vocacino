package com.example.voca.controller;

import com.example.voca.dto.WrongAnswerDto;
import com.example.voca.entity.VocaEntity;
import com.example.voca.service.VocaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
public class TestController {

    private final VocaService vocaService;
    public TestController(VocaService vocaService){
        this.vocaService = vocaService;
    }

    @GetMapping("/test/{learningDate}")
    public String testP(@PathVariable Integer learningDate, HttpSession session, Model model){
        model.addAttribute("learningDate", learningDate);

        List<Integer> vocaIndex = vocaService.findAllVocaId();
        int startIndex = learningDate * 40;
        int endIndex = Math.min(startIndex + 40, vocaIndex.size());

        //테스트 단어
        List<Integer> todayVocaIndex = vocaIndex.subList(startIndex, endIndex);
        Collections.shuffle(todayVocaIndex);
        List<Integer> testVocaIndex = todayVocaIndex.subList(0, Math.min(20, todayVocaIndex.size()));

        //테스트 단어
        session.setAttribute("testVocaIndex", testVocaIndex);
        session.setAttribute("currentIndex", 0);
        session.setAttribute("answerCount", 0);

        //오답 처리
        session.setAttribute("wrongAnswerIds", new java.util.ArrayList<Integer>());
        session.setAttribute("wrongUserAnswers", new java.util.ArrayList<String>());

        int firstVocaId = testVocaIndex.get(0);
        VocaEntity voca = vocaService.findVocaById(firstVocaId);
        model.addAttribute("voca", voca);
        model.addAttribute("currentIndex", 1);
        model.addAttribute("totalIndex", testVocaIndex.size());

        return "test";
    }

    @GetMapping("/test/{learningDate}/next")
    public String nextTestP(@PathVariable Integer learningDate, HttpSession session, Model model){
        model.addAttribute("learningDate", learningDate);

        List<Integer> testVocaIndex = (List<Integer>) session.getAttribute("testVocaIndex");
        int currentIndex = (int) session.getAttribute("currentIndex");

        currentIndex++;

        if(currentIndex >= testVocaIndex.size()){
            session.removeAttribute("currentIndex");
            return "redirect:/testResult";
        }

        session.setAttribute("currentIndex", currentIndex);

        int testVocaId = testVocaIndex.get(currentIndex);
        VocaEntity voca = vocaService.findVocaById(testVocaId);

        model.addAttribute("voca", voca);
        model.addAttribute("currentIndex", currentIndex + 1);
        model.addAttribute("totalIndex", testVocaIndex.size());

        return "test";
    }

    @PostMapping("test/{learningDate}/submit")
    public String checkTestSubmit(@PathVariable Integer learningDate, @RequestParam String userAnswer, @RequestParam Integer vocaId, HttpSession session){

        boolean isAnswer = vocaService.checkAnswer(vocaId, userAnswer);



        if(isAnswer){ //정답 처리
            int answerCount = (int) session.getAttribute("answerCount");
            answerCount++;
            session.setAttribute("answerCount", answerCount);
        }
        else{ //오답 처리
            List<Integer> wrongAnswerIds = (List<Integer>) session.getAttribute("wrongAnswerIds");
            List<String> wrongUserAnswers = (List<String>) session.getAttribute("wrongUserAnswers");

            wrongAnswerIds.add(vocaId);
            wrongUserAnswers.add(userAnswer);

            session.setAttribute("wrongAnswerIds", wrongAnswerIds);
            session.setAttribute("wrongUserAnswers", wrongUserAnswers);
        }

        int currentIndex = (int) session.getAttribute("currentIndex");
        List<Integer> testVocaIndex = (List<Integer>) session.getAttribute("testVocaIndex");

        if (currentIndex >= testVocaIndex.size() - 1) {
            return "redirect:/testResult";
        } else {
            return "redirect:/test/{learningDate}/next";
        }
    }

    @GetMapping("/testResult")
    public String testResultP(HttpSession session, Model model){

        List<Integer> testVocaIndex = (List<Integer>) session.getAttribute("testVocaIndex");
        int answerCount = (int) session.getAttribute("answerCount");

        //오답 정보 가져오기
        List<Integer> wrongAnswerIds = (List<Integer>) session.getAttribute("wrongAnswerIds");
        List<String> wrongUserAnswers = (List<String>) session.getAttribute("wrongUserAnswers");

        List<WrongAnswerDto> wrongAnswerDtoList = new java.util.ArrayList<>();

        if(wrongUserAnswers != null && !wrongUserAnswers.isEmpty()){
            List<VocaEntity> wrongVocaList = vocaService.findVocaByIds(wrongAnswerIds);

            for (int i = 0; i < wrongVocaList.size(); i++) {
                VocaEntity correctVoca = wrongVocaList.get(i);
                String userAnswer = wrongUserAnswers.get(i);
                wrongAnswerDtoList.add(new WrongAnswerDto(userAnswer, correctVoca.getSpelling(), correctVoca.getMeaning()));
            }
        }


        model.addAttribute("totalIndex", testVocaIndex.size());
        model.addAttribute("answerCount", answerCount);
        model.addAttribute("wrongAnswerList", wrongAnswerDtoList);

        return "testResult";
    }
}
