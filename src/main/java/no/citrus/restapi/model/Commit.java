package no.citrus.restapi.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/*
   "commits": [
    {
      "added": [
        "test1\/README"
      ], 
      "author": {
        "email": "oyvindvol@gmail.com", 
        "name": "\u00d8yvind Voldsund"
      }, 
      "id": "c117410bae42b0cf7f97c6d5d1a09feeba02779c", 
      "message": "Added README to test1", 
      "modified": [], 
      "removed": [], 
      "timestamp": "2010-10-24T02:35:15-07:00", 
      "url": "http:\/\/github.com\/oyvindvol\/citrus-exp\/commit\/c117410bae42b0cf7f97c6d5d1a09feeba02779c"
    }
  ], 

 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Commit {
	public List<String> added;
	public String id;
	public String message;
	public List<String> modified;
	public List<String> removed;
	public String timestamp;
	public String url;
	public Author author;
	
	public Commit() {}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public List<String> getAdded() {
		return added;
	}

	public void setAdded(List<String> added) {
		this.added = added;
	}

	public List<String> getModified() {
		return modified;
	}

	public void setModified(List<String> modified) {
		this.modified = modified;
	}

	public List<String> getRemoved() {
		return removed;
	}

	public void setRemoved(List<String> removed) {
		this.removed = removed;
	}
}
