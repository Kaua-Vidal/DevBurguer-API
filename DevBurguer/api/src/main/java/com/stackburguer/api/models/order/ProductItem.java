package com.stackburguer.api.models.order;

import com.stackburguer.api.models.Category;

public record ProductItem(
        Long id,
        String name,
        Double price,
        Category category,
        String url,  //Link da imagem para o front
        int quantity
){}

//Aqui, salvamos uma "Snapshot" do produto da hora da compra
//pois se o preço for alterado depois, o pedido continuará com o mesmo preço ainda
