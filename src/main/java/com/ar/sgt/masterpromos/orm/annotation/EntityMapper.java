package com.ar.sgt.masterpromos.orm.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.ar.sgt.masterpromos.model.ModelKey;
import com.google.appengine.api.datastore.KeyFactory;

public class EntityMapper {

	private Map<String, List<Field>> cached = new HashMap<String, List<Field>>();
	
	public com.google.appengine.api.datastore.Entity toDatastoreEntity(Object element) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException  {

		if (element == null) return null;
		
		String entityType = element.getClass().getSimpleName();
		
		com.google.appengine.api.datastore.Entity entity = ((ModelKey) element).getKey() == null ? new com.google.appengine.api.datastore.Entity(entityType) : new com.google.appengine.api.datastore.Entity(KeyFactory.createKey(entityType, ((ModelKey) element).getKey()));
		
		for (Field field : getFields(element.getClass())) {
			if (field.isAnnotationPresent(Id.class)) {
				continue;
			}
			if (field.isAnnotationPresent(Unindexed.class)) {
				entity.setUnindexedProperty(field.getName(), getPropertyValue(field, element));				
			} else {
				entity.setProperty(field.getName(), getPropertyValue(field, element));
			}
		}
		
		return entity;
		
	}
	
	public <T> T fromDatastoreEntity(com.google.appengine.api.datastore.Entity entity, Class<T> type) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException  {
		
		if (entity == null) return null;
		
		T object = type.newInstance();
		
		for (Field field : getFields(type)) {
			if (field.isAnnotationPresent(Id.class)) {
				setPropertyValue(field, entity.getKey().getId(), object);
			} else {
				setPropertyValue(field, entity.getProperty(field.getName()), object);
			}
		}

		return object;
	}
	
	private void setPropertyValue(Field field, Object value, Object element) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		PropertyUtils.setProperty(element, field.getName(), value);
	}
	
	private Object getPropertyValue(Field field, Object element) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return PropertyUtils.getProperty(element, field.getName());
	}
	
	private List<Field> getFields(Class<?> type) {
		
		if (cached.get(type.getName()) != null) return cached.get(type.getName());
		
		List<Field> fields = new ArrayList<Field>();
		
		if (type.getSuperclass() != null) {
			fields.addAll(getFields(type.getSuperclass()));
		}
		
		for (Field field : type.getDeclaredFields()) {
			if (isAccesible(field.getModifiers())) {
				field.setAccessible(true);
				fields.add(field);
			}
		}
		
		cached.put(type.getName(), fields);
		
		return fields;
	}
	
	private boolean isAccesible(int modifier) {
		return !(Modifier.isTransient(modifier) || Modifier.isStatic(modifier) || Modifier.isAbstract(modifier));
	}
	
}
