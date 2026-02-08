package service;

import Dao.Impl.OrderDaoImpl;
import Dao.ReviewsDao;
import Dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Impl.OrderServiceImpl;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderDaoImpl mockOrderRepo;
    private ReviewsDao mockReviewsRepo;
    private OrderServiceImpl service;

    @BeforeEach
    void setUp() {
        mockOrderRepo = mock(OrderDaoImpl.class);
        mockReviewsRepo = mock(ReviewsDao.class);

        service = new OrderServiceImpl(mockOrderRepo, mockReviewsRepo);
    }

    @Test
    void testPlaceOrder_Failure() {
        when(mockOrderRepo.createOrder(1, 1000.0)).thenReturn(0);

        boolean result = service.placeOrder(
                1,
                1000.0,
                Collections.emptyList(),
                new OrderAddressDTO(),
                new OrderAddressDTO(),
                new PaymentDTO()
        );

        assertFalse(result);
    }

    @Test
    void testPlaceOrder_Success() {
        when(mockOrderRepo.createOrder(1, 1000.0)).thenReturn(101);

        boolean result = service.placeOrder(
                1,
                1000.0,
                Collections.emptyList(),
                new OrderAddressDTO(),
                new OrderAddressDTO(),
                new PaymentDTO()
        );

        assertTrue(result);
        verify(mockOrderRepo, times(1)).clearCart(1);
    }

    @Test
    void testSubmitReview_NotPurchased() {
        ReviewDTO dto = new ReviewDTO();
        dto.setBuyerId(1);
        dto.setProductId(10);

        when(mockReviewsRepo.hasPurchased(1, 10)).thenReturn(false);

        boolean result = service.submitReview(dto);

        assertFalse(result);
        verify(mockReviewsRepo, never()).addReview(any());
    }

    @Test
    void testSubmitReview_Success() {
        ReviewDTO dto = new ReviewDTO();
        dto.setBuyerId(1);
        dto.setProductId(10);

        when(mockReviewsRepo.hasPurchased(1, 10)).thenReturn(true);
        when(mockReviewsRepo.addReview(dto)).thenReturn(true);

        boolean result = service.submitReview(dto);

        assertTrue(result);
        verify(mockReviewsRepo, times(1)).addReview(dto);
    }
}
