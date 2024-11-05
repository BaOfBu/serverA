package org.example.servera;

import org.example.servera.entity.User;
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
//    UserRepository userRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Khi chương trình chạy
//        // Insert vào csdl một user.
//        User user = new User();
//        user.setUsername("admin");
//        user.setPassword(passwordEncoder.encode("123"));
//        userRepository.save(user);
//        System.out.println(user);
//    }

}
