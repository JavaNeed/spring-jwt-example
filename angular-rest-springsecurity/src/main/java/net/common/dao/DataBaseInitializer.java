package net.common.dao;

import java.util.Date;

import net.common.dao.newsentry.NewsEntryDao;
import net.common.dao.user.UserDao;
import net.common.entity.NewsEntry;
import net.common.entity.User;

import org.springframework.security.crypto.password.PasswordEncoder;


public class DataBaseInitializer {

	private NewsEntryDao newsEntryDao;
	private UserDao userDao;
	private PasswordEncoder passwordEncoder;

	protected DataBaseInitializer() {
		/* Default constructor for reflection instantiation */
	}


	public DataBaseInitializer(UserDao userDao, NewsEntryDao newsEntryDao, PasswordEncoder passwordEncoder){
		this.userDao = userDao;
		this.newsEntryDao = newsEntryDao;
		this.passwordEncoder = passwordEncoder;
	}


	public void initDataBase(){
		User userUser = new User("user", this.passwordEncoder.encode("user"));
		userUser.addRole("user");
		this.userDao.save(userUser);

		User adminUser = new User("admin", this.passwordEncoder.encode("admin"));
		adminUser.addRole("user");
		adminUser.addRole("admin");
		this.userDao.save(adminUser);

		long timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
		for (int i = 0; i < 10; i++) {
			NewsEntry newsEntry = new NewsEntry();
			newsEntry.setContent("This is example content " + i);
			newsEntry.setDate(new Date(timestamp));
			this.newsEntryDao.save(newsEntry);
			timestamp += 1000 * 60 * 60;
		}
	}
}