package jp.whisper.common.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import jp.whisper.common.dao.BaseDAO;
import jp.whisper.common.dao.BaseQuery;
import jp.whisper.common.model.BaseModel;
import jp.whisper.common.service.BaseService;
import jp.whisper.common.service.BusinessException;
import jp.whisper.common.util.GenericsUtil;

public class BaseServiceImpl<T> implements BaseService<T> {
	/**
	 * ジェネリッククラスタイプ
	 */
	protected Class<T> entityClass;

	protected String primaryKeyName;

	public BaseDAO<T> baseDAO;

	protected List<String> getUserAuthorities() {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		List<String> list = null;
		@SuppressWarnings("unchecked")
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) currentUser.getAuthorities();
		if (authorities != null && authorities.size() > 0) {
			list = new ArrayList<String>(authorities.size());
			for (int i = 0; i < authorities.size(); i++) {
				GrantedAuthority g = authorities.get(i);
				String a = g.getAuthority();
				list.add(a);
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		entityClass = GenericsUtil.getSuperClassGenricType(getClass());
	}

	public String getPrimaryKeyName() {
		if (StringUtils.isEmpty(primaryKeyName))
			primaryKeyName = "id";
		return primaryKeyName;
	}

	protected Object getPrimaryKeyValue(Object o)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		return PropertyUtils.getProperty(entityClass.cast(o), getPrimaryKeyName());
	}

	public BaseDAO<T> getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO<T> dao) {
		this.baseDAO = dao;
	}

	@Transactional
	public void delete(Long id) {
		baseDAO.delete(id);
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Transactional(readOnly = true)
	public <T> T get(Long id) {
		return (T) baseDAO.get(id);
	}

	@Transactional(readOnly = true)
	public List<T> listAll() {
		return baseDAO.listAll();
	}

	@Transactional(readOnly = true)
	public List<T> query(BaseQuery query) {
		return baseDAO.query(query);
	}

	@Transactional(readOnly = false, rollbackFor = BusinessException.class)
	public void save(T model) {
		baseDAO.save(model);
	}

	@Transactional(readOnly = false, rollbackFor = BusinessException.class)
	public void create(T model) {
		Object primaryKey;
		try {
			primaryKey = getPrimaryKeyValue(model);
		} catch (Exception e) {
			throw new ObjectRetrievalFailureException(entityClass, e);
		}

		if (primaryKey == null) {
			baseDAO.insert(model);
		} else {
			baseDAO.insertWithPrimaryKey(model);
		}

	}

	@Transactional(readOnly = false, rollbackFor = BusinessException.class)
	public void update(T model) {
		baseDAO.update(model);
	}

	@Transactional(readOnly = true)
	public List<T> findBy(Map<String, Object> map) {
		return baseDAO.findBy(map);
	}

	@Transactional(readOnly = false, rollbackFor = BusinessException.class)
	public void deleteBatch(List<T> list) {
		if (list == null || list.size() == 0)
			return;
		for (T t : list) {
			delete(((BaseModel) t).getId());
		}
	}

	@Transactional(readOnly = false, rollbackFor = BusinessException.class)
	public void deleteBatch(String[] idArr) {
		if (idArr == null || idArr.length == 0)
			return;
		for (String t : idArr) {
			delete(Long.parseLong(t));
		}
	}

	@Transactional(readOnly = true)
	public List<T> findByLike(Map<String, Object> map) {
		return this.baseDAO.findByLike(map);
	}

	@Transactional(readOnly = true)
	public T findUniqueBy(Map<String, Object> map) {
		return this.baseDAO.findUniqueBy(map);
	}

	@Transactional(readOnly = false, rollbackFor = BusinessException.class)
	@Override
	public void saveBatch(List<T> modelList) {
		if (CollectionUtils.isNotEmpty(modelList)) {
			for (T t : modelList) {
				this.baseDAO.save(t);
			}
		}
	}

}

// @Transactinal(
// propagation=Propagation.REQUIRED,
// isolation=Isolation.READ_COMMITED,
// timeout=10, readOnly=false,
// rollbackFor=BusinessException.class )