package Dao;

import Dto.CategoryDTO;
import Dto.ProductDTO;

import java.util.List;

public interface ProductDao {

    boolean saveProductDetails(ProductDTO productDTO);

    List<CategoryDTO> getAllCategories();

    List<ProductDTO> getAllProducts(int sellerId);
    boolean updateProductDetailsById(int productId,double mrp,double discountedPrice,int stock,int stockThreshold);

    boolean deleteProductById(int productId);


    int getSellerIdByProductId(int productId);

    boolean isProductExistsForSeller(int productId, int sellerId);
}
