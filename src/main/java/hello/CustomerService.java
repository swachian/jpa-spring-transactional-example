package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Transactional
	public void insert() throws Exception {
		System.out.println("----------------transaction-------------");
		customerRepository.save(new Customer("Jack", "Bauer"));
		customerRepository.save(new Customer("Chloe", "O'Brian"));

		customerRepository.save(new Customer("Kim", "Bauer"));
		customerRepository.save(new Customer("David", "Palmer"));
		customerRepository.save(new Customer("Michelle", "Dessler"));
        if (3>2) {
        	throw new UnsupportedOperationException();
        }
       
	}
}
