package com.ice.impl;

import com.ice.Parameter;
import com.ice.Starter;
import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommonCLIStarter extends Starter {
    private static final String THREAD_NAME = "t";
    private static final String COUNT_NAME = "c";
    private static final String SECOND_NAME = "s";
    private static final String PROPERTY_NAME = "p";
    private static final String OUTPUT_NAME = "o";

    public CommonCLIStarter(String[] args) {
        super(args);
    }

    @Override
    protected Parameter parse() {
        // 1. 定义阶段
        Options options = stage1();

        // 2. 解析阶段
        CommandLine commandLine = stage2(options);

        // 3. 服务阶段
        return stage3(commandLine);
    }

    private Options stage1() {
        return new Options()
                .addOption(Option.builder(THREAD_NAME)
                        .longOpt("thread")
                        .required()
                        .hasArg()
                        .desc("并发数")
                        .build())
                .addOption(Option.builder(COUNT_NAME)
                        .longOpt("count")
                        .required()
                        .hasArg()
                        .desc("调用次数")
                        .build())
                .addOption(Option.builder(SECOND_NAME)
                        .longOpt("second")
                        .required().hasArg()
                        .desc("调用时长：单位秒")
                        .build())
                .addOption(Option.builder(PROPERTY_NAME)
                        .longOpt("property")
                        .hasArgs()
                        .valueSeparator()
                        .numberOfArgs(2)
                        .desc("自定义扩展属性")
                        .build())
                .addOption(Option.builder(OUTPUT_NAME)
                        .longOpt("output")
                        .hasArg()
                        .desc("结果输出到文件中")
                        .build());
    }

    private CommandLine stage2(Options options) {
        CommandLineParser parser = new DefaultParser();

        try {
            return parser.parse(options, args);
        } catch (Exception e) {
            throw new IllegalArgumentException("Parsed parameters error", e);
        }
    }

    private Parameter stage3(CommandLine commandLine) {
        PlanParameter parameter = new PlanParameter();
        parameter.setThread(Integer.parseInt(commandLine.getOptionValue(THREAD_NAME)));
        parameter.setCount(Integer.parseInt(commandLine.getOptionValue(COUNT_NAME)));
        parameter.setSecond(Integer.parseInt(commandLine.getOptionValue(SECOND_NAME)));
        parameter.setOutput(commandLine.getOptionValue(OUTPUT_NAME));

        if (commandLine.hasOption(PROPERTY_NAME)) {
            Properties properties = commandLine.getOptionProperties(PROPERTY_NAME);
            if (properties != null && properties.size() > 0) {
                Map<String, String> property = new HashMap<>();
                parameter.setProperty(property);
                for (Map.Entry<Object, Object> entity : properties.entrySet()) {
                    property.put(String.valueOf(entity.getKey()), String.valueOf(entity.getValue()));
                }
            }
        }

        return parameter;
    }
}
