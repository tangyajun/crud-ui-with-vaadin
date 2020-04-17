package crudwithvaadin.cruduiwithvaadin;

import crudwithvaadin.cruduiwithvaadin.web.domain.Customer;
import crudwithvaadin.cruduiwithvaadin.web.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author tangyajun
 */
@SpringBootApplication
public class CrudUiWithVaadinApplication {
	private static final Logger log = LoggerFactory.getLogger(CrudUiWithVaadinApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CrudUiWithVaadinApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(CustomerRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Customer("Jack", 1,20,"1861111121","Jack@test.com","广东"));
			repository.save(new Customer("Chloe", 0,28,"11223232","Brian@126.com","湖南"));
			repository.save(new Customer("Kim", 1,30,"15810911761","Bauer@163.com","山东"));
			repository.save(new Customer("David", 1,22,"13693669002","Palmer","河北"));
			repository.save(new Customer("Michelle", 1,23,"170111121","Dessler","北京"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Customer customer = repository.findById(1L).get();
			log.info("Customer found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastNameStartsWithIgnoreCase('Chloe'):");
			log.info("--------------------------------------------");
			for (Customer bauer : repository
					.findByNameStartingWithIgnoreCase("Chloe")) {
				log.info(bauer.toString());
			}
			log.info("");
		};
	}
}
