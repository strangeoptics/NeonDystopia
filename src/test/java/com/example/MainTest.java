package com.example;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MainTest {

    @Test
    public void testMe() {

        Assertions.assertThat("this").isNotEqualTo("that");
    }
}
