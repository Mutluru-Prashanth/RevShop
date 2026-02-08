package service;

import Dao.BuyerProductDao;
import Dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.Impl.BuyerProductServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuyerProductServiceImplTest {

    private BuyerProductDao mockRepo;
    private BuyerProductServiceImpl service;

    @BeforeEach
    void setUp() {
        mockRepo = Mockito.mock(BuyerProductDao.class);
        service = new BuyerProductServiceImpl(mockRepo);
    }

    @Test
    void testViewAllProducts() {
        when(mockRepo.viewAllProducts()).thenReturn(Collections.emptyList());

        List<ProductDTO> products = service.viewAllProducts();

        assertNotNull(products);
        verify(mockRepo, times(1)).viewAllProducts();
    }

    @Test
    void testAddToFavourites_ProductNotExists() {
        when(mockRepo.isProductExists(1)).thenReturn(false);

        boolean result = service.addToFavourites(10, 1);

        assertFalse(result);
        verify(mockRepo, never()).addToFavourites(anyInt(), anyInt());
    }

    @Test
    void testAddToFavourites_Success() {
        when(mockRepo.isProductExists(1)).thenReturn(true);
        when(mockRepo.addToFavourites(10, 1)).thenReturn(true);

        boolean result = service.addToFavourites(10, 1);

        assertTrue(result);
        verify(mockRepo, times(1)).addToFavourites(10, 1);
    }

    @Test
    void testAddToCart_StockExceeded() {
        when(mockRepo.isProductExists(1)).thenReturn(true);
        when(mockRepo.getStockByProductId(1)).thenReturn(1);
        when(mockRepo.getCartQuantity(10, 1)).thenReturn(1);

        boolean result = service.addToCart(10, 1);

        assertFalse(result);
        verify(mockRepo, never()).addToCart(anyInt(), anyInt());
    }

    @Test
    void testAddToCart_Success() {
        when(mockRepo.isProductExists(1)).thenReturn(true);
        when(mockRepo.getStockByProductId(1)).thenReturn(5);
        when(mockRepo.getCartQuantity(10, 1)).thenReturn(1);
        when(mockRepo.addToCart(10, 1)).thenReturn(true);

        boolean result = service.addToCart(10, 1);

        assertTrue(result);
        verify(mockRepo, times(1)).addToCart(10, 1);
    }

    @Test
    void testIncreaseQuantity_Fail() {
        when(mockRepo.getStockByProductId(1)).thenReturn(1);
        when(mockRepo.getCartQuantity(10, 1)).thenReturn(1);

        boolean result = service.increaseQuantity(10, 1);

        assertFalse(result);
        verify(mockRepo, never()).increaseQuantity(anyInt(), anyInt());
    }

    @Test
    void testIncreaseQuantity_Success() {
        when(mockRepo.getStockByProductId(1)).thenReturn(5);
        when(mockRepo.getCartQuantity(10, 1)).thenReturn(1);
        when(mockRepo.increaseQuantity(10, 1)).thenReturn(true);

        boolean result = service.increaseQuantity(10, 1);

        assertTrue(result);
        verify(mockRepo, times(1)).increaseQuantity(10, 1);
    }
}
