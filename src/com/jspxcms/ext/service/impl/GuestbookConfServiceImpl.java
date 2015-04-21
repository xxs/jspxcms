package com.jspxcms.ext.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.ext.domain.Guestbook;
import com.jspxcms.ext.service.GuestbookConfService;

/**
 * GuestbookConfServiceImpl
 * 
 * @author yangxing
 * 
 */
@Service
@Transactional(readOnly = true)
public class GuestbookConfServiceImpl implements GuestbookConfService {

	public Guestbook update(Guestbook bean, Integer typeId, String IP) {
		return null;
	}
}
