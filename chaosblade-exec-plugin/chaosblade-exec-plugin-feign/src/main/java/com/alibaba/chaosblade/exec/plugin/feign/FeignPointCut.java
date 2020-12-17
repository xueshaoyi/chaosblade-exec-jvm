package com.alibaba.chaosblade.exec.plugin.feign;

import com.alibaba.chaosblade.exec.common.aop.PointCut;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.ClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.NameClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.MethodMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.NameMethodMatcher;

/**
 * @author xueshaoyi
 * @Date 2020/12/14 下午4:21
 **/
public class FeignPointCut implements PointCut {
	private static final String FEIGN_EXEC = "feign.SynchronousMethodHandler";
	private static final String INTERCEPTOR_PRE_METHOD = "invoke";

	@Override
	public ClassMatcher getClassMatcher() {
		return new NameClassMatcher(FEIGN_EXEC);
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		return new NameMethodMatcher(INTERCEPTOR_PRE_METHOD);
	}
}
