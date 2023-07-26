package com.ice;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class HowToUseCLI {
    public Options stage1() {
        Options options = new Options();

// 方式1：直接调用Options提供的方法
        options.addOption("t", "thread", true, "并发数");

// 方式2：通过创建Option类的实例
        options.addOption(new Option("c", "count", true, "调用次数"));

// 方式3：通过Option内部的Builder构建
        options.addOption(Option.builder("s")
                .longOpt("second")
                .required()
                .hasArg()
                .desc("调用时长：单位秒")
                .build());
        return options;
    }

}
