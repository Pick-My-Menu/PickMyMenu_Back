package com.pmm.pickmymenu_back.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class FoodCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer foodCategoryId; // 인덱스

    private String categoryName; // 음식 이름

    private String categoryClassification; // 음식 구분



    /*

    음식의 특징
매운것
안매운것
뜨거운것
차가운것
고소한것
달콤한것
짠것
새콤한것
요리 종류
한식
중식
일식
양식
인도식
태국식
멕시코식
베트남식
음식 형태
밥
면
빵
국물
찜
볶음
구이
튀김
주요 재료
육류
해산물
채소류
곡물류
과일류
유제품
계란
음식의 성격
건강식
디저트
간식
스낵
정찬
패스트푸드
특정 음식
떡볶이
김밥
치킨
피자
스테이크
파스타
햄버거
초밥
라면
샌드위치
기타
길거리 음식
가정식
뷔페
채식주의자용
글루텐 프리

    */
}
