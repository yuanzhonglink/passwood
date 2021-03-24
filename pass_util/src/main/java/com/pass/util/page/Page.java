package com.pass.util.page;


import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanzhonglin
 * @date 2021/3/23
 */
public class Page {
    private Integer currentPage;//当前页
    private int pageSize;//每页显示记录条数
    private int totalPage;//总页数
    private List<?> dataList;//每页显示的数据
    private int star;//开始数据

    public Integer getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public List<?> getDataList() {
        return dataList;
    }
    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }
    public int getStar() {
        return star;
    }
    public void setStar(int star) {
        this.star = star;
    }

    public String fen(Page page, HttpServletRequest request){

        try {
            //查询出的list数据
            List<Data> showdata= new ArrayList<>();
            //刚开始的页面为第一页
            if (page.getCurrentPage() == null){
                page.setCurrentPage(1);
            } else {
                page.setCurrentPage(page.getCurrentPage());
            }
            //设置每页数据为十条
            page.setPageSize(10);
            //每页的开始数
            page.setStar((page.getCurrentPage() - 1) * page.getPageSize());
            //list的大小
            int count = showdata.size();
            //设置总页数
            page.setTotalPage(count % 10 == 0 ? count / 10 : count / 10 + 1);
            //对list进行截取
            page.setDataList(showdata.subList(page.getStar(),count-page.getStar()>page.getPageSize()?page.getStar()+page.getPageSize():count));
            //设置作用域
            request.setAttribute("paging", page);
            return "add/show.jsp";

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "mistake.jsp";
        }

    }
}



