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
		Object proxy = ReflectUtil.getFieldValue(object, "target", true);
		Object metaData = ReflectUtil.getFieldValue(object, "metadata", true);
		LOGGER.info("project aop metaData {}", metaData);
		Object temp = ReflectUtil.getFieldValue(metaData, "template", true);
		LOGGER.info("project aop temp {}", temp);
		Object uriTemplate = ReflectUtil.getFieldValue(temp, "uriTemplate", true);
		LOGGER.info("project aop uriTemplate {}", uriTemplate);
		Object template = ReflectUtil.getFieldValue(uriTemplate, "template", true);
		LOGGER.info("project aop template {}", template);

		String urlPath = (String)template;

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
