package com.biud436.rest.rest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MyPost {
    
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "TITLE")
    private String title;
}
