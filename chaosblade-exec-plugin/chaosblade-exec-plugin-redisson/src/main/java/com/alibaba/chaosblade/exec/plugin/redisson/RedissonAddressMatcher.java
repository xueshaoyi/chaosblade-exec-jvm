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

import com.alibaba.chaosblade.exec.common.aop.CustomMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author xsy
 * @date 2021-03-08 11:31 上午
 */
public class RedissonAddressMatcher implements CustomMatcher {

    public static final Logger logger = LoggerFactory.getLogger(RedissonAddressMatcher.class);

    public static final RedissonAddressMatcher CALL_BACK = new RedissonAddressMatcher();

    public static RedissonAddressMatcher getInstance() {
        return CALL_BACK;
    }


    @Override
    public boolean match(String commandValue, Object originValue) {
        Set<String> addressSet = (HashSet<String>)originValue;
        for (String address : addressSet) {
            if (address.contains(commandValue)) {
                return true;
            }
        }
        return false;
    }
}
