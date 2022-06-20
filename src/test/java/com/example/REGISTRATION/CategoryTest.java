//package com.example.REGISTRATION;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import com.example.REGISTRATION.entity.Category;
//import com.example.REGISTRATION.repo.CategoryRepo;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Rollback(false)
//public class CategoryTest {
//	@Autowired
//	private CategoryRepo categoryRepo;
//	
//	@Test
//	public void TestCategory(){
//		Category category = new Category();
//		category.setId(1L);
//		category.setCategoryName("Tai nghe");
//		Category cateSave = categoryRepo.save(category);
//		assertThat(cateSave.getId() > 0);
//	}
//}
