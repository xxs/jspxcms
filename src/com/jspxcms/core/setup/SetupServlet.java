package com.jspxcms.core.setup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.jspxcms.common.security.Digests;
import com.jspxcms.common.security.SHA1CredentialsDigest;
import com.jspxcms.common.util.Encodes;

/**
 * 安装Servlet
 * 
 * @author liufang
 * 
 */
public class SetupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Map<Integer, String> STEP_MAP = new HashMap<Integer, String>();
	static {
		STEP_MAP.put(0, "/setup/license.jsp");
		STEP_MAP.put(1, "/setup/database.jsp");
		STEP_MAP.put(2, "/setup/finish.jsp");
	}

	public SetupServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer step = (Integer) session.getAttribute("step");
		if (step == null) {
			step = 0;
		}
		RequestDispatcher rd = request.getRequestDispatcher(STEP_MAP.get(step));
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer step = 0;
		String stepStr = request.getParameter("step");
		if (StringUtils.isNotBlank(stepStr)) {
			step = Integer.parseInt(stepStr);
		}

		if (StringUtils.isNotBlank(request.getParameter("previous"))) {
			previous(request, response);
		} else if (step == 0) {
			license(request, response);
		} else if (step == 1) {
			try {
				database(request, response);
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		response.sendRedirect(request.getContextPath() + "/");
	}

	private void previous(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Integer step = (Integer) session.getAttribute("step");
		if (step != null && step > 0) {
			session.setAttribute("step", step - 1);
		}
	}

	private void license(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.setAttribute("step", 1);
	}

	private void database(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		long begin = System.currentTimeMillis();
		String host = request.getParameter("host");
		String port = request.getParameter("port");
		String name = request.getParameter("name");
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		boolean isCreateDatabase = "true".equals(request
				.getParameter("isCreateDatabase"));

		String adminUsername = request.getParameter("adminUsername");
		String adminPassword = request.getParameter("adminPassword");

		String version = request.getParameter("version");
		int serverPort = request.getServerPort();
		String webPort = serverPort == 80 ? null : String.valueOf(serverPort);
		String contextPath = request.getContextPath();

		if (isCreateDatabase) {
			createDatabase(host, port, name, user, password);
		} else {
			alterDatabaseCharset(host, port, name, user, password);
		}
		createDatabaseTable(host, port, name, user, password, adminUsername,
				adminPassword, version, webPort, contextPath);
		copyFile(request, host, port, name, user, password);

		HttpSession session = request.getSession();
		session.setAttribute("step", 2);
		long processed = System.currentTimeMillis() - begin;
		System.out.println("processed in: " + processed);
	}

	private void createDatabase(String host, String port, String name,
			String user, String password) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String connectionUrl = "jdbc:mysql://" + host;
		if (StringUtils.isNotBlank(port)) {
			connectionUrl += ":" + port;
		}
		connectionUrl += "?user=" + user + "&password=" + password
				+ "&characterEncoding=utf8";
		System.out.println(connectionUrl);
		Connection connection = DriverManager.getConnection(connectionUrl);
		Statement statement = connection.createStatement();
		String dropDatebaseSql = "drop database if exists " + name;
		String createDatabaseSql = "create database " + name
				+ " character set utf8";
		System.out.println(dropDatebaseSql);
		statement.execute(dropDatebaseSql);
		System.out.println(createDatabaseSql);
		statement.execute(createDatabaseSql);
		statement.close();
		connection.close();
	}

	private void alterDatabaseCharset(String host, String port, String name,
			String user, String password) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = getConnection(host, port, name, user, password);
		Statement statement = connection.createStatement();
		String alterDatabase = "alter database " + name + " character set utf8";
		statement.execute(alterDatabase);
		statement.close();
		connection.close();
	}

	private void createDatabaseTable(String host, String port, String name,
			String user, String password, String adminUsername,
			String adminPassword, String version, String webPort,
			String contextPath) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			IOException {
		Connection connection = getConnection(host, port, name, user, password);
		Statement statement = connection.createStatement();
		String tablePath = getServletContext().getRealPath(
				"/setup/database/1_table_sql.txt");
		excuteSQL(statement, new File(tablePath));
		String dataPath = getServletContext().getRealPath(
				"/setup/database/2_data_sql.txt");
		excuteSQL(statement, new File(dataPath));
		String referencePath = getServletContext().getRealPath(
				"/setup/database/3_reference_sql.txt");
		excuteSQL(statement, new File(referencePath));

		// 修改用户名密码
		byte[] saltBytes = Digests.generateSalt(8);
		String salt = Encodes.encodeHex(saltBytes);
		String sql;
		if (StringUtils.isNotBlank(adminPassword)) {
			SHA1CredentialsDigest digest = new SHA1CredentialsDigest();
			String encPass = digest.digest(adminPassword, saltBytes);
			sql = "update cms_user set f_username='" + adminUsername
					+ "',f_password='" + encPass + "', f_salt='" + salt
					+ "' where f_user_id=1";
			System.out.println(sql);
			statement.execute(sql);
		} else {
			sql = "update cms_user set f_username='" + adminUsername
					+ "',f_password=null, f_salt='" + salt
					+ "' where f_user_id=1";
			System.out.println(sql);
			statement.execute(sql);
		}
		// 修改版本号
		sql = "update cms_global set f_version='" + version + "'";
		System.out.println(sql);
		statement.execute(sql);
		// 修改部署路径和端口号
		if (StringUtils.isNotBlank(webPort)) {
			sql = "update cms_global set f_port='" + webPort + "'";
			System.out.println(sql);
			statement.execute(sql);
		} else {
			sql = "update cms_global set f_port=null";
			System.out.println(sql);
			statement.execute(sql);
		}
		if (StringUtils.isNotBlank(contextPath)) {
			sql = "update cms_global set f_context_path='" + contextPath + "'";
			System.out.println(sql);
			statement.execute(sql);
		} else {
			sql = "update cms_global set f_context_path=null";
			System.out.println(sql);
			statement.execute(sql);
		}
		statement.close();
		connection.close();
	}

	private void copyFile(ServletRequest request, String host, String port,
			String name, String user, String password) throws IOException {
		String customPath = getServletContext().getRealPath(
				"/setup/file/custom.properties");
		File customFile = new File(customPath);
		String customString = FileUtils.readFileToString(customFile, "utf-8");
		customString = StringUtils.replace(customString, "{host}", host);
		if (StringUtils.isNotBlank(port)) {
			customString = StringUtils.replace(customString, "{port}", port);
		} else {
			customString = StringUtils.remove(customString, ":{port}");
		}
		customString = StringUtils.replace(customString, "{name}", name);
		customString = StringUtils.replace(customString, "{user}", user);
		customString = StringUtils
				.replace(customString, "{password}", password);
		String destCustomPath = getServletContext().getRealPath(
				"/WEB-INF/conf/custom.properties");
		FileUtils.write(new File(destCustomPath), customString, "utf-8");

		String webPath = getServletContext().getRealPath("/setup/file/web.xml");
		String destWebPath = getServletContext()
				.getRealPath("/WEB-INF/web.xml");
		FileUtils.copyFile(new File(webPath), new File(destWebPath));
	}

	private Connection getConnection(String host, String port, String name,
			String user, String password) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String connectionUrl = "jdbc:mysql://" + host;
		if (StringUtils.isNotBlank(port)) {
			connectionUrl += ":" + port;
		}
		connectionUrl += "/" + name + "?user=" + user + "&password=" + password
				+ "&characterEncoding=utf8";
		Connection conn = DriverManager.getConnection(connectionUrl);
		return conn;
	}

	private void excuteSQL(Statement statement, File file) throws IOException,
			SQLException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), "utf-8"));
		String line = null;
		StringBuilder sqlBuilder = new StringBuilder();
		// int i = 0;
		while ((line = reader.readLine()) != null) {
			if (StringUtils.isBlank(line) || line.startsWith("--")
					|| line.startsWith("/*")) {
				continue;
			}
			sqlBuilder.append(line);
			if (line.endsWith(";")) {
				sqlBuilder.setLength(sqlBuilder.length() - 1);
				String sql = sqlBuilder.toString();
				System.out.println(sql);
				statement.execute(sql);
				sqlBuilder.setLength(0);
			}
		}
	}
}
