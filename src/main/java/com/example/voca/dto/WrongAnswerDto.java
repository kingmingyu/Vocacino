package com.example.voca.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WrongAnswerDto {


    public String userAnswer;
    public String spelling;
    public String meaning;
}
