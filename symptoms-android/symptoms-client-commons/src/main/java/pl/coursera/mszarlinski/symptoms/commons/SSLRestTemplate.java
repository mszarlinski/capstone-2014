package pl.coursera.mszarlinski.symptoms.commons;

import org.apache.http.client.HttpClient;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author Maciej
 *
 */
public class SSLRestTemplate extends RestTemplate {
	public SSLRestTemplate(HttpClient httpClient) {
		setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
	}
}
