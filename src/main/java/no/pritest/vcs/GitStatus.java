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

package no.pritest.vcs;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class GitStatus implements VCSStatus {

    private Status status;

    public GitStatus(File directory) throws IOException {
        File repositoryPath = new File(directory.getAbsolutePath());
		RepositoryBuilder repositoryBuilder = new RepositoryBuilder();
        Repository repository = repositoryBuilder.findGitDir(repositoryPath).build();
        Git git = new Git(repository);
		status = git.status().call();
    }

    public Set<String> getUntracked() {
        return status.getUntracked();
    }

    public Set<String> getModified() {
        return status.getModified();
    }
}
