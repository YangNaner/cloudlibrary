package com.itheima.cloudlibrary.utils;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 工厂类：

 */
public class BeanFactory {
	/**
	 * 提供获得对象实例的方法:
	 * 
	 * @param id
	 * @return
	 */
	public static Object getBean(String id) {
		try {
			// 解析XML
			SAXReader saxReader = new SAXReader();
			// 使用类的加载器读取配置文件:
			InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("applicationContext.xml");
			Document document = saxReader.read(is);
			// 获得id为某个值的Bean标签
			Element beanElement = (Element) document.selectSingleNode("//bean[@id='" + id + "']");
			// 获得这个bean标签下的class属性的值
			String value = beanElement.attributeValue("class");// com.itheima.store.dao.impl.UserDaoImpl
			// 反射
			Class clazz = Class.forName(value);
			final Object obj = clazz.newInstance();
			// 使用动态代理对所有DAO产生代理类:
			if(id.endsWith("Dao")){
				// 产生动态代理:
				Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						if("save".equals(method.getName())){
							// 在save之前做权限校验:
							System.out.println("=====权限校验=====");
						}
						if("delete".equals(method.getName())){
							Object o = method.invoke(obj, args);
							System.out.println("=====日志记录=====");
							return o;
						}
						return method.invoke(obj, args);
					}
				});
				return proxy;
			}
			
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
