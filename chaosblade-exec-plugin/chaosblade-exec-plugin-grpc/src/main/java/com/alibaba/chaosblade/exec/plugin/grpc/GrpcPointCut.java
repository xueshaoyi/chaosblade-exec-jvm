package com.alibaba.chaosblade.exec.plugin.grpc;

import com.alibaba.chaosblade.exec.common.aop.PointCut;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.ClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.NameClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.MethodMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.NameMethodMatcher;

/**
 * @author xueshaoyi
 * @Date 2020/12/25 上午10:27
 **/
public class GrpcPointCut implements PointCut {
	private static final String GRPC_EXEC = "io.grpc.stub.ClientCalls";
	private static final String INTERCEPTOR_PRE_METHOD = "blockingUnaryCall";

	@Override
	public ClassMatcher getClassMatcher() {
		return new NameClassMatcher(GRPC_EXEC);
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		return new NameMethodMatcher(INTERCEPTOR_PRE_METHOD);
	}
}
