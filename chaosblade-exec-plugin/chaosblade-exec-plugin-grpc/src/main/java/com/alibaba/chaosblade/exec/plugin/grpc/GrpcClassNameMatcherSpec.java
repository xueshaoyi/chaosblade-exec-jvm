package com.alibaba.chaosblade.exec.plugin.grpc;

import com.alibaba.chaosblade.exec.common.model.matcher.BasePredicateMatcherSpec;

/**
 * @author xueshaoyi
 * @Date 2020/12/25 上午10:30
 **/
public class GrpcClassNameMatcherSpec extends BasePredicateMatcherSpec {
	@Override
	public String getName() {
		return GrpcConstant.CLASS_MATCHER_NAME;
	}

	@Override
	public String getDesc() {
		return "The grpc class name, for example, com.alibaba.client.service.TestService.";
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
