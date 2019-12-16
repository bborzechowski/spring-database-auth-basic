package bborzechowski.basicDatabaseAuth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUser {

    private String email;
    private String login;
    private String password;
    private String name;
    private String lastname;

}
