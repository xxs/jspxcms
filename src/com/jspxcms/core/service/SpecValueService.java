package com.jspxcms.core.service;

import com.jspxcms.core.domain.Spec;
import com.jspxcms.core.domain.SpecValue;

public interface SpecValueService {
	public SpecValue get(Integer id);

	public SpecValue[] save(String[] name, String[] image, Spec spec);

	public SpecValue[] update(Integer[] id, String[] name, String[] image,
			Spec spec);

	public SpecValue delete(SpecValue bean);
}
