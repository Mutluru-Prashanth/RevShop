package service.Impl;

import Dto.CartItemsDTO;
import Dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Dao.BuyerProductDao;
import Dao.Impl.BuyerProductDaoImpl;
import service.BuyerProductService;

import java.util.List;

public class BuyerProductServiceImpl implements BuyerProductService {

    private static final Logger log =
            LoggerFactory.getLogger(BuyerProductServiceImpl.class);

    BuyerProductDao repo;

    // Default constructor (used in real project)
    public BuyerProductServiceImpl() {
        this.repo = new BuyerProductDaoImpl();
    }

    // Constructor for JUnit Testing (Dependency Injection)
    public BuyerProductServiceImpl(BuyerProductDao repo) {
        this.repo = repo;
    }

    @Override
    public List<ProductDTO> viewAllProducts() {
        log.info("Buyer requested to view all products");
        return repo.viewAllProducts();
    }

    @Override
    public ProductDTO viewProductDetails(int productId) {
        log.info("Buyer requested product details for productId {}", productId);
        return repo.viewProductDetails(productId);
    }

    @Override
    public List<ProductDTO> searchByCategoryName(String categoryName) {
        log.info("Buyer searching products by category: {}", categoryName);
        return repo.searchByCategoryName(categoryName);
    }

    @Override
    public boolean addToFavourites(int buyerId, int productId) {

        log.info("Buyer {} attempting to add product {} to favourites", buyerId, productId);

        if (!repo.isProductExists(productId)) {
            log.warn("Cannot add to favourites. Product not found: {}", productId);
            return false;
        }

        boolean result = repo.addToFavourites(buyerId, productId);

        if (result) {
            log.info("Product {} added to favourites for buyer {}", productId, buyerId);
        } else {
            log.warn("Product {} could not be added to favourites for buyer {}",
                    productId, buyerId);
        }

        return result;
    }

    @Override
    public boolean addToCart(int buyerId, int productId) {

        log.info("Buyer {} attempting to add product {} to cart", buyerId, productId);

        if (!repo.isProductExists(productId)) {
            log.warn("Cannot add to cart. Product not found: {}", productId);
            return false;
        }

        int availableStock = repo.getStockByProductId(productId);
        int cartQty = repo.getCartQuantity(buyerId, productId);

        if (cartQty + 1 > availableStock) {
            log.warn("Stock limit exceeded.");
            return false;
        }

        return repo.addToCart(buyerId, productId);
    }

    @Override
    public List<CartItemsDTO> viewCart(int buyerId) {
        return repo.viewCart(buyerId);
    }

    @Override
    public boolean increaseQuantity(int buyerId, int productId) {

        int availableStock = repo.getStockByProductId(productId);
        int cartQty = repo.getCartQuantity(buyerId, productId);

        if (cartQty + 1 > availableStock) {
            return false;
        }

        return repo.increaseQuantity(buyerId, productId);
    }

    @Override
    public void decreaseQuantity(int buyerId, int productId) {
        repo.decreaseQuantity(buyerId, productId);
    }

    @Override
    public void removeItem(int buyerId, int productId) {
        repo.removeFromCart(buyerId, productId);
    }

    @Override
    public List<ProductDTO> viewFavourites(int buyerId) {
        return repo.viewFavourites(buyerId);
    }

    @Override
    public List<ProductDTO> searchByKeyword(String keyword) {
        return repo.searchByKeyword(keyword);
    }
}
