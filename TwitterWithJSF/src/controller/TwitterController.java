
package controller;

import java.io.IOException;

import javax.faces.bean.*;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import paypal.*;
import twitter4j.Twitter;
import twitter4j.TwitterException;
//import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterResponse;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@ManagedBean(name = "twitter")
@SessionScoped
public class TwitterController {

	private TwitterResult result = new TwitterResult();

	public TwitterResult getResult() {
		return result;
	}

	public void setResult(TwitterResult result) {
		this.result = result;
	}

	public String success() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		TwitterSucess ps = new TwitterSucess();
		this.result = ps.getPayPal(request);
		return "success";
	}
	
	public void login() throws IOException {
		String authURL = "noSoup";
		try {
			 Twitter twitter = new TwitterFactory().getInstance();
			 RequestToken requestToken;
			 //request.getSession().setAttribute("twitter", twitter);
			 twitter.setOAuthConsumer("n6OC67D2kBZ8R4uxqX2teXfcu", "mn0RVnpqFdOl2j4dwtmsZC4CkK7vfcdEURYgnKLRIfjLz4PX4d");
			 requestToken = twitter.getOAuthRequestToken("http://localhost:8080/TwitterWithJSF/verify.xhtml");
			 authURL = requestToken.getAuthenticationURL();

			 
			 //request.getSession().setAttribute("requestToken", requestToken);
			 //response.sendRedirect(authURL);
			 } catch (Exception  twitterException) {
			 twitterException.printStackTrace();
			 }
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	    externalContext.redirect(authURL);
		//return authURL;//"success";
	}
	
	public String verify() throws TwitterException {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("n6OC67D2kBZ8R4uxqX2teXfcu", "mn0RVnpqFdOl2j4dwtmsZC4CkK7vfcdEURYgnKLRIfjLz4PX4d");
		
		HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		String verifier[] = origRequest.getParameterValues("oauth_verifier");
		String request_token[] = origRequest.getParameterValues("oauth_token"); 
		
		AccessToken at = twitter.getOAuthAccessToken(request_token[0], verifier[0]);
		twitter.setOAuthAccessToken(at);
		
		Long userId = twitter.getId();
		User user = twitter.showUser(userId);
		
		return user.getName();
		
		//String verifyURL = at.getAuthorizationURL();
		
		//String verifier = FacesContext.getCurrentInstance().get
		
	}

}
