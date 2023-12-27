package com.nabil.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;
public class UtilitiesClass {

	public static void copyByGetterSetter(Object target ,Object source, String ... excludedAttributes) throws IllegalArgumentException, IllegalAccessException,
	InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, IncompatibleTypesException {
 
if( target.getClass() != source.getClass()) { throw new IncompatibleTypesException();}
		
Method[] methods = source.getClass().getMethods();
Stream<Method> methodsStream = Arrays.stream(methods);
methodsStream.filter(m -> m.getName().startsWith("get")).filter(gm -> {
	for (String str : excludedAttributes) {
		if (gm.getName().toLowerCase().substring(3).equals(str.toLowerCase())) {
			return false;
		}
	}
	return true;
}).forEach(getMethod -> {
	Class<?> getterReturnType = getMethod.getReturnType();
	String setMethodName = "set" + getMethod.getName().substring(3);
	Method setMethod;
	try {
		setMethod = target.getClass().getMethod(setMethodName, getterReturnType);
		Object val = getMethod.invoke(source);
		if(val!=null && setMethod!=null) {
		setMethod.invoke(target, Class.forName(val.getClass().getName()).cast(val));
		}
	} catch (NoSuchMethodException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
		System.err.println("setter call fail");
	} catch (SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
});
}
}
