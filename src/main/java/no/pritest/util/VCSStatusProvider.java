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
import org.eclipse.jgit.errors.NoWorkTreeException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class VCSStatusProvider {

    private File basedir;
    private final String sourceDirectory;
    private final String testSourceDirectory;
    VCSStatus status;

    public VCSStatusProvider(File basedir, String sourceDirectory, String testSourceDirectory, VCSStatus status) throws IOException {
        this.basedir = basedir;
        this.sourceDirectory = sourceDirectory.replace(basedir.getPath() + File.separator, "");
        this.testSourceDirectory = testSourceDirectory.replace(basedir.getPath() + File.separator, "");
        this.status = status;
    }

    public List<String> getGitStatusPriorityList() throws NoWorkTreeException, IOException {
        List<String> statusList = callStatus();
        List<String> finalList = new ArrayList<String>();

        finalList.addAll(statusList);

        return finalList;
    }

    public List<String> callStatus() throws IOException {
        List<String> gitStatusList = new ArrayList<String>();

        addTestCasesToList(status.getUntracked(), gitStatusList);
        addTestCasesToList(status.getModified(), gitStatusList);

        return gitStatusList;
    }

    private void addTestCasesToList(Set<String> changedFiles, List<String> statusList) {

        JavaPackageUtil jpu =
                new JavaPackageUtil(new String[]{sourceDirectory, testSourceDirectory,
                        basedir.getName() + File.separator + sourceDirectory,
                        basedir.getName() + File.separator + testSourceDirectory});

        for (String changedFile : changedFiles) {
            String testCaseName = jpu.prepareTestCaseName(changedFile);

            if (testCaseName != null && !statusList.contains(testCaseName)) {
                statusList.add(testCaseName);
            }
        }
    }
}
