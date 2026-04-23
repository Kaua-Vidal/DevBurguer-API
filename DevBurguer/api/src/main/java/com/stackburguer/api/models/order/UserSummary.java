package com.stackburguer.api.models.order;

import java.util.UUID;

public record UserSummary(
        String id,
        String name
) {}

//Aqui guardamos apenas o ID e o Name do cliente para
//facilitar a vida do ADMIN
