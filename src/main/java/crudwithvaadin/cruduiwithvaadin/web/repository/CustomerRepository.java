package crudwithvaadin.cruduiwithvaadin.web.repository;

import crudwithvaadin.cruduiwithvaadin.web.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author tangyajun
 * @Description TO DO
 * @create 2020-04-14-9:44
 **/
public interface CustomerRepository extends JpaRepository<Customer,Long> {
	/**
	 * 根据姓名查询
	 * @param name
	 * @return
	 */
	List<Customer> findByNameStartingWithIgnoreCase(String name);
}
