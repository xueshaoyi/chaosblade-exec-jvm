package com.alibaba.chaosblade.exec.plugin.feign;

import com.alibaba.chaosblade.exec.common.model.matcher.BasePredicateMatcherSpec;

/**
 * @author xueshaoyi
 * @Date 2020/12/14 下午4:11
 **/
public class FeignMethodNameMatcherSpec extends BasePredicateMatcherSpec {
	@Override
	public String getName() {
		return FeignConstant.METHOD_MATCHER_NAME;
	}

	@Override
	public String getDesc() {
		return "The methodName, for example, getClientTest.";
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
