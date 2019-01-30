package com.gestionabs.beans;

import javax.jws.WebService;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Classroom {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Integer id;
    @Size(min = 1,max = 30)
    @NotNull
    String name;
    @Size(min = 1,max = 30)
    @NotNull
    String block;
    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.REMOVE)
    private List<Session> sessions;

    public Classroom() {
    }


    public Classroom(String name, String block) {
        this.name = name;
        this.block= block;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
}
