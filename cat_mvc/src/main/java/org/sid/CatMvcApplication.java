package org.sid;

import org.sid.dao.ProduitRepository;
import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.sid.entities.Produit;
import org.sid.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;





@SpringBootApplication
public class CatMvcApplication implements CommandLineRunner {
@Autowired
ProduitRepository produitRepository;
@Autowired
private AccountService accountService;
	public static void main(String[] args) {
		SpringApplication.run(CatMvcApplication.class, args);
	}
	@Bean
    public BCryptPasswordEncoder getBCPE() {
    	return new BCryptPasswordEncoder();
    }
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		accountService.saveUser(new AppUser(null, "admin", "1234",null));
		accountService.saveUser(new AppUser(null, "user", "1234",null));
		accountService.saveRole(new AppRole(null,"ADMIN"));
		accountService.saveRole(new AppRole(null,"USER"));
		accountService.addRoleToUser("admin", "ADMIN");
		accountService.addRoleToUser("admin", "USER");
		accountService.addRoleToUser("user", "USER");
		
		produitRepository.save(new Produit(null,"LX 4352",670,90));
		produitRepository.save(new Produit(null,"HP 45782",1300,12));
		produitRepository.save(new Produit(null,"Epson 25",450,10));
		
		produitRepository.findAll().forEach(p->{
			System.out.println(p.getDesignation());
		});
		
	}

}
