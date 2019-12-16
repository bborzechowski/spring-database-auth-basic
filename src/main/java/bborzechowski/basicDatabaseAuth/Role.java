package bborzechowski.basicDatabaseAuth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "role", schema = "public")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    private String role;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")//, cascade = CascadeType.ALL)
    private Set<UserApp> users = new HashSet<>();


}
