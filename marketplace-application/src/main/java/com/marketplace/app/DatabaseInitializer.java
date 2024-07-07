package com.marketplace.app;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.app.common.AppProperties;
import com.marketplace.domain.Utils;
import com.marketplace.domain.general.SiteSetting;
import com.marketplace.domain.general.dao.SiteSettingDao;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class DatabaseInitializer {
	
	private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);
	
	@Autowired
	private SiteSettingDao siteSettingDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AppProperties props;
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@EventListener(ApplicationReadyEvent.class)
	public void onReady() {
		var result = initialize();
		if (!result) {
			SpringApplication.exit(context, () -> 100);
		}
	}
	
	
	@Transactional
	public boolean initialize() {
		try {
			var su = props.getSuperUser();
			var errors = new ArrayList<String>();
			if (!Utils.isPhoneNumber(su.getPhone())) {
				errors.add("Required super-user's phone");
			}
			
			if (!Utils.hasText(su.getName())) {
				errors.add("Required super-user's name");
			}
			
			if (!Utils.hasText(su.getPassword())) {
				errors.add("Required super-user's password");
			}
			
			if (errors.size() > 0) {
				throw new RuntimeException(errors.stream().collect(Collectors.joining("\n\s\s", "\s\s", "\n")));
			}
			
			if (!userDao.existsByPhone(su.getPhone())) {
				var user = new User();
				user.setName(su.getName());
				user.setPhone(su.getPhone());
				user.setPassword(passwordEncoder.encode(su.getPassword()));
				user.setRole(User.Role.OWNER);
				
				userDao.create(user);
			}
			
			if (!siteSettingDao.exists(SiteSetting.DEFAULT_ID)) {
				siteSettingDao.createEmpty(SiteSetting.DEFAULT_ID);
			}
			
			return true;
		} catch (Exception e) {
			log.error("\n\n*****INITIALIZATION ERROR***** \n\n{}\n\n", e.getMessage());
		}
		
		return false;
	}
	
}
