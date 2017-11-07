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
	 * ͨ�ø���
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
	 * ��ѯͨ�÷���
	 */
	public  <T> List<T> query(String sql,Class<T> clazz,Object... objs){
		//����Listʵ�����շ��ؽ��
		List<T> listResult = new ArrayList<T>();
		openConn();
		try{
			ps = conn.prepareStatement(sql);
			ParameterMetaData pmd = ps.getParameterMetaData();
			//��ȡ��������
			int count = pmd.getParameterCount();
			//�������
			if(objs!=null && objs.length>0){
				for(int i = 0;i<count;i++){
					ps.setObject(i+1, objs[i]);
				}
			}
			//ִ�и���
			rs = ps.executeQuery();
			//��ȡ���������
			int columnCount = rs.getMetaData().getColumnCount();
			//���������ÿ�е�ÿһ��
			while(rs.next()){
				T t = clazz.newInstance();
				for(int j = 0;j < columnCount;j++){
					String columnName = rs.getMetaData().getColumnName(j+1).toLowerCase();//��ȡÿһ��j�е�����;
					Object value = rs.getObject(j+1);//��ȡÿһ��j�е�ֵ
					//ͨ��BeanUtils����t��ֵ(������bean��������Ҫ�����ݿ��е���ͬ)
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
