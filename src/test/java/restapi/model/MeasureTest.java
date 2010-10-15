package restapi.model;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import restapi.model.Measure;

public class MeasureTest {

    @Test
    public void shouldSupportName() {
        Measure measure = new Measure("abc");
        assertThat(measure.getName(), equalTo("abc"));
    }
}
