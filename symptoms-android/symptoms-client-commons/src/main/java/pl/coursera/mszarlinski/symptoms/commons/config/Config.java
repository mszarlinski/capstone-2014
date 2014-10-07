package pl.coursera.mszarlinski.symptoms.commons.config;

/**
 * 
 * @author Maciej
 *
 */
public interface Config {
	String SERVER_LOCAL = "https://10.0.2.2:8443";
	int SERVER_LOCAL_PORT = 8443;
	String SERVER_CLOUD = "";
	//FIXME:
	String OAUTH_TOKEN_URL = Config.SERVER_LOCAL + "/symptoms-server/oauth/token";
}
