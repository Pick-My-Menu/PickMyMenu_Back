package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.dto.request.map.PlaceSearchReq;
import com.pmm.pickmymenu_back.dto.response.map.PlaceSearchRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
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
        Mono<PlaceSearchRes> searchResult = getSearchResult(req);
        Mono<PlaceSearchRes> map = searchResult.map(data -> {
            System.out.println(data.getDocuments());
            System.out.println(data.getDocuments().getClass());
            return data;
        });
        return map;
//        return getSearchResult(req)
//                .flatMap(initialResult -> {
//                    int pageableCount = initialResult.getMeta().getPageable_count();
//                    if (pageableCount <= 1) {
//                        return Mono.just(initialResult);
//                    }
//
//                    return Flux.fromStream(IntStream.range(2, pageableCount + 1).boxed())
//                            .flatMap(page -> {
//                                PlaceSearchReq pageReq = req.toBuilder().page(page).build();
//                                return getSearchResult(pageReq);
//                            })
//                            .reduce(initialResult, (acc, res) -> {
//                                acc.getDocuments().addAll(res.getDocuments());
//                                return acc;
//                            });
//                });
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
