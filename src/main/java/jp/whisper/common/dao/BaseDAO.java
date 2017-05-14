package jp.whisper.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * DAOインタフェース
 *
 * @author whisper
 *
 * @param <T>
 */
public interface BaseDAO<T> {
	/**
	 * IDにより、レコードを取得する
	 */
	public abstract T get(Serializable id);

	/**
	 * すべてのレコードを取得する
	 */
	public abstract List<T> listAll();

	@SuppressWarnings("rawtypes")
	public abstract String getIdName(Class clazz);

	public abstract String getPrimaryKeyName();

	/**
	 * IDによってレコードを削除する
	 */
	public abstract void delete(Serializable id, String... otherArgs);

	/**
	 * UIDがNULLだった場合、新規；その以外は更新する
	 */
	public abstract void save(Object o);

	/**
	 * 新規。シーケンスによってUIDを設定し、登録する
	 *
	 * @param o
	 */
	public abstract void insert(Object o);

	/**
	 * シーケンスを使わず、レコードを新規する
	 *
	 * @param o
	 */
	public abstract void insertWithPrimaryKey(Object o);

	/**
	 * 更新
	 *
	 * @param o
	 */
	public abstract void update(Object o);

	public abstract void setPrimaryKeyName(String primaryKeyName);

	/**
	 * クエリ
	 *
	 * @param query
	 * @return
	 */
	public abstract List<T> query(BaseQuery query);

	/**
	 * カレントスレッドに使っているDBコネクションを取得する
	 *
	 * @return
	 */
//	public abstract Connection getCurrentConnection();

	/**
	 * マップ化した条件によって検索する
	 */
	public abstract List<T> findBy(Map<String, Object> map);

	/**
	 * マップ化した条件によって検索する（ワイルドカード型）
	 */
	public abstract List<T> findByLike(Map<String, Object> map);

	/**
	 * マップ化した条件によって唯一のレコードを抽出する
	 *
	 * @return 唯一レコード
	 */
	public abstract T findUniqueBy(Map<String, Object> map);

}
