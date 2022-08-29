package ru.msaggik.spring.SpringBootSecurityCSRF.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.msaggik.spring.SpringBootSecurityCSRF.security.PersonDetails;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    // реализация метода для доступа в java потоку
    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        // получение доступа к объекту authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // получение доступа к personDetails (принципалу)
        PersonDetails personDetails = (PersonDetails)authentication.getPrincipal();
        System.out.println(personDetails.getPerson()); // вывод на экран полей пользователя
        return "hello";
    }
}
