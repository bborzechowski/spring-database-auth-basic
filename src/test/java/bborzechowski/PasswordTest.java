package bborzechowski;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordTest {

    @Test
    public void getPasswordHash(){
        PasswordEncoder pass = new BCryptPasswordEncoder();
        System.out.println(pass.encode("user"));
    }
}
