package service.Impl;

import Dto.BuyerDTO;
import Dto.LoginDTO;
import Dto.SecurityQuestionDTO;
import Dto.SellerDTO;
import Dao.Impl.RegistrationDaoImpl;
import Dao.RegistrationDao;
import service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger log =
            LoggerFactory.getLogger(RegistrationServiceImpl.class);

    private RegistrationDao repo;

    // ✅ Default constructor (for normal project run)
    public RegistrationServiceImpl() {
        this.repo = new RegistrationDaoImpl();
    }

    // ✅ Constructor injection (for JUnit testing)
    public RegistrationServiceImpl(RegistrationDao repo) {
        this.repo = repo;
    }

    @Override
    public List<SecurityQuestionDTO> getAllQuestions() {

        log.info("Fetching all security questions");

        return repo.getAllQuestions();
    }

    @Override
    public boolean registerBuyer(BuyerDTO dto) {

        log.info("Attempting buyer registration for email {}", dto.getEmail());

        if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
            log.warn("Invalid email provided for buyer registration");
            return false;
        }

        if (dto.getPassword().length() < 6) {
            log.warn("Password too short for buyer registration");
            return false;
        }

        if (dto.getPhone().length() != 10) {
            log.warn("Invalid phone number for buyer registration");
            return false;
        }

        boolean saved = repo.registerBuyer(dto);

        if (saved) {
            log.info("Buyer registered successfully: {}", dto.getEmail());
        } else {
            log.error("Buyer registration failed: {}", dto.getEmail());
        }

        return saved;
    }

    @Override
    public boolean registerSeller(SellerDTO dto) {

        log.info("Attempting seller registration for email {}", dto.getEmail());

        if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
            log.warn("Invalid email provided for seller registration");
            return false;
        }

        if (dto.getPassword().length() < 6) {
            log.warn("Password too short for seller registration");
            return false;
        }

        if (dto.getPhone().length() != 10) {
            log.warn("Invalid phone number for seller registration");
            return false;
        }

        boolean saved = repo.registerSeller(dto);

        if (saved) {
            log.info("Seller registered successfully: {}", dto.getEmail());
        } else {
            log.error("Seller registration failed: {}", dto.getEmail());
        }

        return saved;
    }

    @Override
    public String login(String email, String password) {

        log.info("Login attempt for email {}", email);

        LoginDTO dto = repo.login(email);

        if (dto.getUserId() == 0) {
            log.warn("Login failed. Email does not exist: {}", email);
            return "Email does not exists";
        } else {

            if (password.equals(dto.getPassword())) {

                String username =
                        repo.getUserNameById(dto.getUserId(), dto.getRole());

                log.info("Login successful for userId {}", dto.getUserId());

                return dto.getRole() + "," + username + "," + dto.getUserId();
            }

            log.warn("Incorrect password attempt for email {}", email);
            return "Password is Incorrect";
        }
    }

    @Override
    public void setNewPassword(int userid,
                               String currentPassword,
                               String newPassword,
                               String confirmPassword) {

        log.info("Password change attempt for userId {}", userid);

        String password = repo.getPasswordByUserId(userid);

        if (password != null) {

            if (currentPassword.equals(password)) {

                if (newPassword.equals(confirmPassword)) {

                    if (repo.updateUserPassword(userid, newPassword)) {
                        log.info("Password changed successfully for userId {}", userid);
                    }

                } else {
                    log.warn("New password and confirm password do not match for userId {}",
                            userid);
                }

            } else {
                log.warn("Current password mismatch for userId {}", userid);
            }
        }
    }

    @Override
    public String getQuestionByEmail(String email) {

        log.info("Fetching security question for email {}", email);

        return repo.getQuestionByEmail(email);
    }

    @Override
    public boolean verifyAnswer(String email, String answer) {

        log.info("Verifying security answer for email {}", email);

        return repo.verifyAnswer(email, answer);
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {

        log.info("Reset password attempt for email {}", email);

        boolean result = repo.resetPassword(email, newPassword);

        if (result) {
            log.info("Password reset successful for email {}", email);
        } else {
            log.error("Password reset failed for email {}", email);
        }

        return result;
    }
}
