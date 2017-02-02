/*
 * 
 */
package com.medsys.ui.controller;

import java.util.Collection;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medsys.adminuser.model.AdminUser;
import com.medsys.adminuser.model.Roles;
import com.medsys.ui.model.LoginForm;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.util.EpMessage;
import com.medsys.util.EpSystemError;
import com.octo.captcha.service.image.ImageCaptchaService;
/*import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;*/

/**
 * The Class WelcomeController.
 */
@Controller
public class WelcomeController extends SuperController {

	
	@Autowired
	private ImageCaptchaService capthcaService;

	private byte[] captchaChallengeAsJpeg = null;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(WelcomeController.class);

	/**
	 * Load home page.
	 * 
	 * @param loginForm
	 *            the user form
	 * @param serviceId
	 *            the service id
	 * @param bindingResult
	 *            the binding result
	 * @param reAttributes
	 *            the re attributes
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = { UIActions.FORWARD_SLASH + UIActions.HOME,
			UIActions.FORWARD_SLASH }, method = RequestMethod.GET)
	public String loadHomePage(@ModelAttribute LoginForm loginForm,
			BindingResult bindingResult, RedirectAttributes reAttributes,
			Model model) {

		Collection<GrantedAuthority> authorities;
		try {
			authorities = getAuthorities();

			String rolename;

			for (GrantedAuthority authority : authorities) {
				rolename = authority.getAuthority();

				if (rolename.equals(Roles.MASTER_ADMIN)) {
					logger.debug("Directing to home page for: [" + rolename
							+ "]");
					return UIActions.FORWARD
							+ UIActions.LOAD_ADMIN_DASHBOARD;
				}
				
			}

			logger.error("Role not found - directing to default home page");
			return UIActions.FORWARD + UIActions.LOAD_DEFAULT_DASHBOARD;

		} catch (AuthenticationCredentialsNotFoundException e) {
			logger.error("No User Found");
			return UIActions.FORWARD + UIActions.LOGIN;
		}
		
	}

	private Collection<GrantedAuthority> getAuthorities()
			throws AuthenticationCredentialsNotFoundException {
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof AdminUser) {
			authorities = ((AdminUser) principal).getAuthorities();
		} else {
			logger.error("Principal is not an instance of AdminUser");
			throw new AuthenticationCredentialsNotFoundException(
					"Principal is not an instance of AdminUser");
		}
		return authorities;
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOGIN, method = RequestMethod.GET)
	public String loadLogin(HttpServletRequest request, Model model) {
		logger.debug("forwarding to the login page.");
		return MedsysUITiles.LOGIN.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOGIN, method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {

		logger.debug("Username:" + request.getParameter("username"));
		logger.debug("Password:" + request.getParameter("password"));

		return UIActions.FORWARD + UIActions.LOAD_DEFAULT_DASHBOARD;
	}

	

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOG_OUT, method = {
			RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpServletRequest request, Model model) {

		return UIActions.REDIRECT + UIActions.LOGIN +"?logout=" + EpMessage.MSG_FOR_SUCCESSFUL_LOG_OUT.getMsgCode();
	}
	
	
	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.EXPIRED_URL, method = {
			RequestMethod.GET, RequestMethod.POST })
	public String expired(HttpServletRequest request, Model model) {

		return UIActions.REDIRECT + UIActions.LOGIN +"?error=" + EpSystemError.USER_SESSION_EXPIRED.getErrorCode();
	}
	
	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOGIN_FAILURE_URL, method = {
			RequestMethod.GET, RequestMethod.POST })
	public String loginFailure(HttpServletRequest request, Model model) {

		return UIActions.REDIRECT + UIActions.LOGIN +"?error=" + EpSystemError.INVALID_USER.getErrorCode();
	}
	
	
	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.CAPTCHA)
	public ResponseEntity<byte[]> captchaImage(HttpServletRequest request)
			throws Exception {
		logger.info("----------------Inside Captcha function--------------");
	//	handleRequest(request);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<byte[]>(captchaChallengeAsJpeg, headers,
				HttpStatus.CREATED);
	}

	
	
}
