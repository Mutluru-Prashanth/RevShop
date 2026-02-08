package service;

import Dto.BuyerDTO;
import Dto.LoginDTO;
import Dao.RegistrationDao;
import service.Impl.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegistrationServiceImplTest {

    private RegistrationDao mockDao;
    private RegistrationServiceImpl service;

    @BeforeEach
    void setup() {
        mockDao = Mockito.mock(RegistrationDao.class);
        service = new RegistrationServiceImpl(mockDao);
    }

    // ✅ Test 1: Successful Buyer Registration
    @Test
    void testRegisterBuyerSuccess() {

        BuyerDTO dto = new BuyerDTO();
        dto.setEmail("test@gmail.com");
        dto.setPassword("123456");
        dto.setPhone("9876543210");

        when(mockDao.registerBuyer(dto)).thenReturn(true);

        boolean result = service.registerBuyer(dto);

        assertTrue(result);
        verify(mockDao).registerBuyer(dto);
    }

    // ✅ Test 2: Invalid Email
    @Test
    void testRegisterBuyerInvalidEmail() {

        BuyerDTO dto = new BuyerDTO();
        dto.setEmail("invalidemail");
        dto.setPassword("123456");
        dto.setPhone("9876543210");

        boolean result = service.registerBuyer(dto);

        assertFalse(result);
        verify(mockDao, never()).registerBuyer(dto);
    }

    // ✅ Test 3: Login Success
    @Test
    void testLoginSuccess() {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId(1);
        loginDTO.setPassword("123456");
        loginDTO.setRole("BUYER");

        when(mockDao.login("test@gmail.com")).thenReturn(loginDTO);
        when(mockDao.getUserNameById(1, "BUYER")).thenReturn("Prashanth");

        String result = service.login("test@gmail.com", "123456");

        assertEquals("BUYER,Prashanth,1", result);
    }

    // ✅ Test 4: Wrong Password
    @Test
    void testLoginWrongPassword() {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId(1);
        loginDTO.setPassword("correctPass");
        loginDTO.setRole("BUYER");

        when(mockDao.login("test@gmail.com")).thenReturn(loginDTO);

        String result = service.login("test@gmail.com", "wrongPass");

        assertEquals("Password is Incorrect", result);
    }
}
