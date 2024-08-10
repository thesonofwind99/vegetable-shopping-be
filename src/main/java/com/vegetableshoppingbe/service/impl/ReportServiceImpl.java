package com.vegetableshoppingbe.service.impl;

import com.vegetableshoppingbe.dto.response.TopProducts;
import com.vegetableshoppingbe.dto.response.TopUserBuyLotOf;
import com.vegetableshoppingbe.entity.Report;
import com.vegetableshoppingbe.repository.ReportRepository;
import com.vegetableshoppingbe.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    public List<Report> reportRevenueByMonth(int year) {
        return reportRepository.reportRevenueByMonth(year);
    }

    @Override
    public List<TopProducts> topProductsBestSeller() {
        List<Object[]> results = reportRepository.topProductsBestSeller();
        return results.stream()
                .map(result -> new TopProducts(
                        (String) result[0],
                        ((Long) result[1]).intValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TopUserBuyLotOf> TopUserBuyLotOf() {
        List<Object[]> results = reportRepository.topUsersBestSeller();
        return results.stream()
                .map(result -> new TopUserBuyLotOf(
                        (String) result[0],
                        (String) result[1],
                        (String) result[2],
                        (String) result[3],
                        (Double) result[4],
                        (Long) result[5]
                )).toList();
    }
}
