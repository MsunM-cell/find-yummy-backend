package com.example.demo.TasteRequest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TasteRequestRepository extends JpaRepository<TasteRequest, Long> {

    Optional<TasteRequest> findTasteRequestById(Long id);

    List<TasteRequest> findTasteRequestsByUserId(Long userId);
    List<TasteRequest> findTasteRequestsByUserId(Long userId, PageRequest pageRequest);
    List<TasteRequest> findTasteRequestsByUserIdIn(Collection<Long> userIds);
    List<TasteRequest> findTasteRequestsByUserIdIn(Collection<Long> userIds, PageRequest pageRequest);

    @Query("select tr from TasteRequest tr where tr.name like ?1")
    List<TasteRequest> findTasteRequestsByFuzzyName(String name, PageRequest pageRequest);

    @Query("select tr from TasteRequest tr where tr.name like ?1")
    List<TasteRequest> findTasteRequestsByFuzzyName(String name);

    List<TasteRequest> findTasteRequestsByTasteType(String type);
    List<TasteRequest> findTasteRequestsByTasteType(String type, PageRequest pageRequest);

    @Query("select distinct tr.tasteType from TasteRequest tr")
    List<String> findDistinctTasteType();
}
