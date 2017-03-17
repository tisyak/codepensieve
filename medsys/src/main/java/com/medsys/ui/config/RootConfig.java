package com.medsys.ui.config;

import java.awt.Color;
import java.awt.Font;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.SimpleTextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.DictionaryWordGenerator;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;

/**
 * The Class RootConfig.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.medsys" })
@PropertySource("classpath:database.properties")
public class RootConfig implements TransactionManagementConfigurer{

	/*
	 * @Autowired
	 * 
	 * @Qualifier("dataSource") private DataSource dataSource;
	 * 
	 * public void setDataSource(DataSource dataSource) { this.dataSource =
	 * dataSource; }
	 */

	/** The Constant PROPERTY_NAME_HIBERNATE_DIALECT. */
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	
	/** The Constant PROPERTY_NAME_HIBERNATE_SHOW_SQL. */
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	
	/** The Constant PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN. */
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
	// static final String PROPERTY_NAME_HIBERNATE_CONNECTION_DATASOURCE=
	// "hibernate.dialecthibernate.connection.datasource";
	
	
	private static final String PROPERTY_NAME_DATASOURCE_JDBC = "ds.jdbc";
	private static final String PROPERTY_NAME_DATASOURCE_SERVER = "ds.server";
	private static final String PROPERTY_NAME_DATASOURCE_PORT = "ds.port";
	private static final String PROPERTY_NAME_DATASOURCE_DB_NAME = "ds.dbname";
	private static final String PROPERTY_NAME_DATASOURCE_DB_USERNAME = "ds.db.username";
	private static final String PROPERTY_NAME_DATASOURCE_DB_PASSWORD = "ds.db.password";



	/** The env. */
	@Resource
	private Environment env;



	/**
	 * Hib properties.
	 *
	 * @return the properties
	 */
	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT,
				env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL,
				env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		// properties.put(PROPERTY_NAME_HIBERNATE_CONNECTION_DATASOURCE,
		// env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_CONNECTION_DATASOURCE));
		return properties;
	}

	/**
	 * Transaction manager.
	 *
	 * @return the hibernate transaction manager
	 */
	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		 return transactionManager();
	}
	/**
	 * Session factory.
	 *
	 * @return the local session factory bean
	 */
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean
				.setPackagesToScan(env
						.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
		sessionFactoryBean.setHibernateProperties(hibProperties());
		return sessionFactoryBean;
	}

	/*
	 * @Bean public DataSource dataSource() { final JndiDataSourceLookup
	 * dsLookup = new JndiDataSourceLookup(); dsLookup.setResourceRef(true);
	 * DataSource dataSource = dsLookup.getDataSource("jdbc/medsysDB"); return
	 * dataSource; }
	 */

	/**
	 * Data source.
	 *
	 * @return the data source
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		String jdbcDatasource = env.getRequiredProperty(PROPERTY_NAME_DATASOURCE_JDBC);
		String dbServer = env.getRequiredProperty(PROPERTY_NAME_DATASOURCE_SERVER);
		String dbPort = env.getRequiredProperty(PROPERTY_NAME_DATASOURCE_PORT);
		String dbName = env.getRequiredProperty(PROPERTY_NAME_DATASOURCE_DB_NAME);
		dataSource.setUrl(jdbcDatasource+"://"+dbServer+":"+dbPort+"/"+dbName);
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATASOURCE_DB_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATASOURCE_DB_PASSWORD));
		return dataSource;
	}

	/**
	 * Task executor.
	 *
	 * @return the task executor
	 */
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(100);
		taskExecutor.setMaxPoolSize(500);
		taskExecutor.setQueueCapacity(10000);
		return taskExecutor;
	}

	
	
	
	/**
	 * ******************************** JCAPTCHA SERVICE ******************************************.
	 *
	 * @return the generic manageable captcha service
	 */
	
	
	
	@Bean
	@Scope("singleton")
	public GenericManageableCaptchaService captchaService() {
		
		return new GenericManageableCaptchaService(imageEngine(),180,180000);
		
	}
	
	/**
	 * Image engine.
	 *
	 * @return the generic captcha engine
	 */
	@Bean
	public GenericCaptchaEngine imageEngine() {
		
		return new GenericCaptchaEngine(new CaptchaFactory[]{captchaFactory()});
		
	}
	
	
	/**
	 * Captcha factory.
	 *
	 * @return the gimpy factory
	 */
	@Bean
	public GimpyFactory captchaFactory() {
		
		return new GimpyFactory(wordGenerator(),wordToImage());
		
	}
	
	/**
	 * Word generator.
	 *
	 * @return the dictionary word generator
	 */
	@Bean
	public DictionaryWordGenerator wordGenerator() {
		
		return new DictionaryWordGenerator(fileDictionary());
		
	}
	
	
	/**
	 * File dictionary.
	 *
	 * @return the file dictionary
	 */
	@Bean
	public FileDictionary fileDictionary() {
		
		return  new FileDictionary("toddlist");
		
	}
	
	/**
	 * Word to image.
	 *
	 * @return the composed word to image
	 */
	@Bean
	public ComposedWordToImage wordToImage() {
		
		return new ComposedWordToImage(fontGenRandom(),backGenUni(),simpleWhitePaster());
		
	}
	
	/**
	 * Font gen random.
	 *
	 * @return the random font generator
	 */
	@Bean
	public RandomFontGenerator fontGenRandom() {
		
		return  new RandomFontGenerator(40,50,new Font[]{fontVerdana(),fontArial()});
		
	}
	
	
	
	/**
	 * Back gen uni.
	 *
	 * @return the uni color background generator
	 */
	@Bean
	public UniColorBackgroundGenerator backGenUni() {
		
		return  new UniColorBackgroundGenerator(220,80,colorWhite());
	}
	
	/**
	 * Simple white paster.
	 *
	 * @return the simple text paster
	 */
	@Bean
	public SimpleTextPaster simpleWhitePaster() {
		
		return  new SimpleTextPaster(5,7,textColorGenerator(),true);
	}
	
	
	/**
	 * Text color generator.
	 *
	 * @return the random list color generator
	 */
	@Bean
	public RandomListColorGenerator textColorGenerator() {
		
		return  new RandomListColorGenerator(new Color[]{colorGray()});
		
	}
	

	
	/**
	 * Font verdana.
	 *
	 * @return the font
	 */
	@Bean
	public Font fontVerdana() {
		
		return  new Font("Verdana",0,10);
		
	}
	
	/**
	 * Font arial.
	 *
	 * @return the font
	 */
	@Bean
	public Font fontArial() {
		
		return new Font("Arial",0,10);
		
	}
	
	/**
	 * Color gray.
	 *
	 * @return the color
	 */
	@Bean
	public Color colorGray() {
		
		return new Color(132,132,132);
		
		
	}
	
	/**
	 * Color white.
	 *
	 * @return the color
	 */
	@Bean
	public Color colorWhite() {
		
		return new Color(242,242,242);
		
	}


	/********************************** END OF JCAPTCHA SERVICE *******************************************/
	


}