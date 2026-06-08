package org.example.modules.cookie.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cookie")
public class CookieController {

    @GetMapping("/guardar")
    public String guardarCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("user_pref", "dark_mode");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "Cookie guardada exitosamente";
    }

    @GetMapping("/leer")
    public String leerCookie(@CookieValue(value = "user_pref", defaultValue = "No existe la cookie") String cookieVal) {
        return "El valor de la cookie es: " + cookieVal;
    }

    @GetMapping("/borrar")
    public String borrarCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("user_pref", null);
        cookie.setMaxAge(0); // Borra la cookie inmediatamente
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "Cookie borrada exitosamente";
    }
}
