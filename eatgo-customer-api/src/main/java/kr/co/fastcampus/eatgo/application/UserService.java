package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String email, String name, String password) {
        Optional<User> existedUser=userRepository.findByEmail(email);
        if(existedUser.isPresent()){
            throw new EmailExistedException(email);
        }

        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String encodedPassword =passwordEncoder.encode(password);

        User user=User.builder().email(email).name(name).level(1L).password(encodedPassword).build();

        userRepository.save(user);

        return user;
    }
}
