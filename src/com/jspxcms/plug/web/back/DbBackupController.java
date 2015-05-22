package com.jspxcms.plug.web.back;

import static com.jspxcms.core.support.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.support.Constants.MESSAGE;
import static com.jspxcms.core.support.Constants.OPERATION_SUCCESS;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.web.PathResolver;
import com.jspxcms.core.support.WebFile;
import com.jspxcms.plug.dbbackup.DbBackupExcutor;

@Controller
@RequestMapping("/plug/db_backup")
public class DbBackupController {
	private static final Logger logger = LoggerFactory
			.getLogger(DbBackupController.class);
	public static final String BACKUP_PATH = "/WEB-INF/db_backup";

	@RequiresPermissions("plug:db_backup:list")
	@RequestMapping("list.do")
	public String list(HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		String realPath = pathResolver.getPath(BACKUP_PATH);
		File parent = new File(realPath);
		WebFile parentWebFile = new WebFile(parent, parent.getAbsolutePath(),
				request.getContextPath());
		List<WebFile> list = parentWebFile.listFiles();
		modelMap.addAttribute("list", list);
		return "plug/db_backup/db_backup_list";
	}

	@RequiresPermissions("plug:db_backup:backup")
	@RequestMapping("backup.do")
	public String backup(HttpServletRequest request, RedirectAttributes ra) {
		File dir = new File(pathResolver.getPath(BACKUP_PATH));
		excutor.backup(dir, false);
		logger.info("database backup");
		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		return "redirect:list.do";
	}

	@RequiresPermissions("plug:db_backup:restore")
	@RequestMapping("restore.do")
	public String restore(String id, HttpServletRequest request,
			RedirectAttributes ra) {
		File file = new File(pathResolver.getPath(BACKUP_PATH), id);
		excutor.restore(file);
		logger.info("database restore");
		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		return "redirect:list.do";
	}

	@RequiresPermissions("plug:db_backup:download")
	@RequestMapping("download.do")
	public String download(String id, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		return null;
	}

	@RequiresPermissions("plug:db_backup:delete")
	@RequestMapping("delete.do")
	public String delete(String[] ids, Integer querySlotId,
			RedirectAttributes ra) {
		for (String id : ids) {
			File file = new File(pathResolver.getPath(BACKUP_PATH), id);
			FileUtils.deleteQuietly(file);
		}
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}

	@Autowired
	private PathResolver pathResolver;
	@Autowired
	private DbBackupExcutor excutor;
}
