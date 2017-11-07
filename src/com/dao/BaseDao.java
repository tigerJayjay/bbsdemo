package com.dao;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.util.DBUtil;

public class BaseDao {
	protected Connection conn = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;
	private static ComboPooledDataSource  dataSource = null;
	
	static{
		dataSource = new ComboPooledDataSource();
	}
	
	private void openConn(){
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 通用更新
	 */
	public Integer update(String sql,Object[] objs){
		
		int result = 0;
		try{
			openConn();
			ps = conn.prepareStatement(sql);
			if(objs!=null && objs.length>0){
				for(int i = 0; i < objs.length; i++){
					ps.setObject(i+1, objs[i]);
				}
			}
			result = ps.executeUpdate();
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, ps, null);
		}
		return result;
	}
	
	/**
	 * 查询通用方法
	 */
	public  <T> List<T> query(String sql,Class<T> clazz,Object... objs){
		//创建List实例接收返回结果
		List<T> listResult = new ArrayList<T>();
		openConn();
		try{
			ps = conn.prepareStatement(sql);
			ParameterMetaData pmd = ps.getParameterMetaData();
			//获取参数个数
			int count = pmd.getParameterCount();
			//传入参数
			if(objs!=null && objs.length>0){
				for(int i = 0;i<count;i++){
					ps.setObject(i+1, objs[i]);
				}
			}
			//执行更新
			rs = ps.executeQuery();
			//获取结果集列数
			int columnCount = rs.getMetaData().getColumnCount();
			//遍历结果集每行的每一列
			while(rs.next()){
				T t = clazz.newInstance();
				for(int j = 0;j < columnCount;j++){
					String columnName = rs.getMetaData().getColumnName(j+1).toLowerCase();//获取每一行j列的名字;
					Object value = rs.getObject(j+1);//获取每一行j列的值
					//通过BeanUtils设置t的值(条件是bean的属性名要跟数据库中的相同)
					BeanUtils.setProperty(t, columnName, value);
				}
				listResult.add(t);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, ps, rs);
		}
		return listResult;
	}
}
