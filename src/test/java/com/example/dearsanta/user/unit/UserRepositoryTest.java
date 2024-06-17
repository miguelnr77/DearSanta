/*package com.example.dearsanta.user.unit;

import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setEnabled(true);
        userRepository.save(user);

        assertTrue(userRepository.findByEmail("test@example.com").isPresent());
    }
}
*/