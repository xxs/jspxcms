package com.jspxcms.ext.collect;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoDetail;
import com.jspxcms.core.service.InfoService;
import com.jspxcms.ext.domain.Collect;
import com.jspxcms.ext.service.CollectService;

public class CollectorImpl implements Collector {
	private Logger logger = LoggerFactory.getLogger(CollectorImpl.class);

	public void start(Integer collectId) {
		new CollectThread(collectId).start();
	}

	public class CollectThread extends Thread {
		private Integer collectId;

		public CollectThread(Integer collectId) {
			this.collectId = collectId;
		}

		@Override
		public void run() {
			Collect bean = service.get(collectId);
			if (bean.isRunning()) {
				return;
			}
			service.running(collectId);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			try {
				doCollect(bean);
			} catch (Exception e) {
				logger.error("", e);
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error("close HttpClient error!", e);
			}
			service.ready(collectId);
		}

		private void doCollect(Collect bean) throws ClientProtocolException,
				IOException {
			List<String> urls = bean.getUrls();
			Integer nodeId = bean.getNode().getId();
			Integer siteId = bean.getSite().getId();
			Integer creatorId = bean.getUser().getId();
			String charset = bean.getCharset();
			String html;
			List<String> itemUrls;
			CloseableHttpClient httpclient = HttpClients.createDefault();
			for (String url : urls) {
				if (!service.isRunning(collectId)) {
					return;
				}
				html = getHtmlPage(httpclient, url, charset);
				itemUrls = bean.getItemUrls(html);
				for (String itemUrl : itemUrls) {
					if (!service.isRunning(collectId)) {
						return;
					}
					collcetItemPage(httpclient, itemUrl, bean, charset, nodeId,
							creatorId, siteId);
				}
			}
		}

		private String getHtmlPage(CloseableHttpClient httpclient, String url,
				String charset) throws ClientProtocolException, IOException {
			logger.debug("collcet list url: {}", url);
			HttpGet httpget = new HttpGet(url);
			CloseableHttpResponse response = httpclient.execute(httpget);
			String html = null;
			try {
				HttpEntity entity = response.getEntity();
				html = EntityUtils.toString(entity, charset);
			} catch (IOException e) {
				logger.error(null, e);
			} finally {
				response.close();
			}
			return html;
		}

		private void collcetItemPage(CloseableHttpClient httpclient,
				String url, Collect bean, String charset, Integer nodeId,
				Integer creatorId, Integer siteId)
				throws ClientProtocolException, IOException {
			logger.debug("collcet item url: {}", url);
			HttpGet httpget = new HttpGet(url);
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				String html = EntityUtils.toString(entity, charset);
				String title = bean.getTitleHtml(html);
				if (StringUtils.isBlank(title)) {
					return;
				}
				String text = bean.getTextHtml(html);
				if (StringUtils.isBlank(text)) {
					return;
				}
				Info info = new Info();
				InfoDetail detail = new InfoDetail();
				detail.setTitle(title);
				Map<String, String> clobs = new HashMap<String, String>();
				clobs.put(Info.INFO_TEXT, text);
				infoService.save(info, detail, null, null, null, null, null,
						clobs, null, null, null, null, null, nodeId, creatorId,
						Info.COLLECTED, siteId);
			} catch (IOException e) {
				logger.error(null, e);
				return;
			} finally {
				response.close();
			}
		}

	}

	private InfoService infoService;
	private CollectService service;

	@Autowired
	public void setInfoService(InfoService infoService) {
		this.infoService = infoService;
	}

	@Autowired
	public void setService(CollectService service) {
		this.service = service;
	}

}
