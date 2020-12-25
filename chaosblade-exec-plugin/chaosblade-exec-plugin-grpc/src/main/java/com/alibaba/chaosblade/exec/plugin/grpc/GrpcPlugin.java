package com.alibaba.chaosblade.exec.plugin.grpc;

import com.alibaba.chaosblade.exec.common.aop.Enhancer;
import com.alibaba.chaosblade.exec.common.aop.Plugin;
import com.alibaba.chaosblade.exec.common.aop.PointCut;
import com.alibaba.chaosblade.exec.common.model.ModelSpec;

/**
 * @author xueshaoyi
 * @Date 2020/12/25 上午11:07
 **/
public class GrpcPlugin implements Plugin {
	@Override
	public String getName() {
		return GrpcConstant.TARGET_NAME;
	}

	@Override
	public ModelSpec getModelSpec() {
		return new GrpcModelSpec();
	}

	@Override
	public PointCut getPointCut() {
		return new GrpcPointCut();
	}

	@Override
	public Enhancer getEnhancer() {
		return new GrpcEnhancer();
	}
}
