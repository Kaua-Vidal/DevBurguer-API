package com.stackburguer.api.models;



import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;

@Document(collection = "categories")  //Nome da "tabela" no mongo
@Data
public class Category {
    @Id
    private String id;
    private String name;
}
