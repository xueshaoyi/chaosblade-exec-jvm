package com.alibaba.chaosblade.exec.plugin.feign;

import com.alibaba.chaosblade.exec.common.aop.Enhancer;
import com.alibaba.chaosblade.exec.common.aop.Plugin;
import com.alibaba.chaosblade.exec.common.aop.PointCut;
import com.alibaba.chaosblade.exec.common.model.ModelSpec;

/**
 * @author xueshaoyi
 * @Date 2020/12/14 下午4:01
 **/
public class FeignPlugin implements Plugin {
	@Override
	public String getName() {
		return FeignConstant.TARGET_NAME;
	}

	@Override
	public ModelSpec getModelSpec() {
		return new FeignModelSpec();
	}

	@Override
	public PointCut getPointCut() {
		return new FeignPointCut();
	}

	@Override
	public Enhancer getEnhancer() {
		return null;
	}
}
