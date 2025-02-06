package com.pmm.pickmymenu_back.domain;

import com.pmm.pickmymenu_back.dto.request.choice.ChoiceUpdateReq;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Choice extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_id")
    private Long id; // 인덱스

    @Column(nullable = false)
    private String question0;

    @Column(nullable = false)
    private String question1;

    // 설문에 사용된 횟수
    @Column(nullable = false)
    private Long useCount;

    //사용할지 안할지 여부
    @Column(nullable = false)
    private boolean isUse;

    @OneToMany(mappedBy = "choice")
    private List<Survey> surveyList = new ArrayList<>();

    private Choice(String question0, String question1) {
        this.question0 = question0;
        this.question1 = question1;
        this.useCount = 0L;
        this.isUse = true;
    }

    public static Choice create(String question0, String question1) {
        return new Choice(question0, question1);
    }
    public void countPlus() {
        this.useCount += 1;
    }

    public void update(ChoiceUpdateReq req) {
        this.question0 = req.getQuestion0();
        this.question1 = req.getQuestion1();
    }
}
