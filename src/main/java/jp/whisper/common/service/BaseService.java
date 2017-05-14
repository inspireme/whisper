package jp.whisper.common.service;

import java.util.List;
import java.util.Map;

import jp.whisper.common.dao.BaseQuery;

public interface BaseService<T> {
	@SuppressWarnings("hiding")
	public <T> T get(Long id);

	public void create(T model);

	public void update(T model);

	public void save(T model);

	public List<T> listAll();

	public List<T> query(BaseQuery query);

	public void delete(Long id);

	public void deleteBatch(List<T> list);

	public void deleteBatch(String[] idArr);

	public List<T> findBy(Map<String, Object> map);

	public List<T> findByLike(Map<String, Object> map);

	public abstract T findUniqueBy(Map<String, Object> map);

	public void saveBatch(List<T> modelList);

}
