package bborzechowski.basicDatabaseAuth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomUserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public CustomUserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<UserApp> userAppOptional = userRepository.findUserByLogin(login);
        userAppOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return userAppOptional.map(CustomUserDetails::new).get();
    }

    public UserApp registerUser(UserApp user){
        Role role = roleRepository.findRoleById(2);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(1);
        user.setRoles(roles);
        UserApp result = userRepository.save(user);


        return result;

        }
    }

