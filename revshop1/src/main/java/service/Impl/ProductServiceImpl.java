package service.Impl;

import Dao.Impl.ProductDaoImpl;
import Dao.Impl.ReviewsDaoImpl;
import Dto.CategoryDTO;
import Dto.ProductDTO;
import Dto.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Dao.*;
import service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private static final Logger log =
            LoggerFactory.getLogger(BuyerProductServiceImpl.class);

    @Override
    public boolean addProduct(ProductDTO productDTO) {

        if (productDTO.getSellingPrice() > productDTO.getMrp()) {
            System.out.println("Selling price cannot be greater than MRP");
            return false;
        }

        ProductDao repo = new ProductDaoImpl();
        return repo.saveProductDetails(productDTO);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {

        ProductDao repo=new ProductDaoImpl();
        return repo.getAllCategories();

    }

    @Override
    public List<ProductDTO> getAllProducts(int sellerId) {

        ProductDao repo=new ProductDaoImpl();
        return repo.getAllProducts(sellerId);
    }

    @Override
    public boolean updateProductDetailsById(int productId,int sellerId,double mrp, double discountedPrice, int stock, int stockThreshold) {

            ProductDao productDao =new ProductDaoImpl();
        if (!productDao.isProductExistsForSeller(productId,sellerId)) {
            log.warn("Cannot add to cart. Product not found: {}", productId);
            return false;
        }
        ProductDao repo=new ProductDaoImpl();

        return repo.updateProductDetailsById(productId,mrp,discountedPrice,stock,stockThreshold);
    }

    @Override
    public boolean deleteProductById(int productId) {
        ProductDao repo=new ProductDaoImpl();

        return repo.deleteProductById(productId);

    }

    @Override
    public int getSellerIdByProductId(int productId) {
        ProductDao repo=new ProductDaoImpl();
        return repo.getSellerIdByProductId(productId);
    }

    @Override
    public List<ReviewDTO> getReviewsAtSeller(int sellerId) {
        ReviewsDao repo=new ReviewsDaoImpl();

        return repo.getReviewsAtSeller(sellerId);
    }



}
