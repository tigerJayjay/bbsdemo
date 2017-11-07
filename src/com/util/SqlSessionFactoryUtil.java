package com.util;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 
 * @author tigerJay
 *
 */
public class SqlSessionFactoryUtil {
	private static SqlSessionFactory factory = null;
	
	static{
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader("mybatis.xml");
			factory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static  SqlSession openSqlSession(){
		return factory.openSession();
	}
	
	public static void close(SqlSession session){
		if(session!=null){
			session.close();
		}
	}
}
