package com.ice.impl;

import com.ice.Parameter;
import com.ice.Starter;
import picocli.CommandLine;

public class PicocliStarter extends Starter {
    public PicocliStarter(String[] args) {
        super(args);
    }

    public Parameter parse() {
        PicocliParameter parameter = new PicocliParameter();
        CommandLine commandLine = new CommandLine(parameter);
        commandLine.parseArgs(args);
        return parameter;
    }

    public static void main(String[] args) {
        Starter starter = new PicocliStarter(args);
        starter.run();
    }
}
