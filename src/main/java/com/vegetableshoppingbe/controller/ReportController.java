package com.vegetableshoppingbe.controller;

import com.vegetableshoppingbe.dto.response.TopProducts;
import com.vegetableshoppingbe.dto.response.TopUserBuyLotOf;
import com.vegetableshoppingbe.entity.Report;
import com.vegetableshoppingbe.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
@CrossOrigin(origins = "http://localhost:8081")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reportRevenueByMonth/{year}")
    public ResponseEntity<List<Report>> reportRevenueByMonth(@PathVariable int year) {
        return ResponseEntity.ok(reportService.reportRevenueByMonth(year));
    }

    @GetMapping("/reportTopProducts")
    public ResponseEntity<List<TopProducts>> reportTopProducts() {
        return ResponseEntity.ok(reportService.topProductsBestSeller());
    }

    @GetMapping("/reportTopUsersBuyLotOf")
    public ResponseEntity<List<TopUserBuyLotOf>> topUser() {
        return ResponseEntity.ok(reportService.TopUserBuyLotOf());
    }

}
