package com.jspxcms.core.support;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

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
	
	public static final String DB_BACKUP_PATH = "/backup/db";
	public static final String SHE_BACKUP_PATH = "/backup/she";
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
	
	/**
	 * 使用http访问网络资源时使用的USER_ANGENT
	 */
	public static final String USER_ANGENT = "Mozilla/5.0";
	/**
	 * 前台登录地址
	 */
	public static String LOGIN_URL = "/login.jspx";
	/**
	 * 模版存储路径。
	 * 
	 * 模版路径在应用内，以/开头。如：/template。
	 * 
	 * 模版路径在应用外，以file:开头。可以实现程序与模版分开部署（配合上传文件发布点和全文索引位置fsDirectory.location）。
	 * 如：file:d:\\jspxcms\\template 或 file:/home/mysite/template。
	 * 因模版内含有图片，该路径应能通过同一域名访问，通常单独作为一个应用部署。
	 */
	public static String TEMPLATE_STORE_PATH = "/template";
	/**
	 * 模版显示路径。
	 * 
	 * 如果模版在应用内，则应与模版存储路径一致。如果模版在应用外，作为独立应用部署，则与部署上下文路径相关。
	 */
	public static String TEMPLATE_DISPLAY_PATH = "/template";
	/**
	 * 全局模版路径。作为模版目录的子目录，与模版路径结合后通常为/template/global。
	 * 
	 * 通常模版按站点存储，但有部分模版或图片是全局的，比如用户默认头像。
	 */
	public static String TEMPLATE_GLOBAL = "/global";
	/**
	 * 用户个人空间路径。作为上传目录的子目录，与上传路径结合后通常为/uploads/users。
	 */
	public static String UPLOADS_USERS = "/users";
	public static String OPENOFFICE_HOST = null;
	public static int OPENOFFICE_PORT = -1;
	public static String SWFTOOLS_PDF2SWF = null;
	public static String SWFTOOLS_LANGUAGEDIR = null;
	public static boolean IS_ROOT_ALL_PERMS = false;
	public static String CMSCP = "/cmscp";
	public static String BACK_SUCCESS_URL = "/cmscp/index.do";
	public static String BACK_LOGIN_URL = "/cmscp/login.do";
	public static String TAG_KEYWORDS_SPLIT = "，；;｜|";
	public static String DEF_USERNAME = "";
	public static String DEF_PASSWORD = "";

	public void loadProperties(Properties properties) {
		if (properties == null) {
			return;
		}
		String loginUrl = properties.getProperty("loginUrl");
		if (loginUrl != null) {
			LOGIN_URL = loginUrl;
		}
		String templateStorePath = properties.getProperty("templateStorePath");
		if (templateStorePath != null) {
			TEMPLATE_STORE_PATH = templateStorePath;
		}
		String templateDisplayPath = properties
				.getProperty("templateDisplayPath");
		if (templateDisplayPath != null) {
			TEMPLATE_DISPLAY_PATH = templateDisplayPath;
		}
		String openofficeHost = properties.getProperty("openofficeHost");
		if (openofficeHost != null) {
			OPENOFFICE_HOST = openofficeHost;
		}
		String openofficePort = properties.getProperty("openofficePort");
		if (StringUtils.isNotBlank(openofficePort)) {
			OPENOFFICE_PORT = Integer.valueOf(openofficePort);
		}
		String swftoolsPdf2swf = properties.getProperty("swftoolsPdf2swf");
		if (swftoolsPdf2swf != null) {
			SWFTOOLS_PDF2SWF = swftoolsPdf2swf;
		}
		String swftoolsLanguagedir = properties.getProperty("swftoolsLanguagedir");
		if (swftoolsLanguagedir != null) {
			SWFTOOLS_LANGUAGEDIR = swftoolsLanguagedir;
		}
		String isRootAllPerms = properties.getProperty("isRootAllPerms");
		if ("true".equals(isRootAllPerms)) {
			IS_ROOT_ALL_PERMS = true;
		}
		String cmscp = properties.getProperty("cmscp");
		if (cmscp != null) {
			CMSCP = cmscp;
		}
		String backSuccessUrl = properties.getProperty("backSuccessUrl");
		if (backSuccessUrl != null) {
			BACK_SUCCESS_URL = backSuccessUrl;
		}
		String backLoginUrl = properties.getProperty("backLoginUrl");
		if (backLoginUrl != null) {
			BACK_LOGIN_URL = backLoginUrl;
		}
		String tagKeywordsSplit = properties.getProperty("tagKeywordsSplit");
		if (tagKeywordsSplit != null) {
			TAG_KEYWORDS_SPLIT = tagKeywordsSplit;
		}
		String defUsername = properties.getProperty("defUsername");
		if (defUsername != null) {
			DEF_USERNAME = defUsername;
		}
		String defPassword = properties.getProperty("defPassword");
		if (defPassword != null) {
			DEF_PASSWORD = defPassword;
		}
	}
}
