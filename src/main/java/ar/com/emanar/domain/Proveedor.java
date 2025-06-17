package ar.com.emanar.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Proveedor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotBlank(message = "Empresa no puede quedar vacía.")
	private String empresa;
	
	@NotBlank(message = "El nombre de contacto no puede quedar vacío.")
	private String nombreDelContacto;
	
	@NotBlank(message = "El número de contacto no puede quedar vacío.")
	private String numeroDeContacto;
	
	@NotBlank(message = "El número de cliente no puede quedar vacío.")
	private String numeroDeCliente;
	
	@NotBlank(message = "El rol de contacto no puede quedar vacío.")
	private String rolDelContacto;
	
	@NotBlank(message = "El día de entrega no puede quedar vacío.")
	private String diaDeEntrega;
	
	@NotBlank(message = "El día de visita no puede quedar vacío.")
	private String diaDeVisita;
	
}
