package bborzechowski.basicDatabaseAuth;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//jak true to najpierw sprawdza auteryzacja i wykonuje metode, jesli false najpierw wykonuje metode a na koncu sprawdza auteryzacje
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class BasicSecurityDatabaseConfig extends WebSecurityConfigurerAdapter {

    private CustomUserService customUserService;
    private PasswordEncoder passwordEncoder;

    public BasicSecurityDatabaseConfig(CustomUserService customUserService, PasswordEncoder passwordEncoder) {
        this.customUserService = customUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().disable().csrf().disable();

        http.authorizeRequests()  //włączamy filtr autoryzacyjny
                .antMatchers("/login**", "/register**").permitAll() //wyjątki filtra autoryzacyjnego(wejdziemy do ściezki /login bez logowania)
                .and()  //spójnik łączący obiekty http
                .formLogin() //włączamy strone logowania
                .loginPage("/login") //ustawiamy adres do strony logowania
                .loginProcessingUrl("/signin")  //ustawiamy adres, pod który formularz html wyśle dane
                .usernameParameter("username") //parametr formularza (input name=...)
                .passwordParameter("password")  //parametr formularza (input name=...)
                .successHandler((req, res, auth) -> {  //przechwytuje prawidłowe zalogowanie
                    for (GrantedAuthority authority : auth.getAuthorities()) {  //przechwytywacz momentu poprawnego zalogowania
                        System.out.println(authority.getAuthority());
                    }
                    System.out.println(auth.getName());
                    System.out.println(req.getRequestURI()); // gdzie chciał sie dostać
                    System.out.println(auth.toString());
                    res.sendRedirect("/");  //przekerowanie na podany url "/"
                })
                // .successForwardUrl("/") //to samo co successHandler tylko prosciej i przechodzi odrazu do "/" (pominiety, gdy istnieje juz successHandler w obiekcie http)
                .failureUrl("/login?error='error!!!'") //konf. w przypadku błednego logowania
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")  //url do wylogowania
                .logoutSuccessHandler((req, res, auth) ->{  //przechwytywanie błednego logowania
                    req.getSession().setAttribute("message", "you are logged out");
                    res.sendRedirect("/login");
                })
                .permitAll();  //wyłacz z filtrowania ustawienia logout()

        http.headers().frameOptions().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

            auth
                .userDetailsService(customUserService)
                .passwordEncoder(passwordEncoder);
    }
}
