package com.example.demo.TasteResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasteResponseRepository extends JpaRepository<TasteResponse, Long> {
}
