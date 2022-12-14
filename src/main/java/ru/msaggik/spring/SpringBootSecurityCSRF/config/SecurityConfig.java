package ru.msaggik.spring.SpringBootSecurityCSRF.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.msaggik.spring.SpringBootSecurityCSRF.services.PersonDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // внедрение PersonDetailsService для аутентификации
    private final PersonDetailsService personDetailsService;
    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    // переопределение формы для логина
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
//                // отключение защиты от межсайтовой подделки запросов
//                .csrf().disable()
                // конфигурация авторизации
                .authorizeRequests()
                // просмотр пришедшего запроса в приложение и допуск всех лишь по определённым адресам
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                // для всех других запросов пользователь должен быть аутентифицирован
                .anyRequest().authenticated()
                // переход от авторизации к переопределению формы для логина
                .and()
                // переопределение формы для логина
                .formLogin().loginPage("/auth/login")
                // задание адреса куда отправлять данные с формы
                .loginProcessingUrl("/process_login")
                // после успешной аутентификации редирект на установленную страницу
                .defaultSuccessUrl("/hello")
                // после не успешной аутентификации редирект на стартовую страницу с ошибкой
                .failureUrl("/auth/login?error")
                .and()
                // процесс разлогинивания: 1) при переходе на url "/logout" будет производиться выход из аккаунта
                // и выход из сессии; 2) при успешном разлогинивании будет переброс на url "/auth/login"
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login");
        // конфигурация Spring Security

    }

    // метод настройки аутентификации
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authProvider); // провайдер аутентификации
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    // указание шифрования пароля
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(); // алгоритм шифрования
    }
}
