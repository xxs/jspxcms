package com.jspxcms.ext.service;

import com.jspxcms.ext.domain.Guestbook;

/**
 * GuestbookConfService
 * 
 * @author yangxing
 * 
 */
public interface GuestbookConfService {
	public Guestbook update(Guestbook bean,Integer typeId, String IP);
}
