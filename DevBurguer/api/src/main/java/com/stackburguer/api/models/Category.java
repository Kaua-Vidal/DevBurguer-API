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

    public String getId(){ return id; }
    public void setId(String id){this.id = id;}
    public String getName(){return name;}
    public void setName(String name) {this.name = name;}
}
