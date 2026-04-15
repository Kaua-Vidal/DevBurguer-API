package com.stackburguer.api.models;



import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;

@Document(collection = "categories")  //Nome da "tabela" no mongo
@Data
public class Category {
    @Id
    private String id;

    @NotBlank(message = "O nome da categoria é obrigatório no banco")
    private String name;

    private String path;

    public String getId(){ return id; }
    public void setId(String id){this.id = id;}
    public String getName(){return name;}
    public void setName(String name) {this.name = name;}
    public String getPath() {return path;}
    public void setPath(String path) {this.path = path;}
}
