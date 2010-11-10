package no.citrus.restapi.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="measures")
public class MeasureList<T>{
    protected List<T> list;

    public MeasureList(){}

    public MeasureList(List<T> list){
        this.list=list;
    }

    @XmlElement(name="measure")
    public List<T> getList(){
        return list;
    }
}
