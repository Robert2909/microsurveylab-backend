package com.microsurveylab.microsurveylabbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del backend (Spring Boot).
 * 
 * Aquí no hay lógica de negocio: solo levanta el contexto de Spring,
 * registra beans y arranca el servidor embebido para exponer la API REST.
 */
@SpringBootApplication
public class MicroSurveyLabBackendApplication {

	public static void main(String[] args) {
		// Arranca la aplicación y deja a Spring encargarse de la configuración automática.
		SpringApplication.run(MicroSurveyLabBackendApplication.class, args);
	}

}
