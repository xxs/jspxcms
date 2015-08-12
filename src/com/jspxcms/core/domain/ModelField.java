package com.jspxcms.core.domain;

import java.util.HashMap;
import java.util.Map;

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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * ModelField
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_model_field")
public class ModelField implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 文本输入框
	 */
	public static final int TEXT = 1;
	/**
	 * 日期输入框
	 */
	public static final int DATE = 2;
	/**
	 * 复选框
	 */
	public static final int CHECKBOX = 3;
	/**
	 * 单选框
	 */
	public static final int RADIO = 4;
	/**
	 * 下拉框
	 */
	public static final int SELECT = 5;
	/**
	 * 文本区
	 */
	public static final int TEXTAREA = 6;
	/**
	 * 图片上传
	 */
	public static final int IMAGE = 7;
	/**
	 * 视频上传
	 */
	public static final int VIDEO = 8;
	/**
	 * 文件上传
	 */
	public static final int FILE = 9;
	/**
	 * 文本编辑器
	 */
	public static final int EDITOR = 50;
	/**
	 * 图片集上传
	 */
	public static final int IMAGES = 51;
	/**
	 * 附件集上传
	 */
	public static final int FILES = 52;
	/**
	 * 可查询的单选框
	 */
	public static final int Q_RADIO = 100;
	/**
	 * 可查询的下拉框
	 */
	public static final int Q_SELECT = 101;
	/**
	 * 可查询的复选框
	 */
	public static final int Q_CHECKBOX = 102;
	/**
	 * 用户自定义字段
	 */
	public static final int FIELD_CUSTOM = 0;
	/**
	 * 用户自定义大字段
	 */
	public static final int FIELD_CUSTOM_CLOB = 1;
	/**
	 * 系统定义字段
	 */
	public static final int FIELD_SYSTEM = 2;
	/**
	 * 预留可查询字段
	 */
	public static final int FIELD_QUERYABLE = 3;

	public static final String IMAGE_WIDTH = "imageWidth";
	public static final String IMAGE_HEIGHT = "imageHeight";

	/**
	 * 是否预定义
	 * 
	 * @return
	 */
	@Transient
	public boolean isPredefined() {
		if (getInnerType() != null) {
			return getInnerType() != FIELD_CUSTOM
					&& getInnerType() != FIELD_CUSTOM_CLOB;
		} else {
			return false;
		}
	}

	/**
	 * 是否自定义大字段
	 * 
	 * @return
	 */
	@Transient
	public boolean isClob() {
		if (getInnerType() != null) {
			return getInnerType() == FIELD_CUSTOM_CLOB;
		} else {
			return false;
		}
	}

	/**
	 * 是否自定义
	 * 
	 * @return
	 */
	@Transient
	public boolean isCustom() {
		return !isPredefined();
	}

	public void applyDefaultValue() {
		if (getInnerType() == null) {
			setInnerType(FIELD_CUSTOM);
		}
		if (getSeq() == null) {
			setSeq(Integer.MAX_VALUE);
		}
		if (getDblColumn() == null) {
			setDblColumn(false);
		}
		if (getRequired() == null) {
			setRequired(false);
		}
		if (getDisabled() == null) {
			setDisabled(false);
		}
		if (getTab() == null) {
			setTab(false);
		}
	}

	private Integer id;
	private Map<String, String> customs = new HashMap<String, String>(0);

	private Model model;
	
	private Integer type;
	private Integer innerType;
	private String label;
	private String name;
	private String prompt;
	private String defValue;
	private Boolean required;
	private Integer seq;
	private Boolean dblColumn;
	private Boolean disabled;
	private Boolean tab;

	public ModelField() {
	}

	public ModelField(Model model, Integer type, String label, String name) {
		this.model = model;
		this.type = type;
		this.label = label;
		this.name = name;
	}

	@Id
	@Column(name = "f_modefiel_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_model_field", pkColumnValue = "cms_model_field", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_model_field")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ElementCollection
	@CollectionTable(name = "cms_model_field_custom", joinColumns = @JoinColumn(name = "f_modefiel_id"))
	@MapKeyColumn(name = "f_key", length = 50)
	@Column(name = "f_value", length = 2000)
	public Map<String, String> getCustoms() {
		return this.customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_model_id", nullable = false)
	public Model getModel() {
		return this.model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	@Column(name = "f_type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "f_inner_type", nullable = false)
	public Integer getInnerType() {
		return this.innerType;
	}

	public void setInnerType(Integer innerType) {
		this.innerType = innerType;
	}

	@Column(name = "f_label", nullable = false, length = 50)
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "f_name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "f_prompt")
	public String getPrompt() {
		return this.prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	@Column(name = "f_def_value")
	public String getDefValue() {
		return this.defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	@Column(name = "f_is_required", nullable = false, length = 1)
	public Boolean getRequired() {
		return this.required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	@Column(name = "f_seq", nullable = false)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Column(name = "f_is_dbl_column", nullable = false, length = 1)
	public Boolean getDblColumn() {
		return this.dblColumn;
	}

	public void setDblColumn(Boolean dblColumn) {
		this.dblColumn = dblColumn;
	}

	@Column(name = "f_is_disabled", nullable = false, length = 1)
	public Boolean getDisabled() {
		return disabled;
	}
	
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
	@Column(name = "f_is_tab", nullable = false, length = 1)
	public Boolean getTab() {
		return tab;
	}

	public void setTab(Boolean tab) {
		this.tab = tab;
	}
}
