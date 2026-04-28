package com.stackburguer.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    public WebMvcConfig(){
//        System.out.println("🚀 ALERTA: A classe WebMvcConfig foi carregada com sucesso!");
//    }
//
//    public void addResourcerHandlers(ResourceHandlerRegistry registry) {
//        System.out.println("🛠️ EXECUTANDO: Configurando mapeamento de arquivos...");
//        String uploadPath = "file:./uploads/";
//
//        registry.addResourceHandler("/files/**")
//                .addResourceLocations(uploadPath)
//                .setCachePeriod(0);
//
//        System.out.println("DEBUG: Tentando ler imagens de: " + uploadPath);
//
//    }
//}
