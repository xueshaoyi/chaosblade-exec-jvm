package com.alibaba.chaosblade.exec.plugin.grpc;

import com.alibaba.chaosblade.exec.common.aop.BeforeEnhancer;
import com.alibaba.chaosblade.exec.common.aop.EnhancerModel;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherModel;
import com.alibaba.chaosblade.exec.common.util.JsonUtil;
import com.alibaba.chaosblade.exec.common.util.ReflectUtil;
import com.alibaba.chaosblade.exec.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author xueshaoyi
 * @Date 2020/12/25 上午11:15
 **/
public class GrpcEnhancer extends BeforeEnhancer {

	private static final Logger LOGGER = LoggerFactory.getLogger(GrpcEnhancer.class);
	@Override
	public EnhancerModel doBeforeAdvice(ClassLoader classLoader, String className, Object object, Method method,
	                                    Object[] methodArguments) throws Exception {
		if (methodArguments.length != 4) {
			LOGGER.info("grpc enhancer methodArguments size is not 4 size is {}", methodArguments.length);
			return null;
		}
		Object methodDescriptor = methodArguments[1];
		String getFullMethodName = ReflectUtil.invokeMethod(methodDescriptor, "getFullMethodName");
		String[] classMethod = getFullMethodName.split("/");
		String classname = null;
		String methodName = null;
		if (classMethod.length == 2) {
			classname = classMethod[0];
			methodName = classMethod[1];
		}
		LOGGER.info("getFullMethodName  classname : {} methodName : {}", classname, methodName);
		MatcherModel matcherModel = new MatcherModel();
		if (StringUtils.isNotEmpty(classname)) {
			matcherModel.add(GrpcConstant.CLASS_MATCHER_NAME, classname);
		}
		if (StringUtils.isNotEmpty(methodName)) {
			matcherModel.add(GrpcConstant.METHOD_MATCHER_NAME, methodName);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("grpc matchers: {}", JsonUtil.writer().writeValueAsString(matcherModel));
		}
		return new EnhancerModel(classLoader, matcherModel);
	}
}
