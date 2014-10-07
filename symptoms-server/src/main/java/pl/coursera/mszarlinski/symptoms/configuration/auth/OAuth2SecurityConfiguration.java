package pl.coursera.mszarlinski.symptoms.configuration.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;

import pl.coursera.mszarlinski.symptoms.repository.IUserRepository;

/**
 * Configure this web application to use OAuth 2.0.
 *
 * The resource server is located at "/video", and can be accessed only by
 * retrieving a token from "/oauth/token" using the Password Grant Flow as
 * specified by OAuth 2.0.
 * 
 * Most of this code can be reused in other applications. The key methods that
 * would definitely need to be changed are:
 * 
 * ResourceServer.configure(...) - update this method to apply the appropriate
 * set of scope requirements on client requests
 * 
 * OAuth2Config constructor - update this constructor to create a "real" (not
 * hard-coded) UserDetailsService and ClientDetailsService for authentication.
 * The current implementation should never be used in any type of production
 * environment as these hard-coded credentials are highly insecure.
 * 
 * OAuth2SecurityConfiguration.containerCustomizer(...) - update this method to
 * use a real keystore and certificate signed by a CA. This current version is
 * highly insecure.
 * 
 */
@Configuration
public class OAuth2SecurityConfiguration {

	@Configuration
	@EnableWebSecurity
	protected static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

		// @Autowired
		// private UserDetailsService userDetailsService;

		@Autowired
		private IUserRepository userRepository;

		@Autowired
		protected void registerAuthentication(final AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userRepository);
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	}

	/**
	 * This method is used to configure who is allowed to access which parts of
	 * our resource server (i.e. the "/video" endpoint)
	 */
	@Configuration
	@EnableResourceServer
	protected static class ResourceServer extends ResourceServerConfigurerAdapter {

		// This method configures the OAuth scopes required by clients to access
		// all of the paths in the video service.
		@Override
		public void configure(HttpSecurity http) throws Exception {

			http.csrf().disable();

			http.authorizeRequests().antMatchers("/oauth/token").anonymous();

			// TODO: Why we cannot just use @PreAuthorize() ?

			// Require all GET requests to have client "read" scope
			http.authorizeRequests().antMatchers(HttpMethod.GET, "/rest/**").access("#oauth2.hasScope('read')");
			// Require all other requests to have "write" scope
//			http.authorizeRequests().antMatchers("/rest/**").access("#oauth2.hasScope('write')");
		}

	}

	/**
	 * This class is used to configure how our authorization server (the
	 * "/oauth/token" endpoint) validates client credentials.
	 */
	@Configuration
	@EnableAuthorizationServer
	@Order(Ordered.LOWEST_PRECEDENCE - 100)
	protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

		// Delegate the processing of Authentication requests to the framework
		@Autowired
		private AuthenticationManager authenticationManager;

		private ClientDetailsService csvc;

		public OAuth2Config() throws Exception {
			// Create a service that has the credentials for all our clients
			csvc = new InMemoryClientDetailsServiceBuilder()
					// Create a client that has "read" and "write" access to the
					// video service
					.withClient("patientClient").authorizedGrantTypes("password")
					.authorities("ROLE_PATIENT")
					.scopes("read", "write")
					// .resourceIds("video")
					.and()
					// Create a second client that only has "read" access to the
					// video service
					.withClient("doctorClient").authorizedGrantTypes("password")
					.authorities("ROLE_DOCTOR")
					.scopes("read", "write")
					// .resourceIds("video")
					.accessTokenValiditySeconds(3600).and().build();
		}

		/**
		 * Return the list of trusted client information to anyone who asks for
		 * it.
		 */
		@Bean
		public ClientDetailsService clientDetailsService() throws Exception {
			return csvc;
		}

		/**
		 * This method tells our AuthorizationServerConfigurerAdapter to use the
		 * delegated AuthenticationManager to process authentication requests.
		 */
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.authenticationManager(authenticationManager);
		}

		/**
		 * This method tells the AuthorizationServerConfigurerAdapter to use our
		 * self-defined client details service to authenticate clients with.
		 */
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.withClientDetails(clientDetailsService());
		}

	}
}
