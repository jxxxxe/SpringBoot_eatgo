package kr.co.fastcampus.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserTests {

    @Test
    public void creation(){
        User user=User.builder().email("tester@example.com").name("테스터").level(3L).build();

        assertThat(user.getName(),is("테스터"));
        assertThat(user.isAdmin(),is(true));

        user.deactivate();

        assertThat(user.isActive(),is(false));
    }
}