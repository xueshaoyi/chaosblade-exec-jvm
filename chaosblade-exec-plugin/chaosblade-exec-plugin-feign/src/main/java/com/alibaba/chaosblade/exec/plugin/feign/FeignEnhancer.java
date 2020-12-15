package com.alibaba.chaosblade.exec.plugin.feign;

import com.alibaba.chaosblade.exec.common.aop.BeforeEnhancer;
import com.alibaba.chaosblade.exec.common.aop.EnhancerModel;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherModel;
import com.alibaba.chaosblade.exec.common.util.JsonUtil;
import com.alibaba.chaosblade.exec.common.util.ReflectUtil;
import com.alibaba.chaosblade.exec.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xueshaoyi
 * @Date 2020/12/14 下午4:25
 **/
public class FeignEnhancer extends BeforeEnhancer {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeignEnhancer.class);
	private static final String REGEX = "\\/(.*?)(?=\")";

	@Override
	public EnhancerModel doBeforeAdvice(ClassLoader classLoader, String className, Object object, Method method,
	                                    Object[] methodArguments) throws Exception {
		if (methodArguments == null || methodArguments.length != 3) {
			LOGGER.info("The necessary parameters is null or length is not equal 3, {}",
			            methodArguments != null ? methodArguments.length : null);
			return null;
		}
		Object proxy = ReflectUtil.getFieldValue(object, "target", true);
		Object methodMethod = methodArguments[1];
		boolean isMethod = ReflectUtil.isAssignableFrom(classLoader, methodMethod.getClass(),
		                                                           "java.lang.reflect.Method");
		if (!isMethod) {
			LOGGER.info("methodArguments 2 is not method error {}", methodArguments);
			return null;
		}

		Method rootMethod = ReflectUtil.invokeMethod(methodMethod, "getRoot");
		String urlPath = null;
		Annotation[] declaredAnnotations = rootMethod.getDeclaredAnnotations();
		if (declaredAnnotations != null && declaredAnnotations.length > 0) {
			Annotation annotation = declaredAnnotations[0];
			InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
			Field memberValues = invocationHandler.getClass().getDeclaredField("memberValues");
			memberValues.setAccessible(true);
			Map map = (Map) memberValues.get(invocationHandler);
			Object value = map.get("value");
			Object path = map.get("path");
			String[] values = null;
			if (path instanceof String[]) {
				values = (String[]) path;
			}
			if (values.length == 0) {
				values = (String[]) value;
			}
			urlPath = values[0];
		}

		String clientName = ReflectUtil.invokeMethod(proxy, "name");
		LOGGER.info("project aop clientName {}, urlPath {}", clientName, urlPath);
		MatcherModel matcherModel = new MatcherModel();
		if (StringUtils.isNotEmpty(clientName)) {
			matcherModel.add(FeignConstant.CLIENT_MATCHER_NAME, clientName.toLowerCase());
		}
		if (StringUtils.isNotEmpty(urlPath)) {
			matcherModel.add(FeignConstant.PATH_MATCHER_NAME, urlPath);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("redisson matchers: {}", JsonUtil.writer().writeValueAsString(matcherModel));
		}
		return new EnhancerModel(classLoader, matcherModel);
	}
}
