package ru.msaggik.spring.SpringBootSecurityCSRF.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.msaggik.spring.SpringBootSecurityCSRF.models.Person;

import java.util.Collection;

public class PersonDetails implements UserDetails {
    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // действительность аккаунта
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // не заблокирован
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // пароль не просрочен
    }

    @Override
    public boolean isEnabled() {
        return true; // включение аккаунта
    }

    // метод доступа к аутентифицированному пользователю для получения имеющихся полей
    public Person getPerson() {
        return this.person;
    }
}
