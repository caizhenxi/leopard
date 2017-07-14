package io.leopard.sysconfig;

import java.lang.reflect.Field;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.StringUtils;

public class SysconfigBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
	protected ConfigurableListableBeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// System.err.println("postProcessBeforeInitialization:" + beanName);
		Class<?> clazz = bean.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Value annotation = field.getAnnotation(Value.class);
			if (annotation == null) {
				continue;
			}
			field.setAccessible(true);
			Object value = resolveValue(annotation, field);
			if (value == null) {
				continue;
			}
			try {
				field.set(bean, value);
			}
			catch (IllegalAccessException e) {
				throw new BeanInstantiationException(clazz, e.getMessage(), e);

			}
		}
		return bean;
	}

	private SysconfigDao sysconfigDao = new SysconfigDaoJdbcImpl();

	protected Object resolveValue(Value anno, Field field) {
		String value = anno.value();
		if (StringUtils.isEmpty(value)) {
			return null;
		}

		// ${aws.oss.endpoint}
		String key = value.replace("${", "").replace("}", "");
		return sysconfigDao.resolve(key);
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
