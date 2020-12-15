package com.alibaba.chaosblade.exec.plugin.feign;

import com.alibaba.chaosblade.exec.common.model.FrameworkModelSpec;
import com.alibaba.chaosblade.exec.common.model.action.ActionSpec;
import com.alibaba.chaosblade.exec.common.model.action.delay.DelayActionSpec;
import com.alibaba.chaosblade.exec.common.model.action.exception.ThrowCustomExceptionActionSpec;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xueshaoyi
 * @Date 2020/12/14 下午4:04
 **/
public class FeignModelSpec extends FrameworkModelSpec {
	public FeignModelSpec() {
		addActionExample();
	}

	private void addActionExample() {
		List<ActionSpec> actions = getActions();
		for (ActionSpec action : actions) {
			if (action instanceof DelayActionSpec) {
				action.setLongDesc("FeignRPC commands delay experiments");
				action.setExample("# Do a delay 2s experiment on Feign \n" +
				                  "blade create feign delay --clientname itest-project --time 2000\n\n" +

				                  "#Do a delay 2s experiment on feign url\n" +
				                  "blade create feign delay --path /api/test/getTestUrl --time 2000");
			}
			if (action instanceof ThrowCustomExceptionActionSpec) {
				action.setLongDesc("Feign commands throws custom exception experiments");
				action.setExample("# Do a throws custom exception experiment on Feign \n" +
				                  "blade create feign throwCustomException --exception java.lang.Exception --path /api/test/getTestUrl");
			}
		}
	}

	@Override
	protected List<MatcherSpec> createNewMatcherSpecs() {
		ArrayList<MatcherSpec> matcherSpecs = new ArrayList<MatcherSpec>();
		matcherSpecs.add(new FeignClientNameMatcherSpec());
		matcherSpecs.add(new FeignPathMatcherSpec());
		return matcherSpecs;
	}

	@Override
	public String getTarget() {
		return FeignConstant.TARGET_NAME;
	}

	@Override
	public String getShortDesc() {
		return "feign experiment";
	}

	@Override
	public String getLongDesc() {
		return "feign experiment contains delay and exception by clientname and so on.";
	}
}
