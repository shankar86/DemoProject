package com.douglas.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;


public class FrameworkProperties {

	public static Properties prop;

	public static final String SELENIUM_BROWSER = getProperty("browserName", "");


	public static String getProperty(String key, String defaultValue) {

		if (null == prop) {
			prop = new Properties();

			InputStream is = null;

			try {

				System.out.println("******Loading Properties file********");
				// TODO: We don't have framework.properties
				is = (FrameworkProperties.class.getClassLoader().getResource("framework.properties") != null)
						? FrameworkProperties.class.getClassLoader().getResource("framework.properties").openStream()
						: null;
				// By default there is no framework.properties , should handled by null check
				if (is == null)
					return null;
				prop.load(is);
			} catch (IOException e) {
				e.printStackTrace();

			} finally {
				try {
					if (is != null)
						is.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

			Enumeration<Object> keys = prop.keys();

			System.out.println("****** Configured Properties ********");
			while (keys.hasMoreElements()) {

				String element = (String) keys.nextElement();
				System.out.println(String.format("\t\tProperty: %s, value: '%s'",
						new Object[] { element, prop.getProperty(element) }));
			}

		}
		return prop.getProperty(key, defaultValue);
	}

}
