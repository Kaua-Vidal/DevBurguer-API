package com.stackburguer.api.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity      //Etiqueta: Isso é uma tabela no bando de dados
@Table(name = "products")
@Data        //Etiqueta do Lombok: Cria getters, setters e toString sozinha
public class Product {
    @Id               //Etiqueta: Essa é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // Auto-incremento
    private Long id;

    private String name;
    private double price;
    private String category;
    private String path;
}
