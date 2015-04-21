package com.jspxcms.core.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.UserDetail;
import com.jspxcms.core.repository.UserDao;
import com.jspxcms.core.service.UserShiroService;

@Service
@Transactional(readOnly = true)
public class UserShiroServiceImpl implements UserShiroService {
	public User findByUsername(String username) {
		return dao.findByUsername(username);
	}

	public User get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public User updateLoginSuccess(Integer userId, String loginIp) {
		User user = get(userId);
		UserDetail detail = user.getDetail();
		detail.setLoginErrorDate(null);
		detail.setLoginErrorCount(0);
		detail.setPrevLoginIp(detail.getLastLoginIp());
		detail.setPrevLoginDate(detail.getLastLoginDate());
		detail.setLastLoginIp(loginIp);
		detail.setLastLoginDate(new Timestamp(System.currentTimeMillis()));
		detail.setLogins(detail.getLogins() + 1);
		return user;
	}

	@Transactional
	public User updateLoginFailure(String username) {
		User user = findByUsername(username);
		if (user != null) {
			UserDetail detail = user.getDetail();
			Date date = detail.getLoginErrorDate();
			if (date != null
					&& System.currentTimeMillis() - date.getTime() < UserDetail.LOGIN_ERROR_MILLIS) {
				detail.setLoginErrorCount(detail.getLoginErrorCount() + 1);
			} else {
				detail.setLoginErrorCount(1);
			}
			detail.setLoginErrorDate(new Timestamp(System.currentTimeMillis()));
		}
		return user;
	}

	private UserDao dao;

	@Autowired
	public void setDao(UserDao dao) {
		this.dao = dao;
	}
}
