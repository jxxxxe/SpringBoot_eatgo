package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User addUser(String email, String name) {
        User user=User.builder().email(email).name(name).level(1L).build();
        userRepository.save(user);
        return user;
    }

    public User updateUser(Long id, String email, String name, Long level) {
        User user=userRepository.findById(id).orElse(null);

        user.setEmail(email);
        user.setName(name);
        user.setLevel(level);

        userRepository.save(user);

        return user;
    }

    public User deactivateUser(Long id) {
        User user=userRepository.findById(id).orElse(null);

        user.deactivate();

        userRepository.save(user);

        return user;
    }
}
