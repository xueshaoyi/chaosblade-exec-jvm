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
import java.lang.reflect.Method;

/**
 * @author xueshaoyi
 * @Date 2020/12/14 下午4:25
 **/
public class FeignEnhancer extends BeforeEnhancer {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeignEnhancer.class);

	@Override
	public EnhancerModel doBeforeAdvice(ClassLoader classLoader, String className, Object object, Method method,
	                                    Object[] methodArguments) throws Exception {
		if (methodArguments == null || methodArguments.length != 3) {
			LOGGER.info("The necessary parameters is null or length is not equal 3, {}",
			            methodArguments != null ? methodArguments.length : null);
			return null;
		}
		Object methodMethod = methodArguments[1];
		boolean isMethod = ReflectUtil.isAssignableFrom(classLoader, methodMethod.getClass(),
		                                                           "java.lang.reflect.Method");
		if (!isMethod) {
			LOGGER.info("methodArguments 2 is not method error {}", methodArguments);
			return null;
		}

		Method rootMethod = ReflectUtil.invokeMethod(methodMethod, "getRoot");
		String methodName = rootMethod.getName();
		String methodClassName = rootMethod.getClass().getName();
		String urlPath = null;
		Annotation[] declaredAnnotations = rootMethod.getDeclaredAnnotations();
		if (declaredAnnotations != null && declaredAnnotations.length > 0) {
			Annotation annotation = declaredAnnotations[0];
			LOGGER.info("annotation s is {}", annotation.toString());
		}

		MatcherModel matcherModel = new MatcherModel();
		if (StringUtils.isNotEmpty(methodName)) {
			matcherModel.add(FeignConstant.METHOD_MATCHER_NAME, methodName.toLowerCase());
		}
		if (StringUtils.isNotEmpty(methodClassName)) {
			matcherModel.add(FeignConstant.CLASS_MATCHER_NAME, methodClassName);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("redisson matchers: {}", JsonUtil.writer().writeValueAsString(matcherModel));
		}
		return new EnhancerModel(classLoader, matcherModel);
	}
}
