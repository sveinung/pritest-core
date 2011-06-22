/**
    This file is part of Pritest.

    Pritest is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Pritest is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
*/

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
