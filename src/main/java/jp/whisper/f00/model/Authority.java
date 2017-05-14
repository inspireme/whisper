package jp.whisper.f00.model;

import jp.whisper.common.model.BaseModel;

public class Authority extends BaseModel {
	/**
	 * テーブル名
	 */
	public final static String TABLE_NAME = "AUTHORITIES";
	/**
	 * コード
	 */
	private java.lang.String code;
	/**
	 * メニュー名称
	 */
	private java.lang.String displayName;
	/**
	 * URL,#@AUTH_TYPEが0（権限）だった場合、「/」になる＠AUTH_TYPEが1（メニュー）だった場合、「/XXX/XX.jp」なのようになる
	 */
	private java.lang.String authUrl;
	/**
	 * タイプ,#@0:権限@1:メニュー
	 */
	private java.lang.String authType;
	/**
	 * 並び順
	 */
	private java.lang.Long authIndex;
	/**
	 * 親ID
	 */
	private java.lang.Long parent;
	/**
	 * 備考
	 */
	private java.lang.String remark;

	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public java.lang.String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(java.lang.String displayName) {
		this.displayName = displayName;
	}

	public java.lang.String getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(java.lang.String authUrl) {
		this.authUrl = authUrl;
	}

	public java.lang.String getAuthType() {
		return authType;
	}

	public void setAuthType(java.lang.String authType) {
		this.authType = authType;
	}

	public java.lang.Long getAuthIndex() {
		return authIndex;
	}

	public void setAuthIndex(java.lang.Long authIndex) {
		this.authIndex = authIndex;
	}

	public java.lang.Long getParent() {
		return parent;
	}

	public void setParent(java.lang.Long parent) {
		this.parent = parent;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public static String getTableName() {
		return TABLE_NAME;
	}

}
