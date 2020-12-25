package com.alibaba.chaosblade.exec.plugin.grpc;

import com.alibaba.chaosblade.exec.common.model.FrameworkModelSpec;
import com.alibaba.chaosblade.exec.common.model.action.ActionSpec;
import com.alibaba.chaosblade.exec.common.model.action.delay.DelayActionSpec;
import com.alibaba.chaosblade.exec.common.model.action.exception.ThrowCustomExceptionActionSpec;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xueshaoyi
 * @Date 2020/12/25 上午11:13
 **/
public class GrpcModelSpec extends FrameworkModelSpec {
	public GrpcModelSpec() {
		addActionExample();
	}

	private void addActionExample() {
		List<ActionSpec> actions = getActions();
		for (ActionSpec action : actions) {
			if (action instanceof DelayActionSpec) {
				action.setLongDesc("Grpc commands delay experiments");
				action.setExample("# Do a delay 2s experiment on Feign \n" +
				                  "blade create grpc delay --classname com.alibaba.client.service.TestService --time 2000\n\n" +

				                  "#Do a delay 2s experiment on feign url\n" +
				                  "blade create grpc delay --method getTestName --time 2000");
			}
			if (action instanceof ThrowCustomExceptionActionSpec) {
				action.setLongDesc("Grpc commands throws custom exception experiments");
				action.setExample("# Do a throws custom exception experiment on Feign \n" +
				                  "blade create grpc throwCustomException --exception java.lang.Exception --method getTestName");
			}
		}
	}

	@Override
	protected List<MatcherSpec> createNewMatcherSpecs() {
		ArrayList<MatcherSpec> matcherSpecs = new ArrayList<MatcherSpec>();
		matcherSpecs.add(new GrpcClassNameMatcherSpec());
		matcherSpecs.add(new GrpcMethodMatcherSpec());
		return matcherSpecs;
	}

	@Override
	public String getTarget() {
		return GrpcConstant.TARGET_NAME;
	}

	@Override
	public String getShortDesc() {
		return "grpc experiment";
	}

	@Override
	public String getLongDesc() {
		return "grpc experiment contains delay and exception by className and so on.";
	}
}
