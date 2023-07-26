package com.ice;

import com.ice.impl.CommonCLIStarter;
import org.junit.Test;

public class CLIParameterTest extends ParameterTest {
    @Test
    @Override
    public void startTest() {
        Starter starter = new CommonCLIStarter(args);
        Parameter parameter = starter.parse();
        validate(parameter);
    }
}
