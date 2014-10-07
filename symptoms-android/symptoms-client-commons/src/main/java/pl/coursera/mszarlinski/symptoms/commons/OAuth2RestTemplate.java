package pl.coursera.mszarlinski.symptoms.commons;

import java.util.Arrays;

import org.apache.http.client.HttpClient;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import pl.coursera.mszarlinski.symptoms.commons.config.Config;

/**
 * 
 * @author Maciej
 *
 */
public class OAuth2RestTemplate extends SSLRestTemplate {
	public OAuth2RestTemplate(String username, String password, String clientId, HttpClient httpClient) {
		super(httpClient);
		setInterceptors(Arrays.<ClientHttpRequestInterceptor> asList(new OAuth2RequestInterceptor(httpClient,
				Config.OAUTH_TOKEN_URL, username, password, clientId, "")));
	}
}
