package no.citrus.restapi.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;


public class ChangeTest {

	@Test
    public void shouldSupportName() {
        Change change = new Change("abc");
        assertThat(change.getName(), equalTo("abc"));
    }
}
