package com.example.scoredei.data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@XmlRootElement
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date time;

    public Event() {

    }
    
    public Event(Date time){
        this.time = time;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Date getTime(){
        return this.time;
    }

    public void setTime(Date time){
        this.time = time;
    }
}
