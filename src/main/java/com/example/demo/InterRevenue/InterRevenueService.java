package com.example.demo.InterRevenue;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InterRevenueService {

    private InterRevenueRepository interRevenueRepository;

    public List<InterRevenue> getStatistics(String start, String stop, String city) {
        return interRevenueRepository.findInterRevenuesByStartAndStopAndCity(start, stop, city);
    }

    public List<TotalInterRevenue> getTotalStatistics(String start, String stop) {
        String[] start_split = start.split("-");
        Integer start_year = Integer.valueOf(start_split[0]);
        Integer start_month = Integer.valueOf(start_split[1]);

        String[] stop_split = stop.split("-");
        Integer stop_year = Integer.valueOf(stop_split[0]);
        Integer stop_month = Integer.valueOf(stop_split[1]);

        List<TotalInterRevenue> totalInterRevenues = new ArrayList<TotalInterRevenue>();
        for (int i = start_year; i <= stop_year; i++) {
            if (i == start_year && i == stop_year) {
                for (int j = start_month; j <= stop_month; j++) {
                    String month = "";
                    if (j < 10) {
                        month = i + "-" + "0" + j;
                    } else {
                        month = i + "-" + j;
                    }
                    List<InterRevenue> interRevenues = interRevenueRepository.findInterRevenuesByMonth(month);
                    Long total_success_cnt = 0L;
                    Double total_revenue = 0.0;
                    for (int k = 0; k < interRevenues.size(); k++) {
                        total_success_cnt += interRevenues.get(k).getSuccessCnt();
                        total_revenue += interRevenues.get(k).getRevenue();
                    }
                    totalInterRevenues.add(new TotalInterRevenue(
                            month,
                            total_success_cnt,
                            total_revenue
                    ));
                }
            } else if (i == start_year) {
                for (int j = start_month; j <= 12; j++) {
                    String month = "";
                    if (j < 10) {
                        month = i + "-" + "0" + j;
                    } else {
                        month = i + "-" + j;
                    }
                    List<InterRevenue> interRevenues = interRevenueRepository.findInterRevenuesByMonth(month);
                    Long total_success_cnt = 0L;
                    Double total_revenue = 0.0;
                    for (int k = 0; k < interRevenues.size(); k++) {
                        total_success_cnt += interRevenues.get(k).getSuccessCnt();
                        total_revenue += interRevenues.get(k).getRevenue();
                    }
                    totalInterRevenues.add(new TotalInterRevenue(
                            month,
                            total_success_cnt,
                            total_revenue
                    ));
                }
            } else if (i == stop_year) {
                for (int j = 1; j <= stop_month; j++) {
                    String month = "";
                    if (j < 10) {
                        month = i + "-" + "0" + j;
                    } else {
                        month = i + "-" + j;
                    }
                    List<InterRevenue> interRevenues = interRevenueRepository.findInterRevenuesByMonth(month);
                    Long total_success_cnt = 0L;
                    Double total_revenue = 0.0;
                    for (int k = 0; k < interRevenues.size(); k++) {
                        total_success_cnt += interRevenues.get(k).getSuccessCnt();
                        total_revenue += interRevenues.get(k).getRevenue();
                    }
                    totalInterRevenues.add(new TotalInterRevenue(
                            month,
                            total_success_cnt,
                            total_revenue
                    ));
                }
            } else {
                for (int j = 1; j <= 12; j++) {
                    String month = "";
                    if (j < 10) {
                        month = i + "-" + "0" + j;
                    } else {
                        month = i + "-" + j;
                    }
                    List<InterRevenue> interRevenues = interRevenueRepository.findInterRevenuesByMonth(month);
                    Long total_success_cnt = 0L;
                    Double total_revenue = 0.0;
                    for (int k = 0; k < interRevenues.size(); k++) {
                        total_success_cnt += interRevenues.get(k).getSuccessCnt();
                        total_revenue += interRevenues.get(k).getRevenue();
                    }
                    totalInterRevenues.add(new TotalInterRevenue(
                            month,
                            total_success_cnt,
                            total_revenue
                    ));
                }
            }
        }

        return totalInterRevenues;
    }
}
