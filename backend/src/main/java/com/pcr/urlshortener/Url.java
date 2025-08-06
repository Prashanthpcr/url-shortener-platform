package com.pcr.urlshortener; // Make sure this package name matches yours

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable; // Import the Serializable interface

@Entity
@Table(name = "urls")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Url implements Serializable { // Implement the Serializable interface

    // A version number for serialization. It's a good practice to add this.
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String longUrl;

    @Column(nullable = false, unique = true)
    private String shortCode;
}