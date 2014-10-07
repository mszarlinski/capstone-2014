package pl.coursera.mszarlinski.symptoms.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import pl.coursera.mszarlinski.symptoms.configuration.auth.OAuth2SecurityConfiguration;
import pl.coursera.mszarlinski.symptoms.domain.Patient;

import com.mysql.jdbc.Driver;

/**
 * 
 * @author Maciej
 *
 */
@Configuration
@Profile("default")
@Import(OAuth2SecurityConfiguration.class) // TODO: Application.class level?
public class LocalConfiguration {
	@Bean
	public DataSource dataSource(Environment environment) {
		String user = environment.getProperty("ds.user"), 
				pw = environment.getProperty("ds.password"), 
				url = environment.getProperty("ds.url");
		
		Class<Driver> driverClass = environment.getPropertyAsClass("ds.driverClass", Driver.class);

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(driverClass.getName());
		basicDataSource.setPassword(pw);
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(user);
		basicDataSource.setInitialSize(5);
		basicDataSource.setMaxActive(10);
		return basicDataSource;
	}

//	@Bean
//	public DataSource dataSource(Environment environment) {
//		return new EmbeddedDatabaseBuilder().setName("symptoms-db")
//				.setType(EmbeddedDatabaseType.H2).build();
//	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			DataSource dataSource) {

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setPackagesToScan(Patient.class.getPackage().getName());
		factory.setPersistenceProvider(new HibernatePersistence());
		Map<String, String> p = new HashMap<String, String>();
		p.put(org.hibernate.cfg.Environment.DIALECT, MySQL5Dialect.class.getName());
		p.put(org.hibernate.cfg.Environment.SHOW_SQL, "true");
		p.put(org.hibernate.cfg.Environment.FORMAT_SQL, "true");
		
		p.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "create-drop");
		p.put(org.hibernate.cfg.Environment.HBM2DDL_IMPORT_FILES, "init_data.sql");
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
