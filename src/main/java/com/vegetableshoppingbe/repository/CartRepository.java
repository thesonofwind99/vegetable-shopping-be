package com.vegetableshoppingbe.repository;

import com.vegetableshoppingbe.entity.Cart;
import com.vegetableshoppingbe.enums.CartStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.vegetableshoppingbe.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query(value = "SELECT COUNT(*) AS total_orders " +
            "FROM cart " +
            "WHERE created_date >= DATE_SUB(NOW(), INTERVAL 1 WEEK )", nativeQuery = true)
    int countOrderInWeek();

    @Query("select year(c.createdDate) from Cart c " +
            "group by year(c.createdDate) " +
            "order by year(c.createdDate) asc ")
    List<Integer> findYearOrder();

    @Query("SELECT o.cartItems FROM Cart o WHERE o.cartId = ?1")
    List<CartItem> findCartItemsByCartId(int cartId);

    @Query("select c from Cart c " +
            "where c.cartStatus = :cartStatus")
    Page<Cart> getCartsByCartStatus(@Param("cartStatus") CartStatus cartStatus,
                                    Pageable pageable);

    @Query("select c from Cart c " +
            "where c.user.userId = :userId")
    List<Cart> getCartsByUser_UserId(@Param("userId") Integer userId);
    
}
