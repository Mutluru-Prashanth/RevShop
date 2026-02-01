package service;

import Dto.CategoryDTO;
import Dto.ProductDTO;
import Dto.ReviewDTO;

import java.util.List;

public interface ProductService {

    boolean addProduct(ProductDTO productDTO);
    List<CategoryDTO> getAllCategories();

    List<ProductDTO> getAllProducts(int sellerId);

    boolean updateProductDetailsById(int productId,int sellerId,double mrp,double discountedPrice,int stock,int stockThreshold);
    boolean deleteProductById(int productId);
    int getSellerIdByProductId(int productId);

    List<ReviewDTO> getReviewsAtSeller(int sellerId);

}
