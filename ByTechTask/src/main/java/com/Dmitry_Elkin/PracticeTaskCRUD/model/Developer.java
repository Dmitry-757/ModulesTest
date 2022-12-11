package com.Dmitry_Elkin.PracticeTaskCRUD.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Developer {

    private long id;
    private String firstName;
    private String lastName;
    private Set<Skill> skills;

    private Specialty specialty;
    private Status status;

    public Developer(String firstName, String lastName, HashSet<Skill> skills, Specialty specialty) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = skills;
        this.specialty = specialty;
        this.status = Status.ACTIVE;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public Status getStatus() {
        return status;
    }

    //*** for compatability with interface BaseModelsMethsI ***

    public String getName() {
        return getFirstName();
    }
    public void setName(String name) {
        setFirstName(name);
    }
    //*****************************************************

    public void setDeleted() {
        status = Status.DELETED;
    }

    public void setUnDeleted() {
        status = Status.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return id == developer.id && firstName.equals(developer.firstName) && lastName.equals(developer.lastName) && Objects.equals(skills, developer.skills) && specialty.equals(developer.specialty) && status == developer.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, skills, specialty, status);
    }

    @Override
    public String toString() {
        String  skillList = "";
        if (skills != null) {
            skillList = skills.stream().map(Object::toString).collect(Collectors.joining(", "));
        }
        return "Developer{" +
                "id=" + id +
                ", firstName = '" + firstName + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", skills = " +
                skillList+
                ", specialty = " + (specialty != null ? specialty.getName() : "SpecialtyLess ((") +
                ", status = " + status +
                '}';
    }
}
