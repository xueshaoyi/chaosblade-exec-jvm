package com.alibaba.chaosblade.exec.plugin.rocketmq;

import com.alibaba.chaosblade.exec.common.model.matcher.BasePredicateMatcherSpec;

/**
 * @author xueshaoyi
 * @date 2020-12-22
 */
public class TagsMatcherSpec extends BasePredicateMatcherSpec implements RocketMqConstant {

    @Override
    public String getName() {
        return FLAG_TAGS;
    }

    @Override
    public String getDesc() {
        return "Message tags";
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
