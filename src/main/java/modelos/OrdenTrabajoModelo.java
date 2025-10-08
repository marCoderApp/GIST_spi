package modelos;

import java.time.LocalDateTime;
import java.util.List;

public class OrdenTrabajoModelo {
	
	private String ordenId;
	private String cliente_id;
	private List<MaquinaModelo> maquinas;
	private String descripcion_falla;
	private String detalleRepId;
	private LocalDateTime fechaIngreso;
	private LocalDateTime fechaRetiro = null;
	private String adminId;
	private String presupuesto_id;
	
	public OrdenTrabajoModelo(
			String ordenId, String cliente_id,
			List<MaquinaModelo> maquina, 
			String descripcion_falla,
			String detalleRepId,
			LocalDateTime fechaIngreso,
			LocalDateTime fechaRetiro,
			String adminId, String presupuesto_id
			) {
		
		
	}
	
	

}
