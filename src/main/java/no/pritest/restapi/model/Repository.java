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
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Repository {
	@XmlElement (name = "created_at")
	public String createdAt;
	public String url;
	public String name;
	public String description;
	public int watchers;
	public int forks;
	public boolean fork;
	@XmlElement (name = "has_downloads")
	public boolean hasDownloads;
	@XmlElement (name = "has_issues")
	public boolean hasIssues;
	@XmlElement (name = "has_wiki")
	public boolean hasWiki;
	public String homepage;
	@XmlElement(name = "private")
	public boolean priv;
	public Owner owner;
	@XmlElement (name = "pushed_at")
	private String pushedAt;
	@XmlElement (name = "open_issues")
	private int openIssues;

	public Repository() {}
	
	public Repository(String createdAt, String url, String name, String description,
			int watchers, int forks, boolean fork, boolean hasDownloads, boolean hasIssues, boolean hasWiki, String homepage,
			boolean priv, Owner owner, String pushedAt, int openIssues) {
		
		this.createdAt = createdAt;
		this.url = url;
		this.name = name;
		this.description = description;
		this.watchers = watchers;
		this.forks = forks;
		this.fork = fork;
		this.hasDownloads = hasDownloads;
		this.hasIssues = hasIssues;
		this.hasWiki = hasWiki;
		this.homepage = homepage;
		this.priv = priv;
		this.owner = owner;
		this.pushedAt = pushedAt;
		this.openIssues = openIssues;
	}
	
	public int getOpenIssues() {
		return openIssues;
	}

	public void setOpenIssues(int openIssues) {
		this.openIssues = openIssues;
	}

	public String getPushedAt() {
		return pushedAt;
	}

	public void setPushedAt(String pushedAt) {
		this.pushedAt = pushedAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isFork() {
		return fork;
	}

	public void setFork(boolean fork) {
		this.fork = fork;
	}

	public boolean isHasDownloads() {
		return hasDownloads;
	}

	public void setHasDownloads(boolean hasDownloads) {
		this.hasDownloads = hasDownloads;
	}

	public boolean isHasIssues() {
		return hasIssues;
	}

	public void setHasIssues(boolean hasIssues) {
		this.hasIssues = hasIssues;
	}

	public boolean isHasWiki() {
		return hasWiki;
	}

	public void setHasWiki(boolean hasWiki) {
		this.hasWiki = hasWiki;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
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
	
	
	public boolean isPriv() {
		return priv;
	}

	public void setPriv(boolean priv) {
		this.priv = priv;
	}

	@Override
	public String toString() {
		return "Repository: " + this.url + " " + this.name + " " + this.description + " " + this.watchers + " " + this.forks;
	}
}
