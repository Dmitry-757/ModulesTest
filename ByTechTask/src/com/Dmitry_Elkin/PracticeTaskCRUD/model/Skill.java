package com.Dmitry_Elkin.PracticeTaskCRUD.model;

import java.util.Objects;

public class Skill{
    private long id;
    private String Name;
    private Status status;


    public Skill(String name) {
        Name = name;
        status = Status.ACTIVE;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDeleted() {
        status = Status.DELETED;
    }

    public void setUnDeleted() {
        status = Status.ACTIVE;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return id == skill.id && Name.equals(skill.Name) && status == skill.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Name, status);
    }


    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", status=" + status +
                '}';
    }

    public Status getStatus() {
        return status;
    }
}
