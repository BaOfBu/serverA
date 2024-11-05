package org.example.servera;

import org.example.servera.entity.RefreshToken;
import org.example.servera.entity.User;
import org.example.servera.repository.RefreshTokenRepository;
import org.example.servera.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ServerAApplication { //implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ServerAApplication.class, args);
    }
//    @Autowired
//    RefreshTokenRepository refreshTokenRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Khi chương trình chạy
//        // Insert vào csdl một refreshtoken
//        RefreshToken refreshToken = new RefreshToken();
//        refreshToken.setToken("refreshToken");
//        refreshTokenRepository.save(refreshToken);
//
//    }

}
