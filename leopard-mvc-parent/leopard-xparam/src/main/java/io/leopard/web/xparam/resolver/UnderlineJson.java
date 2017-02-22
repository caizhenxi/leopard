package io.leopard.web.xparam.resolver;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.leopard.json.DisablingJsonSerializerIntrospector;
import io.leopard.json.JsonException;
import io.leopard.json.JsonJacksonImpl.OnumJsonSerializer;

public class UnderlineJson {

	private static ObjectMapper mapper = new ObjectMapper();

	private static ObjectMapper underlineMapper = new ObjectMapper();

	static {

		{
			SimpleModule module = new SimpleModule();
			module.addSerializer(new OnumJsonSerializer());
			// module.addDeserializer(Onum.class, new OnumJsonDeserializer());

			mapper.registerModule(module);
			underlineMapper.registerModule(module);
		}

		mapper.setAnnotationIntrospector(new DisablingJsonSerializerIntrospector());
		underlineMapper.setAnnotationIntrospector(new DisablingJsonSerializerIntrospector());

		underlineMapper = mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
	}

	private static ObjectMapper getObjectMapper() {
		if (UnderlineNameConfiger.isEnable()) {
			return underlineMapper;
		}
		else {
			return mapper;
		}
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		if (json == null || json.length() == 0) {
			return null;
		}
		try {
			return getObjectMapper().readValue(json, clazz);
		}
		catch (Exception e) {
			throw new JsonException(e.getMessage(), e);
		}
	}

	public static <T> List<T> toListObject(String json, Class<T> clazz) {
		if (json == null || json.length() == 0) {
			return null;
		}
		JavaType javaType = getObjectMapper().getTypeFactory().constructParametrizedType(ArrayList.class, List.class, clazz);
		try {
			return getObjectMapper().readValue(json, javaType);
		}
		catch (Exception e) {

			throw new JsonException(e.getMessage(), e);
		}
	}
}
