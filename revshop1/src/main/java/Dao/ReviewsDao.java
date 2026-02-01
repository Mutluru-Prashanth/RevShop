package Dao;

import Dto.ReviewDTO;

import java.util.List;

public interface ReviewsDao {
    boolean hasPurchased(int buyerId, int productId);

    boolean addReview(ReviewDTO dto);

    List<ReviewDTO> getReviewsByProduct(int productId);


    List<ReviewDTO> getReviewsAtSeller(int sellerId);
}
