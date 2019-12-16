package bborzechowski.basicDatabaseAuth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private CustomUserService customUserService;


    public HomeController(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @GetMapping("/")
    public String homePage(Model model){
        SecurityContext context = SecurityContextHolder.getContext();
        model.addAttribute("message", "You are logged in as " + context.getAuthentication().getName());
        return "index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String securedAdminPage(){
        return "admin_secured";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String securedUserPage(){
        return "user_secured";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerPage(@ModelAttribute UserApp user){
//        UserApp userApp = new UserApp();
//        userApp.setEmail(user.getEmail());
//        userApp.setLogin(user.getLogin());
//        userApp.setPassword(user.getPassword());
//        userApp.setName(user.getName());
//        userApp.setLastname(user.getLastname());
        customUserService.registerUser(user);

        return "redirect:/";
    }
}
