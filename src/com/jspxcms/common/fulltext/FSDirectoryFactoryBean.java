package com.jspxcms.common.fulltext;

import java.io.File;

import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 * FSDirecotry工厂类
 * 
 * @author liufang
 * 
 */
public class FSDirectoryFactoryBean implements FactoryBean<FSDirectory>,
		InitializingBean, DisposableBean {
	private FSDirectory directory;
	private Resource location;

	public FSDirectory getObject() throws Exception {
		return directory;
	}

	public Class<FSDirectory> getObjectType() {
		return FSDirectory.class;
	}

	public void afterPropertiesSet() throws Exception {
		if (location == null) {
			throw new BeanInitializationException(
					"Must specify a location property");
		}
		File locationFile = location.getFile();
		boolean locationExists = locationFile.exists();
		if (locationExists && !locationFile.isDirectory()) {
			throw new BeanInitializationException(
					"location must be a directory");
		}
		directory = FSDirectory.open(locationFile);
	}

	public void destroy() throws Exception {
		if (directory != null) {
			directory.close();
		}
	}

	public boolean isSingleton() {
		return true;
	}

	public void setLocation(Resource location) {
		this.location = location;
	}
}
