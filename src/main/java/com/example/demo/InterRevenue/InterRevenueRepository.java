package com.example.demo.InterRevenue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InterRevenueRepository extends JpaRepository<InterRevenue, InterRevenuePrimaryKey> {

    Optional<InterRevenue> findInterRevenueByMonthAndAndCityAndAndRequestType(String month, String city, String requestType);

    List<InterRevenue> findInterRevenuesByMonth(String month);

    @Query("select ir from InterRevenue ir where ir.city = ?3 and ir.month between ?1 and ?2")
    List<InterRevenue> findInterRevenuesByStartAndStopAndCity(String start, String stop, String city);

    @Query("select ir from InterRevenue ir where ir.month between ?1 and ?2")
    List<InterRevenue> findInterRevenuesByStartAndStop(String start, String stop);
}
