package service;

import Dao.ProductDao;
import Dao.ReviewsDao;
import Dto.CategoryDTO;
import Dto.ProductDTO;
import Dto.ReviewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Impl.ProductServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductDao mockProductDao;
    private ReviewsDao mockReviewsDao;
    private ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        mockProductDao = mock(ProductDao.class);
        mockReviewsDao = mock(ReviewsDao.class);
        service = new ProductServiceImpl(mockProductDao, mockReviewsDao);
    }

    // ===============================
    // addProduct()
    // ===============================

    @Test
    void testAddProduct_SellingPriceGreaterThanMrp() {
        ProductDTO dto = new ProductDTO();
        dto.setProductName("Test Product");
        dto.setMrp(1000);
        dto.setSellingPrice(1200);

        boolean result = service.addProduct(dto);

        assertFalse(result);
        verify(mockProductDao, never()).saveProductDetails(any());
    }

    @Test
    void testAddProduct_Success() {
        ProductDTO dto = new ProductDTO();
        dto.setProductName("Test Product");
        dto.setMrp(1000);
        dto.setSellingPrice(800);

        when(mockProductDao.saveProductDetails(dto)).thenReturn(true);

        boolean result = service.addProduct(dto);

        assertTrue(result);
        verify(mockProductDao, times(1)).saveProductDetails(dto);
    }

    // ===============================
    // getAllCategories()
    // ===============================

    @Test
    void testGetAllCategories() {
        when(mockProductDao.getAllCategories())
                .thenReturn(Collections.emptyList());

        List<CategoryDTO> result = service.getAllCategories();

        assertNotNull(result);
        verify(mockProductDao, times(1)).getAllCategories();
    }

    // ===============================
    // getAllProducts()
    // ===============================

    @Test
    void testGetAllProducts() {
        when(mockProductDao.getAllProducts(1))
                .thenReturn(Collections.emptyList());

        List<ProductDTO> result = service.getAllProducts(1);

        assertNotNull(result);
        verify(mockProductDao, times(1)).getAllProducts(1);
    }

    // ===============================
    // updateProductDetailsById()
    // ===============================

    @Test
    void testUpdateProduct_ProductNotExists() {
        when(mockProductDao.isProductExistsForSeller(1, 10))
                .thenReturn(false);

        boolean result = service.updateProductDetailsById(
                1, 10, 1000, 800, 10, 2);

        assertFalse(result);
        verify(mockProductDao, never())
                .updateProductDetailsById(anyInt(), anyDouble(),
                        anyDouble(), anyInt(), anyInt());
    }

    @Test
    void testUpdateProduct_Success() {
        when(mockProductDao.isProductExistsForSeller(1, 10))
                .thenReturn(true);

        when(mockProductDao.updateProductDetailsById(
                1, 1000, 800, 10, 2))
                .thenReturn(true);

        boolean result = service.updateProductDetailsById(
                1, 10, 1000, 800, 10, 2);

        assertTrue(result);

        verify(mockProductDao, times(1))
                .updateProductDetailsById(1, 1000, 800, 10, 2);
    }

    // ===============================
    // deleteProductById()
    // ===============================

    @Test
    void testDeleteProduct_Success() {
        when(mockProductDao.deleteProductById(1))
                .thenReturn(true);

        boolean result = service.deleteProductById(1);

        assertTrue(result);
        verify(mockProductDao, times(1)).deleteProductById(1);
    }

    // ===============================
    // getSellerIdByProductId()
    // ===============================

    @Test
    void testGetSellerIdByProductId() {
        when(mockProductDao.getSellerIdByProductId(1))
                .thenReturn(100);

        int result = service.getSellerIdByProductId(1);

        assertEquals(100, result);
        verify(mockProductDao, times(1))
                .getSellerIdByProductId(1);
    }

    // ===============================
    // getReviewsAtSeller()
    // ===============================

    @Test
    void testGetReviewsAtSeller() {
        when(mockReviewsDao.getReviewsAtSeller(10))
                .thenReturn(Collections.emptyList());

        List<ReviewDTO> result =
                service.getReviewsAtSeller(10);

        assertNotNull(result);
        verify(mockReviewsDao, times(1))
                .getReviewsAtSeller(10);
    }
}
