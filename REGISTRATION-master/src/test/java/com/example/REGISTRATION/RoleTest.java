package com.example.REGISTRATION;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.REGISTRATION.entity.Role;
import com.example.REGISTRATION.repo.RoleRepo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleTest {
	@Autowired
	private RoleRepo roleRepo;
	
	@Test
	public void TestRole(){
		Role role = new Role();
		role.setId(2L);
		role.setRoleName("USER");
		roleRepo.save(role);
		assertThat(role.getId() > 0);
	}
}
