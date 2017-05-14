package jp.whisper.common.tool.scaffold;

public class FieldInfo
{
	private String	type;
	private String	name;
	private Object	defaultValue;	// 項目デフォルト値
	private String	columnName;	// 列名
	private String	columnType;	// 列のタイプ
	private OpType	op;			// デフォルトオペレーションん：“insert”,"update","all"
	private String	columnExplain;
	private boolean isRequired = true;

	public FieldInfo(String type, String name)
	{
		this.type = type;
		this.name = name;
	}


	public FieldInfo(String type, String name, String columnExplain)
	{
		this.type = type;
		this.name = name;
		this.columnExplain = columnExplain;
	}


	public FieldInfo(String columnName, String columnType, Object defaultValue, String name, OpType op, String type)
	{
		this.columnName = columnName;
		this.columnType = columnType;
		this.defaultValue = defaultValue;
		this.name = name;
		this.op = op;
		this.type = type;
	}

	public FieldInfo(String columnName, String columnType, Object defaultValue, String name, OpType op, String type, boolean isRequired){
		this.columnName = columnName;
		this.columnType = columnType;
		this.defaultValue = defaultValue;
		this.name = name;
		this.op = op;
		this.type = type;
		this.isRequired = isRequired;
	}

	public String getType()
	{
		return type;
	}


	public void setType(String type)
	{
		this.type = type;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public Object getDefaultValue()
	{
		return defaultValue;
	}


	public void setDefaultValue(Object defaultValue)
	{
		this.defaultValue = defaultValue;
	}


	public String getColumnName()
	{
		return columnName;
	}


	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}


	public OpType getOp()
	{
		return op;
	}


	public void setOp(OpType op)
	{
		this.op = op;
	}


	public String getColumnType()
	{
		return columnType;
	}


	public void setColumnType(String columnType)
	{
		this.columnType = columnType;
	}

	public enum OpType
	{
		INSERT, UPDATE, DELETE
	}


	/**
	 * @return the columnExplain
	 */
	public String getColumnExplain()
	{
		return columnExplain;
	}


	/**
	 * @param columnExplain
	 *            the columnExplain to set
	 */
	public void setColumnExplain(String columnExplain)
	{
		this.columnExplain = columnExplain;
	}


	public boolean isRequired()
	{
		return isRequired;
	}


	public void setRequired(boolean isRequired)
	{
		this.isRequired = isRequired;
	}

}
