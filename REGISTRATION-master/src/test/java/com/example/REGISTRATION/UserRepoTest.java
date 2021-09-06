package com.example.REGISTRATION;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.REGISTRATION.entity.User;
import com.example.REGISTRATION.repo.UserRepo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepoTest {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setUsername("minh@gmail.com");
		user.setPassword("minh");
		user.setFullName("Hoàng Lê Minh");
		user.setPhoneNumber("0934690871");
		user.setUser_role("ADMIN");
		User savedUser = userRepo.save(user);
		User existUser = entityManager.find(User.class, savedUser.getId());
		assertThat(existUser.getUsername()).isEqualTo(user.getUsername());
	}

}
