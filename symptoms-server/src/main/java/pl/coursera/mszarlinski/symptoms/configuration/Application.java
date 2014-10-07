package pl.coursera.mszarlinski.symptoms.configuration;

import javax.persistence.EntityManagerFactory;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import pl.coursera.mszarlinski.symptoms.repository.PatientRepository;

/**
 * 
 * @author Maciej
 *
 */
@Configuration
@Import({ LocalConfiguration.class, TestConfiguration.class })
// , CloudConfiguration.class })
@EnableJpaRepositories(basePackageClasses = PatientRepository.class)
@EnableTransactionManagement
@ComponentScan(basePackages = { "pl.coursera.mszarlinski.symptoms.service",
		"pl.coursera.mszarlinski.symptoms.repository" })
@EnableCaching
@PropertySource("classpath:/application.properties")
// feeds Environment object
public class Application {

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
