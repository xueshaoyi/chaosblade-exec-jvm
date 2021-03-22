/*
 * Copyright 1999-2019 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.alibaba.chaosblade.exec.plugin.redisson;

import com.alibaba.chaosblade.exec.common.aop.BeforeEnhancer;
import com.alibaba.chaosblade.exec.common.aop.EnhancerModel;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherModel;
import com.alibaba.chaosblade.exec.common.util.JsonUtil;
import com.alibaba.chaosblade.exec.common.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xueshaoyi
 * @Date 2020/11/23 上午11:40
 **/
public class RedissonEnhancer extends BeforeEnhancer {

	public static final String CHARSET = "UTF-8";
	private static final Logger LOGGER = LoggerFactory.getLogger(RedissonEnhancer.class);

	@Override
	public EnhancerModel doBeforeAdvice(ClassLoader classLoader, String className, Object object, Method method,
	                                    Object[] methodArguments) throws Exception {

		if (methodArguments == null || methodArguments.length != 7) {
			LOGGER.info("The necessary parameters is null or length is not equal 7, {}",
			            methodArguments != null ? methodArguments.length : null);
			return null;
		}
		LOGGER.info("method Arguments {}", methodArguments.toString());
		Set<String> addressSet = new HashSet<String>();
		try {
			Object objectBuilder = ReflectUtil.invokeMethod(object, "getObjectBuilder");
			Object redisson = ReflectUtil.getFieldValue(objectBuilder, "redisson", true);
			Object connectionManager = ReflectUtil.invokeMethod(redisson, "getConnectionManager");
			Object subscribeService = ReflectUtil.invokeMethod(connectionManager, "getSubscribeService");
			Object config = ReflectUtil.getFieldValue(subscribeService, "config", true);
			Object masterAddress = ReflectUtil.invokeMethod(config, "getMasterAddress");
			Object slaveAddresses = ReflectUtil.invokeMethod(config, "getSlaveAddresses");
			Set<String> slaveAddressesSet = (HashSet<String>) slaveAddresses;
			addressSet.addAll(slaveAddressesSet);
			addressSet.add(masterAddress.toString());
			LOGGER.info("redisson masterAddress {} slaveAddress {}", String.valueOf(masterAddress), slaveAddressesSet.size());
		} catch (Exception e) {
			LOGGER.info("redisson address error {}", e);
		}


		Object command = methodArguments[3];
		LOGGER.info("method command {}", ReflectUtil.invokeMethod(command, "getName", new Object[0], false));
		boolean redisCommand =
				ReflectUtil.isAssignableFrom(
						classLoader, command.getClass(), "org.redisson.client.protocol.RedisCommand");
		String cmd = null;
		if (redisCommand) {
			cmd = ReflectUtil.invokeMethod(command, "getName", new Object[0], false);
		}

		String key = null;
		Object args = methodArguments[4];
		if (!args.getClass().isArray() || !(args instanceof Object[])) {
			return null;
		}
		Object[] params = (Object[]) args;
		if (params != null && params.length >= 1) {
			key = params[0].toString();
		}


		MatcherModel matcherModel = new MatcherModel();
		if (cmd != null) {
			matcherModel.add(RedissonConstant.COMMAND_TYPE_MATCHER_NAME, cmd.toLowerCase());
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("redisson matchers: {}", JsonUtil.writer().writeValueAsString(matcherModel));
		}
		EnhancerModel enhancerModel = new EnhancerModel(classLoader, matcherModel);
		enhancerModel.addCustomMatcher(RedissonConstant.KEY_MATCHER_NAME, key,
		                               RedissonKeyMatcher.getInstance());
		enhancerModel.addCustomMatcher(RedissonConstant.ADDRESS_MATCHER_NAME, addressSet,
		                               RedissonAddressMatcher.getInstance());
		return enhancerModel;
	}
}
