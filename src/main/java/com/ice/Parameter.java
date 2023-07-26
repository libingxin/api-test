package com.ice;

import java.util.Map;

public interface Parameter {
    int getThread();
    int getCount();
    int getSecond();
    Map<String, String> getProperty();
    String getOutput();
    boolean isHelp();
}
