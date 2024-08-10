package com.vegetableshoppingbe.service;

import com.vegetableshoppingbe.dto.response.TopProducts;
import com.vegetableshoppingbe.dto.response.TopUserBuyLotOf;
import com.vegetableshoppingbe.entity.Report;

import java.util.List;

public interface ReportService {

    List<Report> reportRevenueByMonth(int year);

    List<TopProducts> topProductsBestSeller();

    List<TopUserBuyLotOf> TopUserBuyLotOf();

}
