package com.jspxcms.core.fulltext;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jspxcms.common.fulltext.LuceneIndexTemplate;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.listener.AbstractInfoListener;

/**
 * InfoFulltextListener
 * 
 * @author liufang
 * 
 */
@Component
public class InfoFulltextListener extends AbstractInfoListener {
	@Override
	public void postInfoSave(Info bean) {
		template.addDocument(FInfo.doc(bean));
	}

	@Override
	public void postInfoUpdate(Info bean) {
		template.updateDocument(FInfo.id(bean.getId()), FInfo.doc(bean));
	}

	@Override
	public void postInfoPass(List<Info> beans) {
		for (Info bean : beans) {
			template.updateDocument(FInfo.id(bean.getId()), FInfo.doc(bean));
		}
	}

	@Override
	public void postInfoReject(List<Info> beans) {
		for (Info bean : beans) {
			template.updateDocument(FInfo.id(bean.getId()), FInfo.doc(bean));
		}
	}

	@Override
	public void postInfoDelete(List<Info> beans) {
		for (Info bean : beans) {
			template.deleteDocuments(FInfo.id(bean.getId()));
		}
	}

	private LuceneIndexTemplate template;

	@Autowired
	public void setLuceneIndexTemplate(LuceneIndexTemplate template) {
		this.template = template;
	}

}
