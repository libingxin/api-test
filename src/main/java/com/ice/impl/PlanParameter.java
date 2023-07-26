package com.ice.impl;

import com.ice.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class PlanParameter implements Parameter {
    private int thread;
    private int count;
    private int second;
    private Map<String, String> property;
    private String output;
    private boolean isHelp;
}
