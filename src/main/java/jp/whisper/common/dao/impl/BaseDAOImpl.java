package jp.whisper.common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.orm.ObjectRetrievalFailureException;

import jp.whisper.common.dao.BaseDAO;
import jp.whisper.common.dao.BaseQuery;
import jp.whisper.common.util.GenericsUtil;

public class BaseDAOImpl<T> extends IBatisGenericDAO implements BaseDAO<T>
{
	/**
	 * Entityのジェネリック
	 */
	protected Class<T>	entityClass;

	protected String	primaryKeyName;

	protected String	sqlMapNamespace;

	@SuppressWarnings("unchecked")
	public BaseDAOImpl()
	{
		entityClass = GenericsUtil.getSuperClassGenricType(getClass());
		//sqlMapNamespace = this.getClass().getInterfaces()[0].getName();
		sqlMapNamespace = entityClass.getName()+"Mapper";
	}


	@Override
	public String getSqlMapNamespace()
	{
		return sqlMapNamespace;
	}


	public void setSqlMapNamespace(String sqlMapNamespace)
	{
		this.sqlMapNamespace = sqlMapNamespace;
	}


	public List<T> findBy(Map<String, Object> map)
	{
		return find(getEntityClass(), map);
	}


	public T get(Serializable id)
	{
		return get(getEntityClass(), id);
	}

	public List<T> listAll()
	{
		return getAll(getEntityClass());
	}


	/**
	 * entityClassを取得する
	 * <p/>
	 */
	protected Class<T> getEntityClass()
	{
		return entityClass;
	}


	public String getIdName(@SuppressWarnings("rawtypes") Class clazz)
	{
		return "id";
	}


	public String getPrimaryKeyName()
	{
		if (StringUtils.isEmpty(primaryKeyName))
			primaryKeyName = "id";
		return primaryKeyName;
	}


	protected Object getPrimaryKeyValue(Object o) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, InstantiationException
	{
		return PropertyUtils.getProperty(entityClass.cast(o), getPrimaryKeyName());
	}


	public void delete(Serializable id, String...otherArgs )
	{
		removeById(getEntityClass(), id);
	}

/**
	public void delete(Object o)
	{
		remove(o);
	}

**/
	public void save(Object o)
	{
		this.save(o, false);
	}


	public void save(Object o, boolean isupdateMC)
	{
		Object primaryKey;
		try
		{
			primaryKey = getPrimaryKeyValue(o);
		}
		catch (Exception e)
		{
			throw new ObjectRetrievalFailureException(entityClass, e);
		}

		if (primaryKey == null)
			insert(o);
		else
			update(o, isupdateMC);
	}


	public void setPrimaryKeyName(String primaryKeyName)
	{
		this.primaryKeyName = primaryKeyName;
	}


	public List<T> query(BaseQuery query)
	{
		return super.query(getEntityClass(), query);
	}

	public List<T> findByLike(Map<String, Object> map)
	{
		return super.findByLike(entityClass, map);
	}


	public T findUniqueBy(Map<String, Object> map)
	{
		return super.findUniqueBy(entityClass, map);
	}

	public void updatePartialModel(Object o)
	{
		super.updatePartialModel(o);
	}

	public void updatePartialModel(Object o, String[] aryNullFields)
	{
		super.updatePartialModel(o, aryNullFields);
	}

	public Map<String, T> findMapBy(String key, Map<String, Object> map)
	{
		return super.findMapBy(entityClass, key, map);
	}


	public Map<String, T> findMapByLike(String key, Map<String, Object> map)
	{
		return super.findMapByLike(entityClass, key, map);
	}
}
