package service.Impl;

import Dto.BuyerDTO;
import Dto.LoginDTO;
import Dto.SecurityQuestionDTO;
import Dto.SellerDTO;
import Dao.Impl.RegistrationDaoImpl;
import Dao.RegistrationDao;
import service.RegistrationService;

import java.util.List;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public List<SecurityQuestionDTO> getAllQuestions() {
        RegistrationDao repo=new RegistrationDaoImpl();
        return repo.getAllQuestions();
    }

    @Override
    public boolean registerBuyer(BuyerDTO dto) {

        if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
            System.out.println("Invalid email");
            return false;
        }

        if (dto.getPassword().length() < 6) {
            System.out.println("Password too short");
            return false;
        }

        if (dto.getPhone().length()!=10)
        {
            System.out.println("Phone number must be 10 digits");
            return false;
        }
        RegistrationDao repo=new RegistrationDaoImpl();
        boolean saved= repo.registerBuyer(dto);
        return saved;
    }

    @Override
    public boolean registerSeller(SellerDTO dto) {
        if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
            System.out.println("Invalid email");
            return false;
        }


        if (dto.getPassword().length() < 6) {
            System.out.println("Password too short");
            return false;
        }

        if (dto.getPhone().length()!=10)
        {
            System.out.println("Phone number must be 10 digits");
            return false;
        }
        RegistrationDao repo=new RegistrationDaoImpl();
        boolean saved= repo.registerSeller(dto);
        return saved;
    }

    @Override
    public String login(String email, String password) {

        RegistrationDao repo=new RegistrationDaoImpl();
        LoginDTO dto= repo.login(email);
        if(dto.getUserId()==0)
        {
            System.out.println("------");
            return "Email does not exists";
        }
        else
        {
            if(password.equals(dto.getPassword()))
            {
                String username=  repo.getUserNameById(dto.getUserId(),dto.getRole());


                return dto.getRole() +","+ username +","+dto.getUserId();
            }

            return "Password is Incorrect";
        }

    }

    @Override
    public void setNewPassword(int userid, String currentPassword, String newPassword, String confirmPassword) {

        RegistrationDao repo=new RegistrationDaoImpl();
        String password=repo.getPasswordByUserId(userid);
        System.out.println(userid);
        if(password!=null)
        {

            if(currentPassword.equals(password))
            {

                if(newPassword.equals(confirmPassword)) {
                   if( repo.updateUserPassword(userid, newPassword))
                   {
                       System.out.println("Password Changed Successfully");
                   }

                }
                else
                    System.out.println("New password and Confirm Password must be same");


            }
            else
                System.out.println("Passwords does not match");

        }
    }

    @Override
    public String getQuestionByEmail(String email) {
        RegistrationDao repo=new RegistrationDaoImpl();
        return repo.getQuestionByEmail(email);
    }

    @Override
    public boolean verifyAnswer(String email, String answer) {
        RegistrationDao repo=new RegistrationDaoImpl();

        return repo.verifyAnswer(email, answer);
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        RegistrationDao repo=new RegistrationDaoImpl();

        return repo.resetPassword(email, newPassword);
    }
}
