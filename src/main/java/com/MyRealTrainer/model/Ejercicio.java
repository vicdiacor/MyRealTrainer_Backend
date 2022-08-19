package com.MyRealTrainer.model;
import javax.persistence.*;

@Entity
@Table(name = "ejercicios")
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Ejercicio() {
    }

    
    
    
}