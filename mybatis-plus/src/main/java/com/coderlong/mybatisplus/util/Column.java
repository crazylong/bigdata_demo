package com.coderlong.mybatisplus.util;

public class Column {
	
    public final int columnNoNulls = 0;
 
    public final int columnNullable = 1;
 
    public final int columnNullableUnknown = 2;
    
	@Override
	public String toString() {
		return "Column [tableCat=" + tableCat + ", tableSchem=" + tableSchem + ", columnName=" + columnName
				+ ", dataType=" + dataType + ", typeName=" + typeName + ", columnSize=" + columnSize + ", nullable="
				+ nullable + ", remarks=" + remarks + ", autoincrement=" + autoincrement + "]";
	}
	/**
	 * 表类别
	 */
	private String tableCat;
	/**
	 * 表模式
	 */
	private String tableSchem;
	/**
	 * 列名称
	 */
	private String columnName;
	/**
	 * 来自 java.sql.Types 的 SQL 类型
	 */
	private int dataType;
	/**
	 * 数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的
	 */
	private String typeName;
	/**
	 * 列的大小
	 */
	private int columnSize;
	/**
	 * 是否允许使用 NULL。
	 * columnNoNulls - 可能不允许使用 NULL 值
	 * columnNullable - 明确允许使用 NULL 值
	 * columnNullableUnknown - 不知道是否可使用 null
	 */
	private int nullable;
	/**
	 * 描述列的注释（可为 null）
	 */
	private String remarks;
	/**
	 * 指示此列是否自动增加
	 * YES --- 如果该列自动增加
	 * NO --- 如果该列不自动增加
	 */
	private String autoincrement;
	
	
	public String getTableCat() {
		return tableCat;
	}
	public void setTableCat(String tableCat) {
		this.tableCat = tableCat;
	}
	public String getTableSchem() {
		return tableSchem;
	}
	public void setTableSchem(String tableSchem) {
		this.tableSchem = tableSchem;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getColumnSize() {
		return columnSize;
	}
	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}
	public int getNullable() {
		return nullable;
	}
	public void setNullable(int nullable) {
		this.nullable = nullable;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAutoincrement() {
		return autoincrement;
	}
	public void setAutoincrement(String autoincrement) {
		this.autoincrement = autoincrement;
	}
}
