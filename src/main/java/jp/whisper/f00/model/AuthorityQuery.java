package jp.whisper.f00.model;

import jp.whisper.common.dao.BaseQuery;

public class AuthorityQuery extends BaseQuery
{
    /**
    *テーブル名
    */
    public final static String TABLE_NAME ="AUTHORITIES";
    /**
    *コード
    */
    private java.lang.String code;
    /**
    *メニュー名称
    */
    private java.lang.String displayName;
    /**
    *URL,#@AUTH_TYPEが0（権限）だった場合、「/」になる＠AUTH_TYPEが1（メニュー）だった場合、「/XXX/XX.jp」なのようになる
    */
    private java.lang.String authUrl;
    /**
    *タイプ,#@0:権限@1:メニュー
    */
    private java.lang.String authType;
    /**
    *並び順
    */
    private java.lang.Long authIndex;
    /**
    *親ID
    */
    private java.lang.Long parent;
    /**
    *備考
    */
    private java.lang.String remark;

    //TODO
}
