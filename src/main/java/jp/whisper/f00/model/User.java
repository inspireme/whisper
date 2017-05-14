package jp.whisper.f00.model;

import jp.whisper.common.model.BaseModel;

public class User extends BaseModel
{
    /**
    *テーブル名
    */
    public final static String TABLE_NAME ="USERS";
    /**
    *ログインユーザー
    */
    private java.lang.String loginId;
    /**
    *パスワード
    */
    private java.lang.String password;
    /**
    *名前
    */
    private java.lang.String name;
    /**
    *メールアドレス
    */
    private java.lang.String email;
    /**
    *TEL
    */
    private java.lang.String phone;
    /**
    *携帯
    */
    private java.lang.String mobile;
    /**
    *事業部門
    */
    private java.lang.String department;

	public java.lang.String getLoginId() {
		return loginId;
	}
	public void setLoginId(java.lang.String loginId) {
		this.loginId = loginId;
	}
	public java.lang.String getPassword() {
		return password;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.String getEmail() {
		return email;
	}
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	public java.lang.String getPhone() {
		return phone;
	}
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	public java.lang.String getMobile() {
		return mobile;
	}
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	public java.lang.String getDepartment() {
		return department;
	}
	public void setDepartment(java.lang.String department) {
		this.department = department;
	}

}

