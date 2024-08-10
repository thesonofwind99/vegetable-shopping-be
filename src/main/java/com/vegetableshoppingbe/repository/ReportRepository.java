package com.vegetableshoppingbe.repository;

import com.vegetableshoppingbe.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query("select new Report (month(c.createdDate), sum(c.totalAmount)) " +
            "from Cart c " +
            "where year(c.createdDate) = :year " +
            "group by month(c.createdDate) " +
            "order by month(c.createdDate) asc ")
    List<Report> reportRevenueByMonth(@Param("year") int year);

    @Query("select c.product.productName, sum(c.quantity) " +
            "from CartItem c " +
            "group by c.product.productId " +
            "order by sum(c.quantity) desc " +
            "limit 10")
    List<Object[]> topProductsBestSeller();

    @Query("select c.user.username, c.user.fullname, c.user.email, c.user.phoneNumber,  sum(c.totalAmount), count(c.cartId) " +
            "from Cart c " +
            "group by c.user.username, c.user.fullname, c.user.phoneNumber, c.user.email " +
            "order by sum(c.totalAmount) desc " +
            "limit 10")
    List<Object[]> topUsersBestSeller();

}
