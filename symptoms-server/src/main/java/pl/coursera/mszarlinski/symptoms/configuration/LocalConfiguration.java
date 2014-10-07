package pl.coursera.mszarlinski.symptoms.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import pl.coursera.mszarlinski.symptoms.domain.Patient;

/**
 * 
 * @author Maciej
 *
 */
@Configuration
@Profile("local")
public class LocalConfiguration {
	@Bean
	public DataSource dataSource(Environment environment) {
		return new EmbeddedDatabaseBuilder().setName("symptoms-db")
				.setType(EmbeddedDatabaseType.H2).build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			DataSource dataSource) {

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setPackagesToScan(Patient.class.getPackage().getName());
		factory.setPersistenceProvider(new HibernatePersistence());
		Map<String, String> p = new HashMap<String, String>();
		p.put(org.hibernate.cfg.Environment.DIALECT, H2Dialect.class.getName());
		p.put(org.hibernate.cfg.Environment.SHOW_SQL, "true");
		p.put(org.hibernate.cfg.Environment.FORMAT_SQL, "true");

		// p.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "create-drop");
		factory.setJpaPropertyMap(p);

		return factory;
	}

	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager scm = new SimpleCacheManager();
		Cache cache = new ConcurrentMapCache("patients");
		scm.setCaches(Arrays.asList(cache));
		return scm;
	}

}
