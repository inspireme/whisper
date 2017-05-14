package jp.whisper.f00.model;

import jp.whisper.common.model.BaseModel;

public class Role extends BaseModel
{
    /**
    *テーブル名
    */
    public final static String TABLE_NAME ="ROLES";
    /**
    *コード
    */
    private java.lang.String code;
    /**
    *名称
    */
    private java.lang.String name;
    /**
    *備考
    */
    private java.lang.String remark;
	public java.lang.String getCode() {
		return code;
	}
	public void setCode(java.lang.String code) {
		this.code = code;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
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

