package pl.coursera.mszarlinski.symptoms.commons;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

/**
 * This is an example of an HTTP client that does not properly validate SSL
 * certificates that are used for HTTPS. You should NEVER use a client like this
 * in a production application. Self-signed certificates are usually only OK for
 * testing purposes, such as this use case.
 * 
 * @author jules
 * 
 *         http://stackoverflow.com/questions/2703161/how-to-ignore-ssl-
 *         certificate-errors-in-apache-httpclient-4-0
 *         http://stackoverflow.com/questions
 *         /7622004/android-making-https-request
 *
 */
public class UnsafeHttpsClient {

	public static HttpClient createUnsafeClient(final int port) {
		try {
			// java.lang.System.setProperty("sun.security.ssl.allowUnsafeRenegotiation",
			// "true");

			// First create a trust manager that won't care.
			X509TrustManager trustManager = new X509TrustManager() {

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				}

				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				}
			};
			// Now put the trust manager into an SSLContext.
			// Supported: SSL, SSLv2, SSLv3, TLS, TLSv1, TLSv1.1
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] { trustManager }, new SecureRandom());

			// Use the above SSLContext to create your socket factory
			SSLSocketFactory sf = new MySSLSocketFactory(sslContext);
			// Accept any hostname, so the self-signed certificates don't fail
			sf.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			// Register our new socket factory with the typical SSL port and the
			// correct protocol name.
			Scheme httpsScheme = new Scheme("https", sf, port);
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(httpsScheme);

			HttpParams params = new BasicHttpParams();
			ClientConnectionManager cm = new SingleClientConnManager(params, schemeRegistry);

			return new DefaultHttpClient(cm, params);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	static class MySSLSocketFactory extends SSLSocketFactory {
		private final SSLContext sslContext;

		public MySSLSocketFactory(SSLContext context) throws KeyManagementException, NoSuchAlgorithmException,
				KeyStoreException, UnrecoverableKeyException {
			super(null);
			sslContext = context;
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
				UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}
}
