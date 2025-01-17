package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.dto.request.rank.RankMenuReq;
import com.pmm.pickmymenu_back.dto.response.rank.RankMenuRes;
import com.pmm.pickmymenu_back.repository.ResultMenuRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RankService {

    private final ResultMenuRepository resultMenuRepository;

    @Transactional(readOnly = true)
    public List<RankMenuRes> getRankMenu(RankMenuReq req) {
        return resultMenuRepository.findMenuCountsOrderByDesc(req.getTime());
    }
}
