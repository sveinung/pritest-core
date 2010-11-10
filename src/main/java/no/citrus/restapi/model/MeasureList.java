package no.citrus.restapi.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="measures")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class MeasureList{
    protected List<Measure> list;

    public MeasureList(){}

    public MeasureList(List<Measure> list){
        this.list=list;
    }

    @XmlElement(name="measure")
    public List<Measure> getList(){
        return list;
    }
    
    public void setList(List<Measure> list) {
    	this.list = list;
    }
}
