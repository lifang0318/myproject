package com.rareboom.member.service.model;

import java.io.Serializable;

/**
 * 基础pojo
 * 
 */
public class CoreModel<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String core;
	private String message;

	private T data;

	/**
	 * 当前页 (currentPage-1)*pageSize，1*pageSize
	 */
	private Integer currentPage = 1;

	/**
	 * 每页显示记录数
	 */
	private Integer pageSize=3;

	/**
	 * 总页数
	 */
	private Integer totalPageNumber;

	/**
	 * 总条数
	 */
	private Integer totalItemNumber;

	public String getCore() {
		return core;
	}

	public void setCore(String core) {
		this.core = core;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalPageNumber() {
		return totalPageNumber;
	}

	public void setTotalPageNumber(Integer totalPageNumber) {
		this.totalPageNumber = totalPageNumber;
	}

	public Integer getTotalItemNumber() {
		return totalItemNumber;
	}

	public void setTotalItemNumber(Integer totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
