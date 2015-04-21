package com.jspxcms.core.web.fore;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jspxcms.common.util.JsonMapper;
import com.jspxcms.common.web.PathResolver;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.common.web.Validations;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.MemberGroup;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.Org;
import com.jspxcms.core.domain.ScoreBoard;
import com.jspxcms.core.domain.ScoreItem;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.service.InfoBufferService;
import com.jspxcms.core.service.InfoQueryService;
import com.jspxcms.core.service.ScoreBoardService;
import com.jspxcms.core.service.ScoreItemService;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.service.VoteMarkService;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;
import com.jspxcms.core.support.TitleText;

/**
 * InfoController
 * 
 * @author liufang
 * 
 */
@Controller
public class InfoController {
	@RequestMapping(value = "/info/{infoId:[0-9]+}_{page:[0-9]+}.jspx")
	public String infoByPagePath(@PathVariable Integer infoId,
			@PathVariable Integer page, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		Site site = Context.getCurrentSite(request);
		return doInfo(infoId, page, site, resp, messages, request, modelMap);
	}

	@RequestMapping(value = "/site_{siteNumber}/info/{infoId:[0-9]+}_{page:[0-9]+}.jspx")
	public String infoByPagePathSite(@PathVariable String siteNumber,
			@PathVariable Integer infoId, @PathVariable Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		if (!Validations.notEmpty(siteNumber, messages, "siteNumber")) {
			return resp.badRequest();
		}
		Site site = siteService.findByNumber(siteNumber);
		if (!Validations.exist(site, messages, "Site", siteNumber)) {
			return resp.badRequest();
		}
		Context.setCurrentSite(request, site);
		Context.setCurrentSite(site);
		return doInfo(infoId, page, site, resp, messages, request, modelMap);
	}

	@RequestMapping(value = "/info/{infoId:[0-9]+}.jspx")
	public String info(@PathVariable Integer infoId, Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		return infoByPagePath(infoId, page, request, response, modelMap);
	}

	@RequestMapping(value = "/site_{siteNumber}/info/{infoId:[0-9]+}.jspx")
	public String infoSite(@PathVariable String siteNumber,
			@PathVariable Integer infoId, Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		return infoByPagePathSite(siteNumber, infoId, page, request, response,
				modelMap);
	}

	private String doInfo(Integer infoId, Integer page, Site site,
			Response resp, List<String> messages, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Info info = query.get(infoId);
		if (info == null) {
			return resp.badRequest("Info not found: " + infoId);
		}
		if (!info.isNormal()) {
			return resp.forbidden();
		}
		Collection<MemberGroup> groups = Context.getCurrentGroups(request);
		Collection<Org> orgs = Context.getCurrentOrgs(request);
		if (!info.isViewPerm(groups, orgs)) {
			User user = Context.getCurrentUser(request);
			if (user != null) {
				return resp.forbidden();
			} else {
				return resp.unauthorized();
			}
		}
		if (!info.getSite().getId().equals(site.getId())) {
			site = info.getSite();
			Context.setCurrentSite(request, site);
			Context.setCurrentSite(site);
		}
		if (info.isLinked()) {
			return "redirect:" + info.getLinkUrl();
		}
		page = page == null ? 1 : page;
		Node node = info.getNode();
		List<TitleText> textList = info.getTextList();
		TitleText infoText = TitleText.getTitleText(textList, page);
		String title = infoText.getTitle();
		String text = infoText.getText();
		modelMap.addAttribute("info", info);
		modelMap.addAttribute("node", node);
		modelMap.addAttribute("title", title);
		modelMap.addAttribute("text", text);

		Page<String> pagedList = new PageImpl<String>(Arrays.asList(text),
				new PageRequest(page - 1, 1), textList.size());
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page, info, pagedList);

		String template = Servlets.getParameter(request, "template");
		if (StringUtils.isNotBlank(template)) {
			return template;
		} else {
			return info.getTemplate();
		}
	}

	@RequestMapping(value = "/info_download.jspx")
	public String download(Integer id, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap)
			throws IOException {
		Response resp = new Response(request, response, modelMap);
		Info info = query.get(id);
		infoBufferService.updateDownloads(id);
		String path = info.getFile();
		if (StringUtils.isBlank(path)) {
			return resp.notFound();
		}
		if (path.startsWith("http") || path.startsWith("ftp")) {
			response.sendRedirect(path);
			return null;
		}
		String ctx = info.getSite().getContextPath();
		if (StringUtils.isNotBlank(ctx) && path.startsWith(ctx)) {
			path = path.substring(ctx.length());
		}
		if (!path.startsWith(Constants.UPLOADS)) {
			return resp.forbidden("Cannot download file not start with '"
					+ Constants.UPLOADS + "': " + path);
		}
		File file = new File(pathResolver.getPath(path));
		Servlets.setDownloadHeader(response, file.getName());
		int length = (int) file.length();
		response.setContentLength(length);
		OutputStream output = response.getOutputStream();
		FileUtils.copyFile(file, output);
		output.flush();
		return null;
	}

	@RequestMapping(value = "/info_views.jspx")
	public void view(Integer id, HttpServletResponse response) {
		if (id == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		if (query.get(id) == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		String result = Integer.toString(bufferService.updateViews(id));
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_views/{id:[0-9]+}.jspx")
	public void views(@PathVariable Integer id,
			@RequestParam(defaultValue = "true") boolean isUpdate,
			HttpServletResponse response) {
		Info info = query.get(id);
		if (info == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Integer views;
		if (isUpdate) {
			views = bufferService.updateViews(id);
		} else {
			views = info.getBufferViews();
		}
		String result = Integer.toString(views);
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_comments/{id:[0-9]+}.jspx")
	public void comments(@PathVariable Integer id, HttpServletResponse response) {
		Info info = query.get(id);
		int comments;
		if (info != null) {
			comments = info.getBufferComments();
		} else {
			comments = 0;
		}
		String result = Integer.toString(comments);
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_downloads/{id:[0-9]+}.jspx")
	public void downloads(@PathVariable Integer id, HttpServletResponse response) {
		Info info = query.get(id);
		int downloads;
		if (info != null) {
			downloads = info.getBufferDownloads();
		} else {
			downloads = 0;
		}
		String result = Integer.toString(downloads);
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_digg.jspx")
	public void digg(Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		if (id == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Info info = query.get(id);
		if (info == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Integer userId = Context.getCurrentUserId(request);
		String ip = Servlets.getRemoteAddr(request);
		String cookie = Site.getIdentityCookie(request, response);
		if (userId != null) {
			if (voteMarkService.isUserVoted(Info.DIGG_MARK, id, userId, null)) {
				Servlets.writeHtml(response, "0");
				return;
			}
		} else if (voteMarkService.isCookieVoted(Info.DIGG_MARK, id, cookie,
				null)) {
			Servlets.writeHtml(response, "0");
			return;
		}
		String result = Integer.toString(bufferService.updateDiggs(id, userId,
				ip, cookie));
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_bury.jspx")
	public void bury(Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		if (id == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Info info = query.get(id);
		if (info == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Integer userId = Context.getCurrentUserId(request);
		String ip = Servlets.getRemoteAddr(request);
		String cookie = Site.getIdentityCookie(request, response);
		if (userId != null) {
			if (voteMarkService.isUserVoted(Info.DIGG_MARK, id, userId, null)) {
				Servlets.writeHtml(response, "0");
				return;
			}
		} else if (voteMarkService.isCookieVoted(Info.DIGG_MARK, id, cookie,
				null)) {
			Servlets.writeHtml(response, "0");
			return;
		}
		String result = Integer.toString(bufferService.updateBurys(id, userId,
				ip, cookie));
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_diggs/{id:[0-9]+}.jspx")
	public void diggs(@PathVariable Integer id, HttpServletResponse response) {
		Info info = query.get(id);
		int diggs;
		int burys;
		if (info != null) {
			diggs = info.getBufferDiggs();
			burys = info.getBufferBurys();
		} else {
			diggs = 0;
			burys = 0;
		}
		String result = "[" + diggs + "," + burys + "]";
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_scoring.jspx", method = { RequestMethod.POST })
	public void scoring(Integer id, Integer itemId, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		if (id == null || itemId == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Info info = query.get(id);
		ScoreItem item = scoreItemService.get(itemId);
		if (info == null || item == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Integer userId = Context.getCurrentUserId(request);
		String ip = Servlets.getRemoteAddr(request);
		String cookie = Site.getIdentityCookie(request, response);
		if (userId != null) {
			if (voteMarkService.isUserVoted(Info.SCORE_MARK, id, userId, null)) {
				Servlets.writeHtml(response, "0");
				return;
			}
		} else if (voteMarkService.isCookieVoted(Info.SCORE_MARK, id, cookie,
				null)) {
			Servlets.writeHtml(response, "0");
			return;
		}
		int score = infoBufferService.updateScore(id, itemId, userId, ip,
				cookie);
		String result = String.valueOf(score);
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_score/{id:[0-9]+}.jspx")
	public void score(@PathVariable Integer id, HttpServletResponse response) {
		List<ScoreBoard> boardList = scoreBoardService.findList(
				Info.SCORE_MARK, id);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (ScoreBoard board : boardList) {
			map.put(board.getItem().getId().toString(), board.getVotes());
		}
		JsonMapper mapper = new JsonMapper();
		String result = mapper.toJson(map);
		Servlets.writeHtml(response, result);
	}

	@Autowired
	private SiteService siteService;
	@Autowired
	private ScoreBoardService scoreBoardService;
	@Autowired
	private VoteMarkService voteMarkService;
	@Autowired
	private ScoreItemService scoreItemService;
	@Autowired
	private InfoBufferService bufferService;
	@Autowired
	private InfoQueryService query;
	@Autowired
	private InfoBufferService infoBufferService;
	@Autowired
	private PathResolver pathResolver;
}
