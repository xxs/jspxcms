package com.jspxcms.core.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * Model
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_model")
public class Model implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public Set<String> getPredefinedNames() {
		Set<String> names = new HashSet<String>();
		for (ModelField field : getFields()) {
			if (field.isPredefined()) {
				names.add(field.getName());
			}
		}
		return names;
	}

	@Transient
	public List<ModelField> getEnabledFields() {
		List<ModelField> fields = getFields();
		List<ModelField> enabledFields = new ArrayList<ModelField>();
		for (ModelField field : fields) {
			if (!field.getDisabled()) {
				enabledFields.add(field);
			}
		}
		return enabledFields;
	}

	@Transient
	public ModelField getField(String name) {
		for (ModelField field : getFields()) {
			if (field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}

	public void addField(ModelField field) {
		List<ModelField> fields = getFields();
		if (fields == null) {
			fields = new ArrayList<ModelField>();
			setFields(fields);
		}
		fields.add(field);
	}

	public void applyDefaultValue() {
		if (getSeq() == null) {
			setSeq(Integer.MAX_VALUE);
		}
	}

	private Integer id;
	private List<ModelField> fields = new ArrayList<ModelField>(0);
	private Map<String, String> customs = new HashMap<String, String>(0);

	private Site site;
	
	private String type;
	private String name;
	private Integer seq;

	public Model() {
	}

	@Id
	@Column(name = "f_model_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_model", pkColumnValue = "cms_model", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_model")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "model")
	@OrderBy(value = "seq asc, id asc")
	public List<ModelField> getFields() {
		return this.fields;
	}

	public void setFields(List<ModelField> fields) {
		this.fields = fields;
	}

	@ElementCollection
	@CollectionTable(name = "cms_model_custom", joinColumns = @JoinColumn(name = "f_model_id"))
	@MapKeyColumn(name = "f_key", length = 50)
	@Column(name = "f_value", length = 2000)
	public Map<String, String> getCustoms() {
		return this.customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_site_id", nullable = false)
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name = "f_type", nullable = false, length = 100)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "f_name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "f_seq", nullable = false)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}
