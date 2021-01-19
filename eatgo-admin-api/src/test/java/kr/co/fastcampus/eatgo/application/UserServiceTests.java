package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService=new UserService(userRepository);
    }

    @Test
    public void getUsers(){
        List<User> mockUsers=new ArrayList<>();
        mockUsers.add(User.builder().email("tester@example.com").name("Tester").level(1L).build());

        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users=userService.getUsers();
        User user=users.get(0);

        assertThat(user.getName(),is("Tester"));
    }

    @Test
    public void updateUser(){
        Long id=1004L;
        String email="admin@example.com";
        String name="Superman";
        Long level=100L;

        User mockUser=User.builder().email("tester@example.com").name("Tester").level(1L).build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user=userService.updateUser(id,email,name,level);

        assertThat(user.getName(),is("Superman"));
        assertThat(user.getLevel(),is(100L));
    }

    @Test
    public void deleteUser(){
        User mockUser=User.builder().email("tester@example.com").name("Tester").level(100L).build();

        given(userRepository.findById(1004L)).willReturn(Optional.of(mockUser));

        User user=userService.deactivateUser(1004L);

        assertThat(user.isAdmin(),is(false));
        assertThat(user.isActive(),is(false));
    }
}