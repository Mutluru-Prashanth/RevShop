package service.Impl;

import Dto.*;
import Dao.Impl.OrderDaoImpl;
import Dao.ReviewsDao;
import Dao.Impl.ReviewsDaoImpl;
import service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static final Logger log =
            LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderDaoImpl orderRepo;
    private ReviewsDao reviewsRepo;

    // ✅ Default constructor (used in real application)
    public OrderServiceImpl() {
        this.orderRepo = new OrderDaoImpl();
        this.reviewsRepo = new ReviewsDaoImpl();
    }

    // ✅ Constructor for JUnit testing (Dependency Injection)
    public OrderServiceImpl(OrderDaoImpl orderRepo, ReviewsDao reviewsRepo) {
        this.orderRepo = orderRepo;
        this.reviewsRepo = reviewsRepo;
    }

    @Override
    public boolean placeOrder(int buyerId,
                              double totalAmount,
                              List<OrderItemDTO> items,
                              OrderAddressDTO shipping,
                              OrderAddressDTO billing,
                              PaymentDTO payment) {

        log.info("Placing order for buyerId {}", buyerId);

        // 1️⃣ Create order
        int orderId = orderRepo.createOrder(buyerId, totalAmount);

        if (orderId == 0) {
            log.error("Order creation failed for buyerId {}", buyerId);
            return false;
        }

        log.debug("Order created successfully with orderId {}", orderId);

        // 2️⃣ Add order items + reduce stock
        for (OrderItemDTO item : items) {
            item.setOrderId(orderId);

            orderRepo.addOrderItem(item);
            orderRepo.reduceStock(item.getProductId(), item.getQuantity());

            log.debug("Processed order item productId {} for orderId {}",
                    item.getProductId(), orderId);
        }

        // 3️⃣ Clear cart
        orderRepo.clearCart(buyerId);
        log.debug("Cart cleared for buyerId {}", buyerId);

        // 4️⃣ Addresses
        shipping.setOrderId(orderId);
        billing.setOrderId(orderId);

        orderRepo.addOrderAddress(shipping);
        orderRepo.addOrderAddress(billing);

        log.debug("Shipping and billing addresses added for orderId {}", orderId);

        // 5️⃣ Payment
        payment.setOrderId(orderId);
        orderRepo.addPayment(payment);

        log.info("Payment added and order completed for orderId {}", orderId);

        return true;
    }

    @Override
    public List<OrderDTO> getOrdersByBuyer(int buyerId) {

        log.info("Fetching orders for buyerId {}", buyerId);

        return orderRepo.getOrdersByBuyer(buyerId);
    }

    @Override
    public boolean submitReview(ReviewDTO dto) {

        log.info("Buyer {} attempting to review product {}",
                dto.getBuyerId(), dto.getProductId());

        if (!reviewsRepo.hasPurchased(dto.getBuyerId(), dto.getProductId())) {
            log.warn("Review rejected. Buyer {} has not purchased product {}",
                    dto.getBuyerId(), dto.getProductId());
            return false;
        }

        boolean result = reviewsRepo.addReview(dto);

        if (result) {
            log.info("Review successfully submitted for product {}",
                    dto.getProductId());
        } else {
            log.error("Failed to submit review for product {}",
                    dto.getProductId());
        }

        return result;
    }

    @Override
    public List<SellerOrderDTO> viewOrders(int sellerId) {

        log.info("Fetching orders for sellerId {}", sellerId);

        return orderRepo.getOrdersForSeller(sellerId);
    }
}
