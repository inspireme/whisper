package jp.whisper.common.dao;

import jp.whisper.common.model.BaseModel;

@SuppressWarnings("serial")
public class BaseQuery extends BaseModel
{
	/**
	 * default current pagenum
	 */
	private static final int	DEFAULT_CURRENT_PAGE	= 1;
	/**
	 * default pagesize
	 */
	private static final int	DEFAULT_PAGE_SIZE		= 20;
	/**
	 * current pagenum
	 */
	private int					currentPage				= DEFAULT_CURRENT_PAGE;
	/**
	 *  pagesize
	 */
	private int					pageSize				= DEFAULT_PAGE_SIZE;
	/**
	 *  the num of the total record
	 */
	private int					total;
	/**
	 * start
	 */
	private int					start;
	/**
	 * total page
	 */
	private int                 totalPage;
	/**
	 * name of the sort item
	 */
	private String sortname;
	/**
	 *  sort type（asc or desc）
	 */
	private String sortorder ="asc";

	public Integer getCurrentPage()
	{
		if (start == 0)
			return this.currentPage;
		if (start > 0)
		{
			this.currentPage = (this.start - 1) / this.pageSize + 1;
		}
		return currentPage;
	}


	public void setCurrentPage(Integer currentPage)
	{
		this.currentPage = currentPage;
	}


	public Integer getEnd()
	{
		int endRecord = 0;
		if (getTotal().intValue() == 0)
		{
			return endRecord;
		}
		endRecord = getPageSize() * getCurrentPage();
		if (endRecord > this.total)
		{
			endRecord = this.total;
		}
		this.totalPage = (total%pageSize) == 0 ? total / pageSize : total / pageSize+ 1;
		return endRecord;
	}


	public int getTotalPage()
	{
		return totalPage;
	}


	public void setTotalPage(int totalPage)
	{
		this.totalPage = totalPage;
	}


	public Integer getPageSize()
	{
		return pageSize;
	}


	public void setPageSize(Integer pageSize)
	{
		this.pageSize = pageSize;
	}


	public Integer getStart()
	{
		if (this.start > 0)
			return this.start;
		int startRecord = this.pageSize * (this.currentPage - 1) + 1;
		if (startRecord >= this.total)
		{
			startRecord = this.total;
		}
		this.start = startRecord;
		return this.start;
	}


	public void setStart(Integer start)
	{
		this.start = start;
	}


	public Integer getTotal()
	{
		return total;
	}


	public void setTotal(Integer total)
	{
		this.total = total;
	}

	/**
	 * @return the sortname
	 */
	public String getSortname()
	{
		return sortname;
	}


	/**
	 * @param sortname the sortname to set
	 */
	public void setSortname(String sortname)
	{
		this.sortname = sortname;
	}


	/**
	 * @return the sortorder
	 */
	public String getSortorder()
	{
		return sortorder;
	}


	/**
	 * @param sortorder the sortorder to set
	 */
	public void setSortorder(String sortorder)
	{
		this.sortorder = sortorder;
	}


	/**
	 * @return the defaultPageSize
	 */
	public static int getDefaultPageSize()
	{
		return DEFAULT_PAGE_SIZE;
	}


	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}


	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}


	/**
	 * @param total the total to set
	 */
	public void setTotal(int total)
	{
		this.total = total;
	}


	/**
	 * @param start the start to set
	 */
	public void setStart(int start)
	{
		this.start = start;
	}

}
