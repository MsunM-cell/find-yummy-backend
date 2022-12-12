package com.example.demo.TasteResponse;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasteResponseRepository extends JpaRepository<TasteResponse, Long> {

    List<TasteResponse> findTasteResponsesByResponseUserId(Long userId, PageRequest pageRequest);
}
