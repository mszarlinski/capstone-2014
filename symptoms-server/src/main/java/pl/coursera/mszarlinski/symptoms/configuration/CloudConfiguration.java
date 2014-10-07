package pl.coursera.mszarlinski.symptoms.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import pl.coursera.mszarlinski.symptoms.configuration.auth.OAuth2SecurityConfiguration;
import pl.coursera.mszarlinski.symptoms.domain.Patient;

/**
 * 
 * @author Maciej
 *
 */
@Configuration
@Profile("cloud")
@Import(OAuth2SecurityConfiguration.class)
public class CloudConfiguration extends AbstractCloudConfig {
	@Bean
	public DataSource dataSource() {
		return connectionFactory().dataSource();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setPackagesToScan(Patient.class.getPackage().getName());
		factory.setPersistenceProvider(new HibernatePersistence());
		Map<String, String> p = new HashMap<String, String>();
		p.put(org.hibernate.cfg.Environment.DIALECT, MySQL5Dialect.class.getName());
		factory.setJpaPropertyMap(p);

		return factory;
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return connectionFactory().redisConnectionFactory();
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() throws Exception {
		RedisTemplate<String, Object> ro = new RedisTemplate<String, Object>();
		ro.setConnectionFactory(redisConnectionFactory());
		return ro;
	}

	@Bean
	public CacheManager cacheManager() throws Exception {
		return new RedisCacheManager(redisTemplate());
	}

}
