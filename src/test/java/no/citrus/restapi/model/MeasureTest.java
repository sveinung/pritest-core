package no.citrus.restapi.model;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import no.citrus.restapi.model.Measure;

import org.junit.Test;


public class MeasureTest {

    @Test
    public void should_support_name() {
        Measure measure = new Measure();
        measure.setName("abc");
        assertThat(measure.getName(), equalTo("abc"));
    }
}
