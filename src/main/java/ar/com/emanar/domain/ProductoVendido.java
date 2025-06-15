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
public class ProductoVendido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Positive(message = "La cantidad no puede ser un número negativo.")
	private Integer cantidad;
	
	@Positive(message = "El precio no puede ser un número negativo.")
	private Float precioEstablecido;
	
	@ManyToOne
	@JoinColumn(name = "producto_id")
	private Producto producto;
	
	@ManyToOne
	@JoinColumn(name = "venta_id", referencedColumnName = "id")
	private Venta venta;
	
}
