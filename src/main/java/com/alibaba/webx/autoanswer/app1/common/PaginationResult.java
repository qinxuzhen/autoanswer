package com.alibaba.webx.autoanswer.app1.common;


public class PaginationResult<T> extends Result<T> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4885442395211589380L;
	
	private Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    /**
     * 设置分页的参数
     * 有,当前页,总数据量,每页显示条数,
     *
     * @param query
     * @param result
     */
    @SuppressWarnings("rawtypes")
	public void setPaginationForQuery(Query query,PaginationResult result) {
        Pagination pagination = new Pagination();
        pagination.setTotalCount(result.getTotal());
        pagination.setPageIndex(query.getPageIndex());
        pagination.setPageSize(query.getPageSize());
        result.setPagination(pagination);
    }

}