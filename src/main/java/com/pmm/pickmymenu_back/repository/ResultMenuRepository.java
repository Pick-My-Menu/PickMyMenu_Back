package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.ResultMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultMenuRepository extends JpaRepository<ResultMenu, Long> {

}
