package no.citrus.restapi.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "change")
@XmlAccessorType(XmlAccessType.FIELD)
public class Change {
	public String after;
	public String before;
	public no.citrus.restapi.model.Repository repository;
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
