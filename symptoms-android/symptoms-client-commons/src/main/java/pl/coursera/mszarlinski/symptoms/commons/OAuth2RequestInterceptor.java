package pl.coursera.mszarlinski.symptoms.commons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import pl.coursera.mszarlinski.symptoms.commons.utils.IOUtils;
import android.util.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Maciej
 *
 */
class OAuth2RequestInterceptor implements ClientHttpRequestInterceptor {

	private static final String ACCESS_TOKEN_JSON_PROPERTY = "access_token";
	
	private String accessToken;

	final private HttpClient client;
	final private String tokenIssuingEndpoint;
	final private String username;
	final private String password;
	final private String clientId;
	final private String clientSecret;

	public OAuth2RequestInterceptor(HttpClient client, String tokenIssuingEndpoint, String username, String password,
			String clientId, String clientSecret) {
		this.client = client;
		this.tokenIssuingEndpoint = tokenIssuingEndpoint;
		this.username = username;
		this.password = password;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	/**
	 * Every time a method on the client interface is invoked, this method is
	 * going to get called. The method checks if the client has previously
	 * obtained an OAuth 2.0 bearer token. If not, the method obtains the bearer
	 * token by sending a password grant request to the server.
	 * 
	 * Once this method has obtained a bearer token, all future invocations will
	 * automatically insert the bearer token as the "Authorization" header in
	 * outgoing HTTP requests.
	 * 
	 */
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		// If we're not logged in, login and store the authentication token.
		if (accessToken == null) {
			try {
				// This code below programmatically builds an OAuth 2.0 password
				// grant request and sends it to the server.

				// Encode the username and password into the body of the
				// request.
				HttpPost httpPost = new HttpPost(tokenIssuingEndpoint);

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
				nameValuePairs.add(new BasicNameValuePair("username", username));
				nameValuePairs.add(new BasicNameValuePair("password", password));

				// Add the client ID and client secret to the body of the
				// request.
				nameValuePairs.add(new BasicNameValuePair("client_id", clientId));
				nameValuePairs.add(new BasicNameValuePair("client_secret", clientSecret));
				// Indicate that we're using the OAuth Password Grant Flow
				// by adding grant_type=password to the body
				nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// The password grant requires BASIC authentication of the
				// client.
				// In order to do BASIC authentication, we need to concatenate
				// the
				// client_id and client_secret values together with a colon and
				// then
				// Base64 encode them. The final value is added to the request
				// as
				// the "Authorization" header and the value is set to "Basic "
				// concatenated with the Base64 client_id:client_secret value
				// described
				// above.

				// Add the basic authorization header
				final String base64Auth = Base64.encodeToString((clientId + ":" + clientSecret).getBytes(),
						Base64.NO_WRAP);

				httpPost.setHeader("Authorization", "Basic " + base64Auth);
				// Request the password grant.
				HttpResponse resp = client.execute(httpPost);

				// Make sure the server responded with 200 OK
				if (resp.getStatusLine().getStatusCode() < 200 || resp.getStatusLine().getStatusCode() > 299) {
					// If not, we probably have bad credentials
					throw new SecuredRestException("Login failure: " + resp.getStatusLine().getStatusCode() + " - "
							+ resp.getStatusLine().getReasonPhrase());
				} else {
					// Extract the string body from the response
					final String responseBody = IOUtils.toString(resp.getEntity().getContent());

					// Extract the access_token (bearer token) from the response
					// so that we
					// can add it to future requests.
					ObjectMapper objectMapper = new ObjectMapper();
					JsonNode json = objectMapper.readTree(responseBody);
					accessToken = json.get(ACCESS_TOKEN_JSON_PROPERTY).textValue();
				}
			} catch (Exception e) {
				throw new SecuredRestException(e);
			}
		}
		// Add the access_token to this request as the
		// "Authorization"
		// header.
		request.getHeaders().add("Authorization", "Bearer " + accessToken);
		return execution.execute(request, body);
	}
}
