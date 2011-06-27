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

package no.pritest.restapi.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Change {
	public String after;
	public String before;
	public no.pritest.restapi.model.Repository repository;
	public String ref;
	public String compare;
	public boolean forced;
	public Pusher pusher;
	public List<Commit> commits;

	public Change() {
	}

    public Change(String after, String before, Repository repository, String ref, String compare, boolean forced, Pusher pusher,
    		List<Commit> commits) {
        this.after = after;
        this.before = before;
        this.repository = repository;
        this.ref = ref;
        this.compare = compare;
        this.forced = forced;
        this.pusher = pusher;
        this.commits = commits;
    }

    public Pusher getPusher() {
		return pusher;
	}

	public void setPusher(Pusher pusher) {
		this.pusher = pusher;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getCompare() {
		return compare;
	}

	public void setCompare(String compare) {
		this.compare = compare;
	}

	public boolean isForced() {
		return forced;
	}

	public void setForced(boolean forced) {
		this.forced = forced;
	}

	public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }
    
    public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	
	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	public List<Commit> getCommits() {
		return commits;
	}

	public void setCommits(List<Commit> commits) {
		this.commits = commits;
	}

	@Override
	public String toString() {
		return "Change: " + this.after + " " + this.before + "\n" + this.repository.toString();
	}
}
