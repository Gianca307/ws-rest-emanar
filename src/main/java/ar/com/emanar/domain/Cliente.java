package ar.com.emanar.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotBlank(message = "El nombre no puede quedar vacío.")
	private String nombre;
	
	@NotBlank(message = "La dirección no puede quedar vacía.")
	private String direccion;
	
	@NotBlank(message = "El número de celular no puede quedar vacío.")
	private String numeroDeCelular;
	
}
