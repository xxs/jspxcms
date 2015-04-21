package com.jspxcms.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.SearchFilter;
import com.jspxcms.common.security.CredentialsDigest;
import com.jspxcms.common.security.Digests;
import com.jspxcms.common.util.Encodes;
import com.jspxcms.common.util.RowSide;
import com.jspxcms.core.domain.GlobalMail;
import com.jspxcms.core.domain.GlobalRegister;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.UserDetail;
import com.jspxcms.core.listener.MemberGroupDeleteListener;
import com.jspxcms.core.listener.OrgDeleteListener;
import com.jspxcms.core.listener.UserDeleteListener;
import com.jspxcms.core.repository.UserDao;
import com.jspxcms.core.service.MemberGroupService;
import com.jspxcms.core.service.OrgService;
import com.jspxcms.core.service.UserDetailService;
import com.jspxcms.core.service.UserMemberGroupService;
import com.jspxcms.core.service.UserOrgService;
import com.jspxcms.core.service.UserRoleService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.DeleteException;

/**
 * UserServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, OrgDeleteListener,
		MemberGroupDeleteListener {
	private static final int SALT_SIZE = 8;

	public Page<User> findPage(Integer rank, Integer[] type,
			String orgTreeNumber, Map<String, String[]> params,
			Pageable pageable) {
		return dao.findAll(spec(rank, type, orgTreeNumber, params), pageable);
	}

	public RowSide<User> findSide(Integer rank, Integer[] type,
			String orgTreeNumber, Map<String, String[]> params, User bean,
			Integer position, Sort sort) {
		if (position == null) {
			return new RowSide<User>();
		}
		Limitable limit = RowSide.limitable(position, sort);
		List<User> list = dao.findAll(spec(rank, type, orgTreeNumber, params),
				limit);
		return RowSide.create(list, bean);
	}

	private Specification<User> spec(final Integer rank, final Integer[] type,
			final String orgTreeNumber, Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<User> fsp = SearchFilter.spec(filters, User.class);
		Specification<User> sp = new Specification<User>() {
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
				pred = cb.and(pred, cb.ge(root.<Integer> get("rank"), rank));
				if (ArrayUtils.isNotEmpty(type)) {
					pred = cb.and(pred, root.get("type")
							.in(Arrays.asList(type)));
				}
				if (StringUtils.isNotBlank(orgTreeNumber)) {
					Path<String> orgsPath = root.join("org").<String> get(
							"treeNumber");
					pred = cb.and(pred, cb.like(orgsPath, orgTreeNumber + "%"));
					query.distinct(true);
				}
				return pred;
			}
		};
		return sp;
	}

	public List<User> findByUsername(String[] usernames) {
		return dao.findByUsername(usernames);
	}

	public User getAnonymous() {
		return dao.findOne(0);
	}

	public Integer getAnonymousId() {
		return 0;
	}

	public User get(Integer id) {
		return dao.findOne(id);
	}

	public User findByUsername(String username) {
		return dao.findByUsername(username);
	}

	public User findByValidation(String type, String key) {
		return dao.findByValidationTypeAndValidationKey(type, key);
	}

	public boolean usernameExist(String username) {
		return dao.countByUsername(username) > 0;
	}

	@Transactional
	public void updatePassword(Integer userId, String rawPassword) {
		User user = get(userId);
		user.setRawPassword(rawPassword);
		entryptPassword(user);
		dao.save(user);
	}

	@Transactional
	public void updateEmail(Integer userId, String email) {
		User user = get(userId);
		user.setEmail(email);
		dao.save(user);
	}

	@Transactional
	public void sendVerifyEmail(Site site, User user, GlobalMail mail,
			String subject, String text) {
		UserDetail detail = user.getDetail();
		String key = StringUtils.remove(UUID.randomUUID().toString(), '-');
		user.setValidationKey(key);
		user.setValidationType(Constants.VERIFY_MEMBER_TYPE);
		detail.setValidationDate(new Date());
		detail.setValidationValue(null);

		String url = site.getProtocol() + ":" + site.getUrl(true)
				+ Constants.VERIFY_MEMBER_URL + key;
		String email = user.getEmail();
		String username = user.getUsername();
		String sitename = site.getFullNameOrName();
		subject = GlobalRegister.replaceVerifyEmail(subject, username,
				sitename, url);
		text = GlobalRegister.replaceVerifyEmail(text, username, sitename, url);
		mail.sendMail(new String[] { email }, subject, text);
	}

	@Transactional
	public User verifyMember(User user) {
		if (user == null || user.getStatus() != User.UNVERIFIED) {
			return null;
		}
		user.setStatus(User.NORMAL);

		user.setValidationKey(null);
		user.setValidationType(null);
		UserDetail detail = user.getDetail();
		detail.setValidationDate(null);
		detail.setValidationValue(null);
		return user;
	}

	@Transactional
	public void sendPasswordEmail(Site site, User user, GlobalMail mail,
			String subject, String text) {
		UserDetail detail = user.getDetail();
		String key = StringUtils.remove(UUID.randomUUID().toString(), '-');
		user.setValidationKey(key);
		user.setValidationType(Constants.RETRIEVE_PASSWORD_TYPE);
		detail.setValidationDate(new Date());
		detail.setValidationValue(null);

		String url = site.getProtocol() + ":" + site.getUrl(true)
				+ Constants.RETRIEVE_PASSWORD_URL + key;
		String email = user.getEmail();
		String username = user.getUsername();
		String sitename = site.getFullNameOrName();
		subject = GlobalRegister.replacePasswordEmail(subject, username,
				sitename, url);
		text = GlobalRegister.replacePasswordEmail(text, username, sitename,
				url);
		mail.sendMail(new String[] { email }, subject, text);
	}

	@Transactional
	public User passwordChange(User user, String rawPassword) {
		if (user == null) {
			return null;
		}
		user.setRawPassword(rawPassword);
		entryptPassword(user);

		user.setValidationKey(null);
		user.setValidationType(null);
		UserDetail detail = user.getDetail();
		detail.setValidationDate(null);
		detail.setValidationValue(null);
		return user;
	}

	@Transactional
	public User deletePassword(Integer id) {
		User bean = get(id);
		bean.setPassword(null);
		return bean;
	}

	@Transactional
	public User[] deletePassword(Integer[] ids) {
		User[] beans = new User[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = deletePassword(ids[i]);
		}
		return beans;
	}

	@Transactional
	public User check(Integer id) {
		User bean = get(id);
		if (bean.getStatus() == User.UNVERIFIED) {
			bean.setStatus(User.NORMAL);
		}
		return bean;
	}

	@Transactional
	public User[] check(Integer[] ids) {
		User[] beans = new User[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = check(ids[i]);
		}
		return beans;
	}

	@Transactional
	public User lock(Integer id) {
		User bean = get(id);
		if (bean.getStatus() == User.NORMAL) {
			bean.setStatus(User.LOCKED);
		}
		return bean;
	}

	@Transactional
	public User[] lock(Integer[] ids) {
		User[] beans = new User[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = lock(ids[i]);
		}
		return beans;
	}

	@Transactional
	public User unlock(Integer id) {
		User bean = get(id);
		if (bean.getStatus() == User.LOCKED) {
			bean.setStatus(User.NORMAL);
		}
		return bean;
	}

	@Transactional
	public User[] unlock(Integer[] ids) {
		User[] beans = new User[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = unlock(ids[i]);
		}
		return beans;
	}

	@Transactional
	public User save(User bean, UserDetail detail, Integer[] roleIds,
			Integer[] orgIds, Integer[] groupIds, Integer orgId,
			Integer groupId, String ip) {
		bean.setOrg(orgService.get(orgId));
		bean.setGroup(groupService.get(groupId));
		entryptPassword(bean);
		bean.applyDefaultValue();
		bean = dao.save(bean);

		detailService.save(detail, bean, ip);
		userRoleService.save(bean, roleIds);
		userOrgService.save(bean, orgIds, orgId);
		userMemberGroupService.save(bean, groupIds, groupId);
		return bean;
	}

	/**
	 * 注册用户
	 * 
	 * 验证模式（发邮件）
	 * 
	 * @param groupId
	 *            会员组ID
	 * @param orgId
	 *            组织ID
	 * @param username
	 * @param password
	 * @param email
	 * @param gender
	 * @param birthDate
	 * @param bio
	 * @param comeFrom
	 * @param qq
	 * @param msn
	 * @param weixin
	 * @return
	 */
	@Transactional
	public User register(String ip, int groupId, int orgId, int status,
			String username, String password, String email, String gender,
			Date birthDate, String bio, String comeFrom, String qq, String msn,
			String weixin) {
		User user = new User();
		user.setUsername(username);
		user.setRawPassword(password);
		user.setEmail(email);
		user.setGender(gender);
		user.setBirthDate(birthDate);
		user.setStatus(status);
		user.setType(User.MEMBER);

		UserDetail detail = new UserDetail();
		detail.setBio(bio);
		detail.setComeFrom(comeFrom);
		detail.setQq(qq);
		detail.setMsn(msn);
		detail.setWeixin(weixin);

		save(user, detail, null, null, null, orgId, groupId, ip);
		return user;
	}

	@Transactional
	public User update(User bean, UserDetail detail, Integer[] roleIds,
			Integer[] orgIds, Integer[] groupIds, Integer orgId,
			Integer groupId, Integer topOrgId, Integer siteId) {
		if (orgId != null) {
			bean.setOrg(orgService.get(orgId));
		}
		if (groupId != null) {
			bean.setGroup(groupService.get(groupId));
		}
		// 密码不为空，则修改密码
		if (StringUtils.isNotBlank(bean.getRawPassword())) {
			entryptPassword(bean);
		}
		bean.applyDefaultValue();
		bean = dao.save(bean);
		if (detail != null) {
			detailService.update(detail);
		}
		if (bean.isAdmin()) {
			userRoleService.update(bean, roleIds, siteId);
		} else {
			// 不是管理员则删除所有角色
			userRoleService.deleteByUserId(bean.getId());
		}
		userOrgService.update(bean, orgIds, orgId, topOrgId);
		userMemberGroupService.update(bean, groupIds, groupId);
		return bean;
	}

	@Transactional
	public User update(User user, UserDetail detail) {
		dao.save(user);
		detailService.update(detail);
		return user;
	}

	private User doDelete(Integer id) {
		User entity = dao.findOne(id);
		if (entity != null) {
			if (entity.getId() <= 1) {
				throw new IllegalStateException(
						"User 0(anonymous) or 1(admin) cannot be delete!");
			}
			dao.delete(entity);
		}
		return entity;
	}

	@Transactional
	public User delete(Integer id) {
		if (id == null) {
			return null;
		}
		firePreDelete(new Integer[] { id });
		User bean = doDelete(id);
		return bean;
	}

	@Transactional
	public User[] delete(Integer[] ids) {
		if (ids == null) {
			return null;
		}
		firePreDelete(ids);
		int len = ids.length;
		List<User> list = new ArrayList<User>(len);
		User bean;
		for (int i = 0; i < len; i++) {
			bean = doDelete(ids[i]);
			if (bean != null) {
				list.add(bean);
			}
		}
		return list.toArray(new User[list.size()]);
	}

	private void entryptPassword(User user) {
		byte[] saltBytes = Digests.generateSalt(SALT_SIZE);
		String salt = Encodes.encodeHex(saltBytes);
		user.setSalt(salt);

		String rawPass = user.getRawPassword();
		String encPass = credentialsDigest.digest(rawPass, saltBytes);
		user.setPassword(encPass);
	}

	public void preMemberGroupDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countByGroupId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("member.management");
			}
		}
	}

	public void preOrgDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countByOrgId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("member.management");
			}
		}

	}

	private void firePreDelete(Integer[] ids) {
		if (!CollectionUtils.isEmpty(deleteListeners)) {
			for (UserDeleteListener listener : deleteListeners) {
				listener.preUserDelete(ids);
			}
		}
	}

	private List<UserDeleteListener> deleteListeners;

	@Autowired(required = false)
	public void setDeleteListeners(List<UserDeleteListener> deleteListeners) {
		this.deleteListeners = deleteListeners;
	}

	private UserOrgService userOrgService;
	private UserMemberGroupService userMemberGroupService;
	private UserRoleService userRoleService;
	private OrgService orgService;
	private MemberGroupService groupService;
	private UserDetailService detailService;
	private CredentialsDigest credentialsDigest;

	@Autowired
	public void setUserOrgService(UserOrgService userOrgService) {
		this.userOrgService = userOrgService;
	}

	@Autowired
	public void setUserMemberGroupService(
			UserMemberGroupService userMemberGroupService) {
		this.userMemberGroupService = userMemberGroupService;
	}

	@Autowired
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	@Autowired
	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	@Autowired
	public void setGroupService(MemberGroupService groupService) {
		this.groupService = groupService;
	}

	@Autowired
	public void setDetailService(UserDetailService detailService) {
		this.detailService = detailService;
	}

	@Autowired
	public void setCredentialsDigest(CredentialsDigest credentialsDigest) {
		this.credentialsDigest = credentialsDigest;
	}

	private UserDao dao;

	@Autowired
	public void setDao(UserDao dao) {
		this.dao = dao;
	}

}
