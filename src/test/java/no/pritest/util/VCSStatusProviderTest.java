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

package no.pritest.util;

import no.pritest.vcs.VCSStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class VCSStatusProviderTest {

    private VCSStatusProvider provider;

    @Mock
    private VCSStatus status;
    @Mock
    private File basedir;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        
        when(basedir.getPath()).thenReturn("basedir/a");
        when(basedir.getName()).thenReturn("a");
    }

    @Test
    public void should_return_list_of_tests_to_run_given_status() throws IOException {
        HashSet<String> modified = new HashSet<String>();
        modified.add("src/test/java/no/ATest.java");
        modified.add("src/test/java/no/b/B.java");

        when(status.getModified()).thenReturn(modified);
        when(status.getUntracked()).thenReturn(new HashSet<String>());

        String testSrcDir = "basedir/a/src/test/java";
        String srcDir = "basedir/a/src/main/java";

        provider = new VCSStatusProvider(basedir, srcDir, testSrcDir, status);

        assertThat(provider.getGitStatusPriorityList(),
                hasItems("no.ATest", "no.b.BTest"));
    }
}
