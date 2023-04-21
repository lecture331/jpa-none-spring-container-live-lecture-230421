package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "course")
public class Course { // course 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String instructor;
    private double cost;

    public Course(String title, String instructor, double cost) {
        this.title = title;
        this.instructor = instructor;
        this.cost = cost;
    }

    public Course(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructor() {
        return instructor;
    }

    public double getCost() {
        return cost;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
