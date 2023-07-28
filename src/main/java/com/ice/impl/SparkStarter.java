/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ice.impl;

import com.ice.Parameter;
import com.ice.Starter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SparkStarter extends Starter {
    private static final String HELP = "help";
    private static final String THREAD = "thread";
    private static final String COUNT = "count";
    private static final String SECOND = "second";
    private static final String PROPERTY = "property";
    private static final String OUTPUT = "output";
    private static final Map<String, String> SHORT_TO_LONG;
    private static final Set<String> OPTS;

    static {
        // 1. 缩写 -> 全写，方便后续统一处理
        SHORT_TO_LONG = new HashMap<>();
        SHORT_TO_LONG.put("t", THREAD);
        SHORT_TO_LONG.put("c", COUNT);
        SHORT_TO_LONG.put("s", SECOND);
        SHORT_TO_LONG.put("p", PROPERTY);
        SHORT_TO_LONG.put("o", OUTPUT);

        // 2. 限定我们自己的参数
        OPTS = new HashSet<>();
        OPTS.addAll(SHORT_TO_LONG.values());
    }

    private boolean isHelp;
    private int thread;
    private int count;
    private int second;
    private Map<String, String> property;
    private String output;

    public SparkStarter(String[] args) {
        super(args);
    }

    private void parse(String[] args) {
        /**
         * 3. 咱们也使用正则
         * group1：忽略前面的前缀 - 或 --，直接获取参数名
         * group2：获取实际的参数值
         */
        Pattern eqSeparatedOpt = Pattern.compile("-{1,2}([^=]+)=(.+)");

        for (int idx = 0; idx < args.length; idx++) {
            String arg = args[idx];

            // 4. 遇到 help 可以直接退出即可
            if(arg.equals("h") || arg.equals(HELP)){
                isHelp = true;
                return;
            }

            String value = null;
            Matcher m = eqSeparatedOpt.matcher(arg);
            if (m.matches()) {
                arg = m.group(1);
                value = m.group(2);
            }

            // 5. 校验参数名是否有效
            String name = findCliOption(arg);
            if (name != null) {
                if (value == null) {
                    if (idx == args.length - 1) {
                        throw new IllegalArgumentException(
                                String.format("Missing argument for option '%s'.", arg));
                    }
                    idx++;
                    value = args[idx];
                }

                // 6. 对于参数值的处理
                if (!handle(name, value)) {
                    break;
                }
            }

            // 这里可以进行容错操作
//            if (!handleUnknown(arg)) {
//                break;
//            }
        }
    }

    @Override
    protected Parameter parse() {
        // 7. 开始解析
        parse(args);

        // 8. 内部已经使用了成员变量存储，直接返回即可
        return new Parameter() {
            @Override
            public int getThread() {
                return thread;
            }

            @Override
            public int getCount() {
                return count;
            }

            @Override
            public int getSecond() {
                return second;
            }

            @Override
            public Map<String, String> getProperty() {
                return property;
            }

            @Override
            public String getOutput() {
                return output;
            }

            @Override
            public boolean isHelp() {
                return isHelp;
            }
        };
    }

    private String findCliOption(String name) {
        name = SHORT_TO_LONG.getOrDefault(name, name);
        return OPTS.contains(name) ? name : null;
    }

    private boolean handle(String opt, String value) {
        switch (opt) {
            case THREAD:
                thread = Integer.parseInt(value);
                return true;
            case COUNT:
                count = Integer.parseInt(value);
                return true;
            case SECOND:
                second = Integer.parseInt(value);
                return true;
            case OUTPUT:
                output = value;
                return true;
            case PROPERTY:
                property = new HashMap<>();
                int index = value.indexOf("=");
                if (index > 0) {
                    property.put(value.substring(0, index), value.substring(index + 1));
                }
                return true;
            default:
                return false;
        }
    }
}