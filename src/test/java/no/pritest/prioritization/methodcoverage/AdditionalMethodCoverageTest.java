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

package no.pritest.prioritization.methodcoverage;

import japa.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class AdditionalMethodCoverageTest {

    @Test
	public void should_give_a_list_of_test_cases_with_size_greater_than_zero() throws ParseException, IOException {
		AdditionalMethodCoverage tmc = new AdditionalMethodCoverage("src/main/java", "src/test/java");
		List<String> testCases = tmc.getTestCases();

		assertThat(testCases.size(), is(not(0)));
	}
}
