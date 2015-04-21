package com.jspxcms.core.support;

/**
 * CMS常量
 * 
 * @author liufang
 * 
 */
public class Constants {
	/**
	 * 当前版本
	 */
	public static final String VERSION = "4.1.1";
	public static final String VERIFY_MEMBER_TYPE = "verify_member";
	public static final String VERIFY_MEMBER_URL = "/verify_member.jspx?key=";
	public static final String RETRIEVE_PASSWORD_TYPE = "retrieve_password";
	public static final String RETRIEVE_PASSWORD_URL = "/retrieve_password.jspx?key=";
	public static final String LOGIN_URL = "/login.jspx";
	/**
	 * 内容访问路径
	 */
	public static final String INFO_PATH = "info";
	/**
	 * 栏目访问路径
	 */
	public static final String NODE_PATH = "node";
	/**
	 * 动态也后缀
	 */
	public static final String DYNAMIC_SUFFIX = ".jspx";
	/**
	 * 站点前缀
	 */
	public static final String SITE_PREFIX = "/site_";
	/**
	 * 上下文路径
	 */
	public static final String CTX = "ctx";
	/**
	 * 上传路径
	 */
	public static final String UPLOADS = "/uploads";
	/**
	 * 页面操作状态
	 */
	public static final String OPRT = "oprt";
	/**
	 * 新增状态
	 */
	public static final String CREATE = "create";
	/**
	 * 编辑状态
	 */
	public static final String EDIT = "edit";
	/**
	 * 查看状态
	 */
	public static final String VIEW = "view";
	/**
	 * 重定向至修改页面
	 */
	public static final String REDIRECT_EDIT = "edit";
	/**
	 * 重定向至查看页面
	 */
	public static final String REDIRECT_VIEW = "view";
	/**
	 * 重定向至列表页面
	 */
	public static final String REDIRECT_LIST = "list";
	/**
	 * 重定向至新增页面
	 */
	public static final String REDIRECT_CREATE = "create";
	/**
	 * 搜索字符串前缀
	 */
	public static final String SEARCH_PREFIX = "search_";
	/**
	 * 搜索字符串
	 */
	public static final String SEARCH_STRING = "searchstring";
	/**
	 * 搜索字符串（不含排序）
	 */
	public static final String SEARCH_STRING_NO_SORT = "searchstringnosort";
	/**
	 * 后台路径
	 */
	public static final String BACK_URL = "/cmscp/";
	/**
	 * 身份识别COOKIE名称
	 */
	public static final String IDENTITY_COOKIE_NAME = "_jspxcms";

	public static final String STATUS = "status";
	public static final String MESSAGE = "message";
	public static final String OPERATION_SUCCESS = "operationSuccess";
	public static final String OPERATION_FAILURE = "operationFailure";
	public static final String SAVE_SUCCESS = "saveSuccess";
	public static final String DELETE_SUCCESS = "deleteSuccess";
}
