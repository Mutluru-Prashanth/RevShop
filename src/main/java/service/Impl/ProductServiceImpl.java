package service.Impl;

import Dao.ProductDao;
import Dao.ReviewsDao;
import Dao.Impl.ProductDaoImpl;
import Dao.Impl.ReviewsDaoImpl;
import Dto.CategoryDTO;
import Dto.ProductDTO;
import Dto.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private static final Logger log =
            LoggerFactory.getLogger(ProductServiceImpl.class);

    private ProductDao productRepo;
    private ReviewsDao reviewsRepo;

    // ✅ Default constructor (for real application)
    public ProductServiceImpl() {
        this.productRepo = new ProductDaoImpl();
        this.reviewsRepo = new ReviewsDaoImpl();
    }

    // ✅ Constructor for JUnit (Dependency Injection)
    public ProductServiceImpl(ProductDao productRepo, ReviewsDao reviewsRepo) {
        this.productRepo = productRepo;
        this.reviewsRepo = reviewsRepo;
    }

    @Override
    public boolean addProduct(ProductDTO productDTO) {

        log.info("Attempting to add product: {}", productDTO.getProductName());

        if (productDTO.getSellingPrice() > productDTO.getMrp()) {
            log.error("Selling price cannot be greater than MRP for product: {}",
                    productDTO.getProductName());
            return false;
        }

        boolean result = productRepo.saveProductDetails(productDTO);

        if (result) {
            log.info("Product added successfully: {}", productDTO.getProductName());
        } else {
            log.error("Failed to add product: {}", productDTO.getProductName());
        }

        return result;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {

        log.info("Fetching all categories");

        return productRepo.getAllCategories();
    }

    @Override
    public List<ProductDTO> getAllProducts(int sellerId) {

        log.info("Fetching all products for sellerId {}", sellerId);

        return productRepo.getAllProducts(sellerId);
    }

    @Override
    public boolean updateProductDetailsById(int productId,
                                            int sellerId,
                                            double mrp,
                                            double discountedPrice,
                                            int stock,
                                            int stockThreshold) {

        log.info("Updating product {} for seller {}", productId, sellerId);

        if (!productRepo.isProductExistsForSeller(productId, sellerId)) {
            log.warn("Product not found for seller. productId={}, sellerId={}",
                    productId, sellerId);
            return false;
        }

        boolean result = productRepo.updateProductDetailsById(
                productId, mrp, discountedPrice, stock, stockThreshold);

        if (result) {
            log.info("Product updated successfully. productId={}", productId);
        } else {
            log.error("Failed to update product. productId={}", productId);
        }

        return result;
    }

    @Override
    public boolean deleteProductById(int productId) {

        log.info("Deleting product with productId {}", productId);

        boolean result = productRepo.deleteProductById(productId);

        if (result) {
            log.info("Product deleted successfully. productId={}", productId);
        } else {
            log.error("Failed to delete product. productId={}", productId);
        }

        return result;
    }

    @Override
    public int getSellerIdByProductId(int productId) {

        log.debug("Fetching sellerId for productId {}", productId);

        return productRepo.getSellerIdByProductId(productId);
    }

    @Override
    public List<ReviewDTO> getReviewsAtSeller(int sellerId) {

        log.info("Fetching reviews for sellerId {}", sellerId);

        return reviewsRepo.getReviewsAtSeller(sellerId);
    }
}
