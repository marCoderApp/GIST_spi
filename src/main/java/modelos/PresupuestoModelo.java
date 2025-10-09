package modelos;

import java.time.LocalDateTime;

public class PresupuestoModelo {

	private String presupuestoId;
	private String ordenId;
	private LocalDateTime fechaCreacion;
	private String detalleRepId;
	private String adminId;
	private LocalDateTime fechaEstimada;
	private float total;
	private String estado;

	public PresupuestoModelo(String presupuestoId,
			String ordenId,
			LocalDateTime fechaCreacion,
			String adminId,
			String detalleRepId,
			LocalDateTime fechaEstimada,
			float total,
			String estado
			) {
		
		this.presupuestoId = presupuestoId;
		this.ordenId = ordenId;
		this.adminId = adminId;
		this.fechaCreacion = fechaCreacion;
		this.detalleRepId = detalleRepId;
		this.fechaEstimada = fechaEstimada;
		this.total = total;
		this.estado = estado;		
	}
	
	
	
	
}
