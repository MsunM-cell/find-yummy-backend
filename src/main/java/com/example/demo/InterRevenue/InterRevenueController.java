package com.example.demo.InterRevenue;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/statistics")
@AllArgsConstructor
public class InterRevenueController {

    private InterRevenueService interRevenueService;

    @GetMapping
    public JSONObject getStatistics(@RequestParam(name = "start", required = false) String start,
                                    @RequestParam(name = "stop", required = false) String stop,
                                    @RequestParam(name = "city", required = false) String city) {
        List<InterRevenue> interRevenues = interRevenueService.getStatistics(start, stop, city);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("inter_revenues", interRevenues);

        return response;
    }

    @GetMapping(path = "total")
    public JSONObject getTotalStatistics(@RequestParam(name = "start", required = false) String start,
                                         @RequestParam(name = "stop", required = false) String stop) {
        List<TotalInterRevenue> totalInterRevenues = interRevenueService.getTotalStatistics(start, stop);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("total_inter_revenues", totalInterRevenues);

        return response;
    }
}
