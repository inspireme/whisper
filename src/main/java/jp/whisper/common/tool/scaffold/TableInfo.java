package jp.whisper.common.tool.scaffold;

import java.util.ArrayList;
import java.util.List;

import jp.whisper.common.util.StringUtil;

public class TableInfo
{
	private static final String	ID		= "id";
	private final String		ENDL	= "\n";
	private final String		TAB		= "    ";
	private final String		TAB2	= TAB + TAB;
	private final String		TAB3	= TAB2 + TAB;
	private final String		TAB4	= TAB2 + TAB2;
	private String				name;
	private String				primaryKey;
	private List<ColumnInfo>	columns;
	private List<FieldInfo>		fields;


	public TableInfo(String name)
	{
		this.name = name;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public String getPrimaryKey()
	{
		return primaryKey;
	}


	public void setPrimaryKey(String primaryKey)
	{
		this.primaryKey = primaryKey;
	}


	public List<ColumnInfo> getColumns()
	{
		return columns;
	}


	public void setColumns(List<ColumnInfo> columns)
	{
		this.columns = columns;
	}


	public void addColumn(ColumnInfo column)
	{
		if (columns == null)
			columns = new ArrayList<ColumnInfo>();
		if (!columns.contains(column))
		{
			columns.add(column);
			String type = column.parseJavaType();
			String name = column.parseFieldName();
			String columnExplain=column.getColumnExplain();
			addFiled(new FieldInfo(type, name,columnExplain));
		}
	}


	public void addFiled(FieldInfo field)
	{
		if (fields == null)
			fields = new ArrayList<FieldInfo>();
		if (field.getName().equalsIgnoreCase(ID))
			return;
		if (!fields.contains(field))
		{
			fields.add(field);
		}

	}


	public List<FieldInfo> getFields()
	{
		return fields;
	}


	public String getFieldsDeclareInfo()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(TAB);
		sb.append("/**");
		sb.append(ENDL);
		sb.append(TAB);
		sb.append("*");
		sb.append("テーブル名");
		sb.append(ENDL);
		sb.append(TAB);
		sb.append("*/");
		sb.append(ENDL);
		sb.append(TAB);
		sb.append("public final static String TABLE_NAME =\"");
		sb.append(this.getName().toUpperCase());
		sb.append("\";");
		sb.append(ENDL);
		for (FieldInfo field : fields)
		{
			if (field.getName().equalsIgnoreCase(ID)){
				continue;// id property is in the BaseModel
			}
			if (ColumnInfo.DEFAULT_FIELDS.containsKey(field.getName()))
			{// properties are in the basemodel
				continue;
			}

			if(field.getColumnExplain()!=null&&!"".equals(field.getColumnExplain())){
				sb.append(TAB);
				sb.append("/**");
				sb.append(ENDL);
				sb.append(TAB);
				sb.append("*");
				sb.append(field.getColumnExplain());
				sb.append(ENDL);
				sb.append(TAB);
				sb.append("*/");
				sb.append(ENDL);
			}


			sb.append(TAB);
			sb.append("private ");
			sb.append(field.getType());
			sb.append(" ");
			sb.append(field.getName());
			sb.append(";");
			sb.append(ENDL);

		}
		return sb.toString();
	}


	public String getSelectStatement()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append(getColumnNames());
		sb.append(" FROM " + name.toUpperCase());
		return sb.toString();
	}


	public String getSelectCountStatement()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT count(" + primaryKey + ") ");
		sb.append(" FROM " + name.toUpperCase());
		return sb.toString();
	}


	public String getInsertStatement()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO " + name.toUpperCase());
		sb.append("( " + this.getColumnNames() + " ) VALUES (");
		ColumnInfo col = null;
		sb.append("#{id},");
		for (int i = 0; i < columns.size(); i++)
		{
			col = columns.get(i);
			String fileName = col.parseFieldName();
			if (ColumnInfo.DEFAULT_FIELDS.containsKey(fileName) && ColumnInfo.DEFAULT_FIELDS.get(fileName).getDefaultValue()!=null)
			{
				if (ColumnInfo.DEFAULT_FIELDS.get(fileName).getOp().equals(FieldInfo.OpType.INSERT)
						|| ColumnInfo.DEFAULT_FIELDS.get(fileName).getOp().equals(FieldInfo.OpType.DELETE))
				{
					sb.append(ColumnInfo.DEFAULT_FIELDS.get(fileName).getDefaultValue());
				}
				else
				{
					sb.append("null");
				}
			}
			else
			{
				sb.append("#{" + fileName);
//				if (!StringUtil.isBlank(col.parseJdbcType()))
//				{
//					sb.append(":" + col.parseJdbcType());
//				}
				sb.append("}");
			}
			if (i + 1 != columns.size())
			{
				sb.append(",");
			}
		}
		sb.append(" )");
		return sb.toString();
	}


	public String getUpdateStatement()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE " + name.toUpperCase() + " SET ");
		ColumnInfo col = null;
		for (int i = 0; i < columns.size(); i++)
		{
			col = columns.get(i);
			String fileName = col.parseFieldName();
			if (ColumnInfo.DEFAULT_FIELDS.containsKey(fileName))
			{
				if (ColumnInfo.DEFAULT_FIELDS.get(fileName).getOp().equals(FieldInfo.OpType.UPDATE)
						&& ColumnInfo.DEFAULT_FIELDS.get(fileName).getDefaultValue()!=null)
				{
					if (i != 0)
					{
						sb.append(",");
					}
					sb.append(col.getName() + "= " + ColumnInfo.DEFAULT_FIELDS.get(fileName).getDefaultValue());
				}
				else
				{
					// sb.append("null");
				}
			}
			else
			{
				if (i != 0)
				{
					sb.append(",");
				}
				sb.append(col.getName() + "=#{" + fileName);
//				if (!StringUtil.isBlank(col.parseJdbcType()))
//				{
//					sb.append(":" + col.parseJdbcType());
//				}
				sb.append("}");
			}
		}
		sb.append(" WHERE " + primaryKey + "=#{id}");
		return sb.toString();
	}


	public String getResultMap()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(TAB3);
		sb.append("<result property=\"id\"");
		sb.append(" column=\"" + primaryKey + "\"");
		sb.append(" jdbcType=\"NUMERIC\" />");
		sb.append(ENDL);
		for (ColumnInfo col : columns)
		{
			sb.append(TAB3);
			sb.append("<result property=\"" + col.parseFieldName() + "\" column=\"" + col.getName());
			if (!StringUtil.isBlank(col.parseJdbcType()))
			{
				sb.append("\" jdbcType=\"" + col.parseJdbcType());
			}
			sb.append("\" />");

			sb.append(ENDL);
		}
		return sb.toString();
	}


	public String getOtherCondition()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(TAB3);
		sb.append("<if test=\"id != null\"> and ");
		sb.append(getPrimaryKey() + "=#{id}</if>");
		sb.append(ENDL);
		for (ColumnInfo col : columns)
		{
//			if (col.getType().contains("CHAR"))
//			{// 字串类型才有like比较
//				sb.append(TAB4);
//				sb.append("<isNotNull prepend=\"and\" property=\"" + col.parseFieldName() + "\"> " + col.getName()
//						+ " like '%'||#" + col.parseFieldName() + "#||'%'</isNotNull>");
//				sb.append(ENDL);
//			}else{
				sb.append(TAB3);
				sb.append("<if test=\"" + col.parseFieldName() + " != null\"> and ");
				sb.append(" " + col.getName() + " = #{" + col.parseFieldName());
//				if (!StringUtil.isBlank(col.parseJdbcType()))
//				{
//					sb.append(":" + col.parseJdbcType());
//				}
				sb.append("}");
				sb.append("</if>");
				sb.append(ENDL);
//			}

		}
		return sb.toString();
	}


	public String getFindByLike()
	{
		StringBuffer sb = new StringBuffer();
		for (ColumnInfo col : columns)
		{
			if (col.getType().contains("CHAR"))
			{
				sb.append(TAB4);
				sb.append("<if test=\"" + col.parseFieldName() + " != null \"> and " + col.getName()
						+ " like '%#{" + col.parseFieldName() + "}%'</if>");
				sb.append(ENDL);
			}
//			if (col.parseFieldName().equalsIgnoreCase(ColumnInfo.PARTITION_FIELD))
//			{
//				sb.append(TAB4);
//				sb.append("<if test=\"" + col.parseFieldName() + " != null \">");
//				sb.append(" " + col.getName() + " = #{" + col.parseFieldName());
////				if (!StringUtil.isBlank(col.parseJdbcType()))
////				{
////					sb.append(":" + col.parseJdbcType());
////				}
//				sb.append("}");
//				sb.append("</if>");
//				sb.append(ENDL);
//			}
		}
		return sb.toString();
	}


	private String getColumnNames()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(primaryKey + ",");
		ColumnInfo column = null;
		for (int i = 0; i < columns.size(); i++)
		{
			column = columns.get(i);
			sb.append(column.getName());
			if (i + 1 != columns.size())
			{
				sb.append(",");
			}
		}
		return sb.toString();
	}


	public String getUpdateMapModel()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE " + name.toUpperCase() + " SET update_time=systimestamp "
				+ ENDL);
		sb.append(TAB + "<set>" + ENDL);
		ColumnInfo col = null;
		for (int i = 0; i < columns.size(); i++)
		{
			col = columns.get(i);
			String fileName = col.parseFieldName();
			if (ColumnInfo.DEFAULT_FIELDS.containsKey(fileName))
			{
				if (ColumnInfo.DEFAULT_FIELDS.get(fileName).getOp().equals(FieldInfo.OpType.UPDATE))
				{

				}
			}
			else
			{
				sb.append(TAB2 + "<if test=\"" + col.parseFieldName() + " != null \">");
				sb.append( col.getName() + "=#{" + col.parseFieldName());

//				if (!StringUtil.isBlank(col.parseJdbcType()))
//				{
//					sb.append(":" + col.parseJdbcType());
//				}
				sb.append("}," );
				sb.append( "</if>" + ENDL);

				sb.append(TAB2 + "<if test=\"" + col.parseFieldName() + " == null \">" );
				sb.append(col.getName() + "=null ," );
				sb.append("</if>" + ENDL);
			}
		}
		sb.append(TAB + "</set>" + ENDL);
		sb.append(TAB2 + " WHERE " + primaryKey + "=#{id}");
		return sb.toString();
	}
}
