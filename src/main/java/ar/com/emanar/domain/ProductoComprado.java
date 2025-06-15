package ar.com.emanar.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoComprado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Positive(message = "La cantidad debe ser  mayor o igual a 0")
	private Integer cantidad;
	
	@Positive(message = "El precio debe ser  mayor o igual a 0")
	private Float precioEstablecido;
	
	@ManyToOne
	@JoinColumn(name = "producto_id", referencedColumnName = "id")
	private Producto producto;
	
	@ManyToOne
	@JoinColumn(name = "gasto_id", referencedColumnName = "id")
	private Gasto gasto;
}
