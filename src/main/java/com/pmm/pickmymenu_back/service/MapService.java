package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.dto.request.map.PlaceSearchReq;
import com.pmm.pickmymenu_back.dto.response.map.PlaceSearchRes;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MapService {

    private final WebClient kakaoWebClient;

    public Mono<PlaceSearchRes> placeSearch(PlaceSearchReq req) {
        return getSearchResult(req).flatMap(placeSearchRes -> {
            List<Mono<PlaceSearchRes.Document>> tasks = placeSearchRes.getDocuments().stream()
                    .map(document -> {
                        String url = document.getPlace_url().replace("http://place.map.kakao.com/",
                                "http://place.map.kakao.com/placePrint.daum?confirmid=");
                        return Mono.fromCallable(() -> {
                            try {
                                Document document1 = Jsoup.connect(url).get();
                                Element imgElement = document1.select(
                                                "body > div > div > div.popup_body > div.wrap_info > div > img")
                                        .first();
                                if (imgElement != null) {
                                    document.setImage_url(imgElement.attr("src"));
                                }
                                return document; // 작업 완료 후 document 리턴
                            } catch (IOException e) {
                                // 에러 처리
                                log.error("Error during Jsoup connection", e);
                                return null; // 에러 발생 시 null 반환
                            }
                        });
                    })
                    .toList();

            // 3. 모든 비동기 작업이 끝난 후 최종 결과 반환
            return Flux.merge(tasks)
                    .then(Mono.just(placeSearchRes));
        });
    }

    private Mono<PlaceSearchRes> getSearchResult(PlaceSearchReq req) {
        return kakaoWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/local/search/keyword.json")
                        .queryParam("query", req.getQuery())
                        .queryParam("x", req.getX())
                        .queryParam("y", req.getY())
                        .queryParam("radius", req.getRadius())
                        .queryParam("page", req.getPage())
                        .queryParam("size", req.getSize())
                        .queryParam("sort", req.getSort())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PlaceSearchRes.class);
    }
}
