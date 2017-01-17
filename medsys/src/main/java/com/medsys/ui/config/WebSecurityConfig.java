package com.medsys.ui.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.medsys.ui.util.UIActions;

/**
 * The Class WebSecurityConfig.
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = com.medsys.adminuser.bd.AdminUserBDImpl.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Configure global.
	 * 
	 * @param userDetailsService
	 *            the user details service
	 * @param auth
	 *            the auth
	 * @throws Exception
	 *             the exception
	 */
	@Autowired
	public void configureGlobal(UserDetailsService userDetailsService,
			AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter
	 * #configure(org.springframework.security.config
	 * .annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				/* .accessDecisionManager(defaultAccessDecisionManager()) */
				.antMatchers("/resources/**", "/signup")
				.permitAll()
				.antMatchers(
						UIActions.FORWARD_SLASH + UIActions.FORGOT_PASSWORD
								+ "/**")
				.permitAll()
				.antMatchers(UIActions.FORWARD_SLASH + UIActions.ERROR + "/**")
				.permitAll()
				.and()
				.formLogin()
				.loginPage(UIActions.FORWARD_SLASH + UIActions.LOGIN)
				.failureUrl(UIActions.FORWARD_SLASH + UIActions.LOGIN_FAILURE_URL)
				.permitAll()
				.and()
				.logout()
				.logoutUrl(UIActions.FORWARD_SLASH + UIActions.LOG_OUT)
				.deleteCookies("JSESSIONID")
				.permitAll()
				.and()
				.exceptionHandling()
				.accessDeniedPage(
						UIActions.FORWARD_SLASH + UIActions.ERROR
								+ UIActions.FORWARD_SLASH
								+ UIActions.LOAD_HTTP_403_ERROR).and()
				.sessionManagement().maximumSessions(1).expiredUrl(UIActions.FORWARD_SLASH + UIActions.EXPIRED_URL)
				.and()
				.and()
				.csrf().disable();
	}

	/*
	 * @Bean public AccessDecisionManager defaultAccessDecisionManager() {
	 * 
	 * @SuppressWarnings("rawtypes") List<AccessDecisionVoter> decisionVoters =
	 * new ArrayList<AccessDecisionVoter>(); RoleVoter roleVoter = roleVoter();
	 * decisionVoters.add(roleVoter); AuthenticatedVoter authenticatedVoter =
	 * new AuthenticatedVoter(); decisionVoters.add(authenticatedVoter); return
	 * defaultAccessDecisionManager();
	 * 
	 * }
	 *//**
	 * Role voter.
	 * 
	 * @return the role voter
	 */
	/*
	 * @Bean public RoleVoter roleVoter() { RoleVoter roleVoter = new
	 * RoleVoter(); roleVoter.setRolePrefix(""); return roleVoter; }
	 */

}