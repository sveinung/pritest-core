package no.citrus.restapi.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "change")
@XmlAccessorType(XmlAccessType.FIELD)
public class Change {
	@XmlElement
	public String after;
	@XmlElement
	public String before;
	@XmlElement
	public no.citrus.restapi.model.Repository repository;

	public Change() {
	}

    public Change(String after, String before, Repository repository) {
        this.after = after;
        this.before = before;
        this.repository = repository;
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

	@Override
	public String toString() {
		return "Change: " + this.after + " " + this.before + "\n" + this.repository.toString();
	}
}
