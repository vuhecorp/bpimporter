package com.hersa.bp.importer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.hersa.app.db.AuditMe;
import com.hersa.bp.importer.dao.bpreading.BpReadingDTO;

public class Test {

	public static void main(String[] args) {
		BpReadingDTO dto = new BpReadingDTO();
		dto.setDescription("hello");
		Field[] fields = dto.getClass().getDeclaredFields();
		for (Field field : fields) {
			System.err.println(field.getName());
			Annotation[] annotations = field.getAnnotations();
			for (Annotation annotation : annotations) {
				AuditMe audit = (AuditMe) annotation;
				System.err.println(audit.isCollection());
			}
		}

	}

}
