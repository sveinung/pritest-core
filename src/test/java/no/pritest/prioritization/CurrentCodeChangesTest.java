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

package no.pritest.prioritization;

import no.pritest.util.VCSStatusProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class CurrentCodeChangesTest {

    private CurrentCodeChanges currentCodeChanges;

    @Mock
    private VCSStatusProvider statusProvider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        currentCodeChanges = new CurrentCodeChanges(statusProvider);
    }

    @Test
    public void should_include_local_unchanged_classes_in_test_run() throws IOException {
        when(statusProvider.getGitStatusPriorityList()).thenReturn(
                Arrays.asList("no.SomeClassTest", "no.SomeOtherClassTest"));

        List<String> localTestCases = new ArrayList<String>();
        localTestCases.add("no.another.ClassTest");

        assertThat(currentCodeChanges.prioritize(localTestCases), is(equalTo(
                Arrays.asList("no.SomeClassTest", "no.SomeOtherClassTest", "no.another.ClassTest"))));
    }
}
