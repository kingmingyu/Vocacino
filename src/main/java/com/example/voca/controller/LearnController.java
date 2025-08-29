package com.example.voca.controller;

import com.example.voca.entity.UserEntity;
import com.example.voca.entity.VocaEntity;
import com.example.voca.repository.VocaRepository;
import com.example.voca.service.UserContextService;
import com.example.voca.service.VocaService;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class LearnController {

    private final VocaService vocaService;
    private final UserContextService userContextService;
    public LearnController(VocaService vocaService, UserContextService userContextService){
        this.vocaService = vocaService;
        this.userContextService = userContextService;
    }

    @GetMapping("/select")
    public String selectP(Model model){
        List<Integer> vocaIndex = vocaService.findAllVocaId();
        UserEntity user = userContextService.getCurrentUser();

        int learningDate = user.getLearningData();
        int startIndex = learningDate * 40;

        if(startIndex >= vocaIndex.size()){
            user.setLearningData(0);
            startIndex = 0; // 처음부터 다시 학습
        }

        int todayStartId = vocaIndex.get(startIndex);

        VocaEntity todayStartVoca = vocaService.findVocaById(todayStartId);

        model.addAttribute("voca", todayStartVoca);
        model.addAttribute("learningDate", learningDate);

        return "select";
    }

    @GetMapping("/voca/{id}")
    public String showVoca(@PathVariable Integer id, Model model) {
        // 1. 필요한 데이터를 모두 가져옵니다.
        VocaEntity voca = vocaService.findVocaById(id);
        List<Integer> vocaIndex = vocaService.findAllVocaId(); // ID가 정렬된 전체 목록
        UserEntity user = userContextService.getCurrentUser();

        // 단어 목록이 비어있을 경우 예외 처리
        if (vocaIndex.isEmpty()) {
            model.addAttribute("voca", voca);
            model.addAttribute("error", "단어 목록이 비어있습니다.");
            return "vocaId";
        }

        // 2. 오늘의 학습 범위 '인덱스'를 정확히 계산합니다.
        int learningDate = user.getLearningData();
        int startIndex = learningDate * 40;
        int endIndex = Math.min(startIndex + 40, vocaIndex.size());

        // 3. 현재 보고 있는 단어의 '인덱스'를 전체 목록에서 찾습니다.
        int currentIndex = vocaIndex.indexOf(id);

        // 4. 이전/다음 'ID'를 '인덱스'를 기반으로 안전하게 찾습니다.
        // 이전 ID: 현재 인덱스가 0보다 클 때만 이전 인덱스의 값을 가져옴
        int prevId = (currentIndex > 0) ? vocaIndex.get(currentIndex - 1) : id;
        // 다음 ID: 현재 인덱스가 마지막 인덱스보다 작을 때만 다음 인덱스의 값을 가져옴
        int nextId = (currentIndex < vocaIndex.size() - 1) ? vocaIndex.get(currentIndex + 1) : id;

        // 5. 현재 단어가 '오늘의 학습 범위'에서 첫 번째인지, 마지막인지 '인덱스'로 확인합니다.
        boolean isFirstInBatch = (currentIndex <= startIndex);
        boolean isLastInBatch = (currentIndex >= endIndex - 1);

        // 모델에 필요한 모든 데이터를 추가합니다.
        model.addAttribute("voca", voca);
        model.addAttribute("prev", prevId);
        model.addAttribute("next", nextId);
        model.addAttribute("isFirst", isFirstInBatch);
        model.addAttribute("isLast", isLastInBatch);

        return "vocaId";
    }

    @GetMapping("/voca/list")
    public String showVocaList(Model model){
        List<VocaEntity> allVoca = vocaService.findAllVoca();
        List<Integer> vocaIndex = vocaService.findAllVocaId();
        UserEntity user = userContextService.getCurrentUser();

        int learningDate = user.getLearningData();
        int startIndex = learningDate * 40;
        int endIndex = Math.min(startIndex + 40, vocaIndex.size());

        List<VocaEntity> todayVoca = allVoca.subList(startIndex, endIndex);

        model.addAttribute("voca", todayVoca);

        return "vocaList";
    }
}
