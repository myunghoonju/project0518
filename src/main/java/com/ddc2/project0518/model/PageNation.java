package com.ddc2.project0518.model;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageNation {
	
	private int total_count;
	private int start_page;
	private int end_page;
	private boolean prev;
	private boolean next;
	
	private int page; 
	private int perPageNum;
	private int row_start; 
	private int row_end;
	
	
	public PageNation() {
		this.page = 1;
		this.perPageNum = 8;
	}
	
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
		calcData();
	}
	public int getStart_page() {
		return start_page;
	}
	public void setStart_page(int start_page) {
		this.start_page = start_page;
	}
	public int getEnd_page() {
		return end_page;
	}
	public void setEnd_page(int end_page) {
		this.end_page = end_page;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	
	
	private void calcData() {
		end_page = start_page + 1;
		start_page = page; //밑에 1부터 표시
		
		int tempEndpage = (int)(Math.ceil(total_count /(double)perPageNum));//마지막 페이지 눌렀을때 
		if(end_page > tempEndpage) {
			end_page = tempEndpage;
		}
		
		prev = start_page == 1 ? false : true; // start_page ==1 prev = false(None exist)
		next = end_page >= total_count ? false : true; // 7*7 >= 7 false(다음없음)
		
	}
	
	public void setPage(int page) {
			this.page = page;
	}
	public void setRow_start(int row_start) {
		this.row_start = row_start;
	}

	public void setRow_end(int row_end) {
		this.row_end = row_end;
	}
	
	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}
	
	public int getRow_start() {
		return (this.page-1)  ;
	}
	public int getRow_end() {
		row_end = row_start + perPageNum;
		return row_end;
	}
	
	public void setRowStart(int row_start) {
		this.row_start = row_start;
	}

	public int getPage() {
		return page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}
	

	@Override
	public String toString() {
		return "[page= " + page + ", perPageNum=" +perPageNum + 
				",row_start" + row_start + ",row_end" + row_end + "]";
	}

	public String make_query(int page) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
										.queryParam("page", page)
										.queryParam("perPageNum", perPageNum)
										.build();
		return uriComponents.toUriString();
		
	}
	
}
