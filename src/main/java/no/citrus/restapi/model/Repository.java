package no.citrus.restapi.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/*
 * "repository": {
	    "url": "http://github.com/defunkt/github",
	    "name": "github",
	    "description": "You're lookin' at it.",
	    "watchers": 5,
	    "forks": 2,
	    "private": 1,
	    "owner": {
	      "email": "citrus@citrus.no",
	      "name": "defunkt"
	    }
	  },
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Repository {
	@XmlElement
	public String url;
	@XmlElement
	public String name;
	@XmlElement
	public String description;
	@XmlElement
	public int watchers;
	@XmlElement
	public int forks;
	@XmlElement(name = "private")
	public int priv;

	public Repository() {}
	
	public Repository(String url, String name, String description,
			int watchers, int forks, int priv) {
		this.url = url;
		this.name = name;
		this.description = description;
		this.watchers = watchers;
		this.forks = forks;
		this.priv = priv;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getWatchers() {
		return watchers;
	}
	public void setWatchers(int watchers) {
		this.watchers = watchers;
	}
	public int getForks() {
		return forks;
	}
	public void setForks(int forks) {
		this.forks = forks;
	}
	
	public int getPriv() {
		return priv;
	}

	public void setPriv(int priv) {
		this.priv = priv;
	}
	
	@Override
	public String toString() {
		return "Repository: " + this.url + " " + this.name + " " + this.description + " " + this.watchers + " " + this.forks;
	}
}
