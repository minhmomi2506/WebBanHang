package com.example.Minh.WebSpringBoot;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.Minh.WebSpringBoot.Entity.User;
import com.example.Minh.WebSpringBoot.Repo.UserRepo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserTest {
	@Autowired
	private UserRepo userRepo;
	
	@Test
	public void testAddUser() {
		User user = new User();
		user.setId(1L);
		user.setUserId("minh");
		user.setPassword("minh");
		user.setPhoneNumber("093-469-0871");
		user.setAddress("Nguyen Cong Tru Street, Ha Noi");
		userRepo.save(user);
		assertThat(user.getId() > 0);
	}
}
