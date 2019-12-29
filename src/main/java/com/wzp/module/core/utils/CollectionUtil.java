package com.wzp.module.core.utils;

import org.springframework.util.CollectionUtils;

import java.util.*;

public class CollectionUtil {
	public static boolean isNotEmpty(Collection<? extends Object> collection) {
		return !CollectionUtils.isEmpty(collection);
	}

	public static boolean isNotEmpty(Map<? extends Object, ? extends Object> result) {
		return result != null && result.size() > 0;
	}

	public static boolean isEmpty(Collection<? extends Object> collection) {
		return CollectionUtils.isEmpty(collection);
	}
	
	public static boolean isEmpty(Map<? extends Object, ? extends Object> result) {
        return !isNotEmpty(result);
	}
	
	/**
	 * 将map进行反转，key作为value，value作为key
	 * 如果value不具有唯一性，结果不可控
	 * @param map
	 * @return
	 */
	public static Map reversalMap(Map map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		
		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = (Object) iterator.next();
			Object value = map.get(key);
			
			result.put(value, key);
		}
		
		return result;
	}

	/**
	 * 数组反转
	 * @param array
	 * @param <T>
	 * @return
	 */
	public static <T> T[] reversal(T[] array) {
		List<T> arrayList = new ArrayList<T>();  
        for (int i = 0; i < array.length; i++) {  
            arrayList.add(array[array.length - i - 1]);  
        }  
        return arrayList.toArray(array);
	}

	/**
	 * 获取有序集合的第一个元素
	 * @param list
	 * @param <T>
	 * @return
	 */
	public static <T> T first(List<T> list) {
		if (isEmpty(list)) {
			return null;
		} else {
			return list.get(0);
		}
	}

	/**
	 * 转换为List形式
	 * @param obj
	 * @return
	 */
	public static <T> List<T> asList(T obj) {
		List<T> result = new ArrayList<>();
		result.add(obj);
		return result;
	}

	/**
	 * 转换为List形式
	 * @param objs
	 * @return
	 */
	public static <T> List<T> toList(T[] objs) {
		List<T> result = new ArrayList<>();

		for (T obj : objs) {
			result.add(obj);
		}

		return result;
	}
}
