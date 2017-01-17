/*
 * 
 */
package com.medsys.master.model;


/**
 * The Constant Class for ConfigParaKey.
 */
public class ConfigParaKey{

	/** The user webservice. */
	public static String USER_WEBSERVICE = "user_webservice";

	/** The user pan ekyc webservice. */
	public static String USER_PAN_EKYC_WEBSERVICE = "user_pan_ekyc_webservice";

	/** The user aadhaar ekyc request otp service url. */
	public static String USER_AADHAAR_EKYC_REQUEST_OTP_SERVICE_URL = "user_aadhaar_ekyc_request_otp_service_url";

	/** The user aadhaar ekyc verify otp service url. */
	public static String USER_AADHAAR_EKYC_VERIFY_OTP_SERVICE_URL = "user_aadhaar_ekyc_verify_otp_service_url";

	/** The smtp user portal url. */
	public static String USER_PORTAL_URL = "user_portal_url";

	/** The smtp verification url. */
	public static String SMTP_VERIFICATION_URL = "smtp_verficationURL";

	/** The smtp user activation url. */
	public static String SMTP_USER_ACTIVATION_URL = "smtp_user_activationURL";

	public static String SMTP_USER_ACTIVATE_SUSPENDED_ACCOUNT_URL = "smtp_user_activate_suspended_accountURL";

	/** The smtp user id. */
	public static String SMTP_USER_ID = "smtp_userId";

	/** The smtp password. */
	public static String SMTP_PASSWORD = "smtp_password";

	/** The smtp host. */
	public static String SMTP_HOST = "smtp_smtpHost";

	/** The smtp port. */
	public static String SMTP_PORT = "smtp_smtpPort";

	/** The smtp forgot password URL. */
	public static String SMTP_FORGOTPASSWORD_URL = "smtp_forgotpasswordURL";

	/** The sms username. */
	public static String SMS_USERNAME = "sms_username";

	/** The sms password. */
	public static String SMS_PASSWORD = "sms_pwd";

	/** The sms sender id. */
	public static String SMS_SENDER_ID = "sms_senderid";

	/** The sms url. */
	public static String SMS_URL = "sms_url";

	/** The email verification duration. */
	public static String EMAIL_VERIFICATION_DURATION = "email_verification_duration";
	
	public static String PASSWORD_VERIFICATION_DURATION = "adminuser_pswd_verification_duration";

	/** The otp length. */
	public static String OTP_LENGTH = "otp_length";

	/** The otp validity. */
	public static String OTP_VALIDITY = "otp_validity";

	/** The sms verification duration. */
	public static String SMS_VERIFICATION_DURATION = "sms_verification_duration";

	/** The captcha active status. */
	public static String CAPTCHA_ACTIVE_STATUS = "captcha_active_status";

	/** The captcha private key. */
	public static String CAPTCHA_PRIVATE_KEY = "captcha_private_key";

	/** The captcha public key. */
	public static String CAPTCHA_PUBLIC_KEY = "captcha_public_key";

	/** The captcha private key. */
	public static String CIPHER_PRIVATE_KEY = "email_decrypt_private_key";

	/** The captcha public key. */
	public static String CIPHER_PUBLIC_KEY = "email_encrypt_public_key";

	/** The aadhaar encode regex. */
	public static String AADHAAR_ENCODE_REGEX = "aadhaar_encode_regex";

	/** The aadhaar encode string. */
	public static String AADHAAR_ENCODE_STRING = "aadhaar_encode_string";

	/** The email encode regex. */
	public static String EMAIL_ENCODE_REGEX = "email_encode_regex";

	/** The email encode string. */
	public static String EMAIL_ENCODE_STRING = "email_encode_string";

	/** The email code resend count. */
	public static String EMAIL_CODE_RESEND_COUNT = "email_code_resend_count";

	/** The mobile code resend count. */
	public static String MOBILE_CODE_RESEND_COUNT = "mobile_code_resend_count";

	/** The dsc valid response. */
	public static String DSC_VALID_RESPONSE = "DSC_Valid_Response";

	/** The dsc max filesize. */
	public static String DSC_MAX_FILESIZE = "DSC_max_filesize";

	/** The dsc validity. */
	public static String DSC_VALIDITY = "DSC_validity";

	/** The msspgateway url. */
	public static String MSSPGATEWAY_URL = "MsspGateway_url";

	/** The mobile encode regex. */
	public static String MOBILE_ENCODE_REGEX = "mobile_encode_regex";

	/** The mobile encode string. */
	public static String MOBILE_ENCODE_STRING = "mobile_encode_string";

	/** The max login before captcha. */
	public static String MAX_LOGIN_BEFORE_CAPTCHA = "max_login_attempt_before_captcha";

	/** The max login before lock. */
	public static String MAX_LOGIN_BEFORE_LOCK = "max_login_attempt_before_lock";

	/** The max login attempt before sp acc will be locked. */
	public static String MAX_SP_LOGIN_BEFORE_LOCK = "sp_max_login_attempt_before_lock";

	/** The max incorrect otp before lock. */
	public static String MAX_INCORRECT_OTP_BEFORE_LOCK = "max_incorrect_otp_before_lock";

	/** The smtp sp portal url. */
	public static String SMTP_SP_PORTAL_URL = "smtp_sp_portal_url";

	/** The smtp sp forgot password URL. */
	public static String SMTP_SP_FORGOTPASSWORD_URL = "smtp_sp_forgotpasswordURL";

	/** The max session time. */
	public static String MAX_SESSION_TIME = "max_session_time_duration";

	/** The smtp sp account activation URL. */
	public static String SMTP_SP_ACTIVATEACCOUNT_URL = "smtp_sp_activatepasswordURL";

	/** The smtp sp verification email code URL. */
	public static String SMTP_SP_VERIFICATIONEMAILCODE_URL = "smtp_sp_verificationemailcodeURL";

	/** The epramaan publickey. */
	public static String EPRAMAAN_PUBLICKEY = "epramaan_publickey";

	/** The epramaan privatekey. */
	public static String EPRAMAAN_PRIVATEKEY = "epramaan_privatekey";

	/** The epramaan admin emailid. */
	public static String EPRAMAAN_ADMIN_EMAILID = "smtp_epraman_admin_mailid";

	/** SAML SSO-SLO *. */
	public static String SAML_EPRAMAAN_ISSUER = "saml_epramaan_issuer";

	/** The saml sso response condition not or after time limit. */
	public static String SAML_SSO_RESPONSE_CONDITION_NOT_OR_AFTER_TIME_LIMIT = "saml_sso_response_condition_not_or_after_time_limit";

	/** The saml sso token success. */
	public static String SAML_SSO_TOKEN_SUCCESS = "saml_sso_token_success";

	/** The smtp user delete account URL. */
	public static String SMTP_USER_DELETEACCOUNT_URL = "smtp_user_delete_accountURL";
	/** The delete confirmation duration. */
	public static String EMAIL_DELETE_CONFIRMATION_DURATION = "email_delete_confirmation_duration";
	
	/** The MoU alert duration in months. */
	public static String SP_MOU_ALERT_DURATION_IN_MONTHS = "sp_mou_alert_duration_in_months";
	
	/** The MoU post expiry duration before suspension in months. */
	public static String SP_MOU_POST_EXPIRY_DURATION_BEFORE_SUSPENSION_IN_MONTHS = "sp_mou_post_expiry_duration_before_suspension_in_months";
	
	public static String MOU_MAX_FILESIZE = "MOU_max_filesize";
	
	public static String MOU_UPLOAD_PATH = "department_mous_path";
	
	public static String ADMIN_PORTAL_URL = "admin_portal_url";
	
	public static String ADMINUSER_DEFAULT_PASSWORD = "adminuser_default_password";
	
	public static String ADMINUSER_EMAIL_VERIFICATION_URL = "adminuser_email_verification_url";
	
	public static String ADMINUSER_PASSWORD_SET_URL = "adminuser_password_set_url";
	
	public static String ADMINUSER_EMAIL_REGEX = "adminuser_email_regex";
	
	public static String IMAGE_MAX_FILESIZE = "image_max_filesize";
	
	

}
