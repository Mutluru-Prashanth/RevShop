package Dao;

import Dto.BuyerDTO;
import Dto.LoginDTO;
import Dto.SecurityQuestionDTO;
import Dto.SellerDTO;

import java.util.List;

public interface RegistrationDao {

    List<SecurityQuestionDTO> getAllQuestions();

    boolean registerBuyer(BuyerDTO dto);
    boolean registerSeller(SellerDTO dto);

    LoginDTO login(String email);
    String getUserNameById(int userId,String role);

    String getPasswordByUserId(int userId);

    boolean updateUserPassword(int userId, String newPassword);



    String getQuestionByEmail(String email);

    boolean verifyAnswer(String email, String answer);

    boolean resetPassword(String email, String newPassword);

}
