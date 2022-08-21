package com.biud436.rest.domain.post;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MyPost2")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPost {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "TITLE")
    private String title;
}
