package com.ice;

import org.junit.Assert;
import org.junit.Before;

public abstract class ParameterTest {
    private static final String CONNECT_TIMEOUT = "connectTimeout";
    private static final String READ_TIMEOUT = "readTimeout";
    protected String[] args;
    private int thread;
    private int count;
    private int second;
    private int connectTimeout;
    private int readTimeout;
    private String output;

    @Before
    public void before() {
        thread = 10;
        count = 20;
        second = 30;
        connectTimeout = 3;
        readTimeout = 10;
        output = "result.txt";

        args = new String[]{
                "-t", Integer.toString(thread),
                "-c", Integer.toString(count),
                "-s", Integer.toString(second),
                "-p", CONNECT_TIMEOUT + "=" + connectTimeout,
                "-p", READ_TIMEOUT + "=" + readTimeout,
                "-o", output
        };
    }

    public abstract void startTest();

    protected void validate(Parameter parameter) {
        Assert.assertEquals(thread, parameter.getThread());
        Assert.assertEquals(count, parameter.getCount());
        Assert.assertEquals(Integer.toString(connectTimeout), parameter.getProperty().get(CONNECT_TIMEOUT));
        Assert.assertEquals(Integer.toString(readTimeout), parameter.getProperty().get(READ_TIMEOUT));
        Assert.assertEquals(output, parameter.getOutput());
    }
}
