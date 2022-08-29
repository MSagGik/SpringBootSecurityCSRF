package ru.msaggik.spring.SpringBootSecurityCSRF.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.msaggik.spring.SpringBootSecurityCSRF.models.Person;
import ru.msaggik.spring.SpringBootSecurityCSRF.repositories.PeopleRepository;

@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    // метод регистрации
    @Transactional
    public void register(Person person){
        // шифрование пароля при регистрации
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        // обновление значения пароля в поле Password
        person.setPassword(encodedPassword);

        peopleRepository.save(person);
    }
}
