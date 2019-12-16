package bborzechowski.basicDatabaseAuth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user", schema = "public")
public class UserApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String login;
    private String password;
    private String name;
    private String lastname;
    private int active;

    @JsonIgnore
    @ManyToMany( fetch = FetchType.EAGER) //jak skasuje role to skasuje użytkownika
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public UserApp(UserApp userApp){
        this.email = userApp.getEmail();
        this.login = userApp.getLogin();
        this.password = userApp.getPassword();
        this.name = userApp.getName();
        this.lastname = userApp.getLastname();
        this.active = userApp.getActive();
        this.roles = userApp.getRoles();

    }

    @Override
    public String toString() {
        return "UserApp{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", active=" + active +
                '}';
    }
}
