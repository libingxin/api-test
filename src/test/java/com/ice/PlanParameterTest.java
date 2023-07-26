package com.ice;

import com.ice.impl.PlanStarter;
import org.junit.Test;

public class PlanParameterTest extends ParameterTest{
    @Test
    @Override
    public void startTest() {
        Starter starter = new PlanStarter(args);
        Parameter parameter = starter.parse();
        validate(parameter);
    }
}
