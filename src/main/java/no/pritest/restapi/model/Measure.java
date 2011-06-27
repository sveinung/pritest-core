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
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Measure implements Comparable<Measure> {
	public String source;
	public String name;
	@XmlElementWrapper(name = "children")
	@XmlElement(name = "measure")
	public List<Measure> children;
	public double value;
	public Category category;
	public Date date;
	public boolean failed;
	public int numOfFails;

	public Measure() {}
    
    public Measure(String source, Date date, List<Measure> children, int numOfFails) {
    	this.source = source;
    	this.date = date;
    	this.children = children;
    	this.numOfFails = numOfFails;
    }
    
    public Measure(String name) {
    	this.name = name;
    }
    
    public int getNumOfFails() {
		return numOfFails;
	}

	public void setNumOfFails(int numOfFails) {
		this.numOfFails = numOfFails;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public List<Measure> getChildren() {
		return children;
	}

	public void setChildren(List<Measure> children) {
		this.children = children;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}
	
	public int compareTo(Measure arg) {
		return this.numOfFails - arg.getNumOfFails();
	}
}
