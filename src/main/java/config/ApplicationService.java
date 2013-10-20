package config;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;
import hello.Customer;
import hello.CustomerRepository;
import hello.CustomerService;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableJpaRepositories("hello")
@EnableTransactionManagement(proxyTargetClass = false)
@ComponentScan("hello")
public class ApplicationService {

	@Bean
	public DataSource dataSourceMy() {
		//http://fruzenshtein.com/spring-jpa-data-hibernate-mysql/
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://192.168.137.102:3306/hibnatedb");
		dataSource.setUsername("test");
		dataSource.setPassword("test123");

		return dataSource;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(true);
		return jpaVendorAdapter;
	}


	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
		lemfb.setDataSource(dataSourceMy());
		lemfb.setJpaVendorAdapter(jpaVendorAdapter());
		//lemfb.setPackagesToScan("repositories");
		lemfb.setPackagesToScan("hello");
		return lemfb;
	}
	public static void main(String[] args) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(
				ApplicationService.class);
		CustomerRepository repository = context
				.getBean(CustomerRepository.class);
		CustomerService service = context.getBean(CustomerService.class);

		// save a couple of customers
		try {
			service.insert();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// fetch all customers
		Iterable<Customer> customers = repository.findAll();
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : customers) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer by ID
		Customer customer = repository.findOne(1L);
		System.out.println("Customer found with findOne(1L):");
		System.out.println("--------------------------------");
		System.out.println(customer);
		System.out.println();

		// fetch customers by last name
		List<Customer> bauers = repository.findByLastName("Bauer");
		System.out.println("Customer found with findByLastName('Bauer'):");
		System.out.println("--------------------------------------------");
		for (Customer bauer : bauers) {
			System.out.println(bauer);
		}

		context.close();
	}

}
