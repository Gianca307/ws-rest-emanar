package ar.com.emanar.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotBlank(message = "La marca no puede quedar vacía.")
	private String marca;
	
	@NotBlank(message = "Variedad no puede quedar vacío.")
	private String variedad;
	
	@NotBlank(message = "Capacidad no puede quedar vacío.")
	private String capacidad;
	
	@Positive(message = "El costo del producto no puede ser número negativo")
	private Float costo;
	
	@Positive(message = "El precio del producto no puede ser un número negativo")
	private Float precio;
	
	@Positive(message= "Ean no puede ser número negativo")
	private Long ean;
	
	@Positive(message = "Stock no puede quedar con número negativo")
	private Integer stock;
	
	@NotNull(message = "Disponible no puede quedar con valor null")
	private boolean disponible;
	
	@NotNull(message = "Disponible no puede quedar con valor null")
	private boolean activo;
	
	@NotBlank(message = "Imagen no puede quedar vacía.")
	private String imgUrl;
	
	@ManyToOne
	@JoinColumn(name = "categoriaProducto_id", referencedColumnName = "id")
	private CategoriaProducto categoriaProducto;
	
}