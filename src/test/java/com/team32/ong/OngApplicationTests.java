package com.team32.ong;

import com.team32.ong.model.OrganizationEntity;
import com.team32.ong.repository.OrganizationCrudRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OngApplicationTests {

	@Autowired
	private OrganizationCrudRepository repo;

	@Test
	void saveOrganizationEntity() {

		repo.save(
				OrganizationEntity
						.builder()
						.name("Organizacion")
						.image("URL")
						.address("Avenida siempreviva 743")
						.phone(2234124)
						.email("organizacion@organizacion.com")
						.welcomeText("Bienvenidos a organizacion")
						.aboutUsText("Somos muchos y con muchas ganas de ser mas")
						.build()
		);
	}

}
