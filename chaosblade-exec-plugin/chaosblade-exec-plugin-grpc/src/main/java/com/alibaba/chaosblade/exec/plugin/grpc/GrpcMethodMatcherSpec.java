package com.alibaba.chaosblade.exec.plugin.grpc;

import com.alibaba.chaosblade.exec.common.model.matcher.BasePredicateMatcherSpec;

/**
 * @author xueshaoyi
 * @Date 2020/12/25 上午11:06
 **/
public class GrpcMethodMatcherSpec extends BasePredicateMatcherSpec {
	@Override
	public String getName() {
		return GrpcConstant.METHOD_MATCHER_NAME;
	}

	@Override
	public String getDesc() {
		return "The grpc method name, for example, getTestName.";
	}

	@Override
	public boolean noArgs() {
		return false;
	}

	@Override
	public boolean required() {
		return false;
	}
}
