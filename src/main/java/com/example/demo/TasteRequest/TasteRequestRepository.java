package com.example.demo.TasteRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TasteRequestRepository extends JpaRepository<TasteRequest, Long> {

    Optional<TasteRequest> findTasteRequestById(Long id);

    List<TasteRequest> findTasteRequestsByUserId(Long userId);
    List<TasteRequest> findTasteRequestsByUserIdIn(Collection<Long> userIds);
}
