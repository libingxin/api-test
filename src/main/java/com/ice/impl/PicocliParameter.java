package com.ice.impl;

import com.ice.Parameter;
import lombok.Getter;
import picocli.CommandLine;

import java.util.Map;

@Getter
public class PicocliParameter implements Parameter {
    @CommandLine.Option(names = {"-h", "--help"}, description = "输出帮助信息")
    private boolean help;

    @CommandLine.Option(names = {"-t", "--thread"}, description = "并发数", required = true)
    private int thread;

    @CommandLine.Option(names = {"-c", "--count"}, description = "调用次数", required = true)
    private int count;

    @CommandLine.Option(names = {"-s", "--second"}, description = "调用时长：单位秒", required = true)
    private int second;

    @CommandLine.Option(names = {"-p", "--property"}, description = "自定义扩展属性", type = {String.class, String.class})
    private Map<String, String> property;

    @CommandLine.Option(names = {"-o", "--output"}, description = "结果输出到文件中")
    private int output;
}
