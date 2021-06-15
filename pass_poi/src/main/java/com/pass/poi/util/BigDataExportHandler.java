package com.pass.poi.util;

import org.springframework.jdbc.core.RowCallbackHandler;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: yuanzhonglin
 * @Date: 2021/6/15
 * @Description:
 * 1.当前栗子，导出数据为csv格式，手动导入excel
 * 2.RowCallbackHandler这个回调接口是指每一条数据遍历后要执行的回调函数
 */
public class BigDataExportHandler implements RowCallbackHandler {

    private PrintWriter pw;

    public BigDataExportHandler(PrintWriter pw){
        this.pw = pw;
    }

    @Override
    public void processRow(ResultSet rs) throws SQLException {
        if (rs.isFirst()){
            rs.setFetchSize(500);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++){
                if (i == rs.getMetaData().getColumnCount() - 1){
                    this.writeToFile(pw, rs.getMetaData().getColumnName(i+1), true);
                }else{
                    this.writeToFile(pw, rs.getMetaData().getColumnName(i+1), false);
                }
            }
        }else{
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++){
                if (i == rs.getMetaData().getColumnCount() - 1){
                    this.writeToFile(pw, rs.getObject(i+1), true);
                }else{
                    this.writeToFile(pw, rs.getObject(i+1), false);
                }
            }
        }
        pw.println();
    }

    private void writeToFile(PrintWriter pw, Object valueObj, boolean isLineEnd){

    }
}
