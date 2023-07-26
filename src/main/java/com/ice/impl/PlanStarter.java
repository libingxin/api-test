package com.ice.impl;

import com.ice.Parameter;
import com.ice.Starter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class PlanStarter extends Starter {
    public PlanStarter(String[] args) {
        super(args);
    }

    public Parameter parse() {
        Map<String, BiConsumer<PlanParameter, String>> functions = createFunctions();

        PlanParameter parameter = new PlanParameter();
        for (int i = 0; i < args.length; i += 2) {
            BiConsumer<PlanParameter, String> function = functions.get(args[i]);
            if (function != null) {
                function.accept(parameter, args[i + 1]);
            }
        }
        return parameter;
    }

    private Map<String, BiConsumer<PlanParameter, String>> createFunctions() {
        Map<String, BiConsumer<PlanParameter, String>> functions = new HashMap<>();

        // 1. 设置输出帮助信息参数
        BiConsumer<PlanParameter, String> helpFunc = (parameter, value) -> parameter.setHelp(true);
        functions.put("-h", helpFunc);
        functions.put("--help", helpFunc);

        // 2. 设置并发数
        BiConsumer<PlanParameter, String> threadFunc = (parameter, value) -> parameter.setThread(Integer.parseInt(value));
        functions.put("-t", threadFunc);
        functions.put("--thread", threadFunc);

        // 3. 设置调用次数
        BiConsumer<PlanParameter, String> countFunc = (parameter, value) -> parameter.setCount(Integer.parseInt(value));
        functions.put("-c", countFunc);
        functions.put("--count", countFunc);

        // 4. 设置调用时长
        BiConsumer<PlanParameter, String> secondFunc = (parameter, value) -> parameter.setSecond(Integer.parseInt(value));
        functions.put("-s", secondFunc);
        functions.put("--second", secondFunc);

        // 5. 设置自定义参数信息
        BiConsumer<PlanParameter, String> propertyFunc = (parameter, value) -> {
            // key1=value1
            Map<String, String> property = parameter.getProperty();
            if (property == null) {
                property = new HashMap<>();
                parameter.setProperty(property);
            }

            int index = value.indexOf("=");
            if (index > 0) {
                property.put(value.substring(0, index), value.substring(index + 1));
            }
        };
        functions.put("-p", propertyFunc);
        functions.put("--property", propertyFunc);

        // 6. 设置输出文件路径
        BiConsumer<PlanParameter, String> outputFunc = (parameter, value) -> parameter.setOutput(value);
        functions.put("-o", outputFunc);
        functions.put("--output", outputFunc);
        return functions;
    }

    public static void main(String[] args) {
        Starter planStarter = new PlanStarter(args);
        planStarter.run();
    }
}
