package jp.whisper.common.model;

import java.io.Serializable;

/**
 * DTOの親モード
 * @author whisper
 *
 */
public class BaseModel implements Serializable {
	private static final long serialVersionUID = 1044939527480102855L;
	/**
	 * UID
	 */
	protected Long id;
	/**
	 * 新規日付
	 */
	protected java.sql.Timestamp createTime;
	/**
	 * 更新日付
	 */
	protected java.sql.Timestamp updateTime;
	/**
	 * 削除フラグ
	 */
	protected String deleteFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.sql.Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}

	public java.sql.Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.sql.Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {

		this.deleteFlag = deleteFlag;
	}

}
