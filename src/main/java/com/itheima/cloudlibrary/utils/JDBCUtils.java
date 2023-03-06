package com.itheima.cloudlibrary.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;


import org.apache.commons.dbcp2.BasicDataSourceFactory;

/**
 * JDBC的工具类
 *
 */
public class JDBCUtils {
	public static  DataSource dataSource ;

	static {
		try{
			InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
			Properties properties = new Properties();
			properties.load(is);
			dataSource = BasicDataSourceFactory.createDataSource(properties);
		}catch (Exception e){
			throw  new RuntimeException(e);
		}
	}
	/**
	 * 获得连接
	 * @throws Exception 
	 */
	public static Connection getConnection() throws Exception{
		return dataSource.getConnection();
	}
	
	/**
	 * 获得连接池
	 */
	public static DataSource getDataSource(){
		return dataSource;
	}
}
