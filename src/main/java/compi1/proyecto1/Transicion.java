package compi1.proyecto1;

public class Transicion {
	/*
	 * Cada Trancision puntara a un estado
	 * 
	 * ---------- a --------> (S1)
	 *        Transicion      Estado
	 * */
	private String Nombre_Transicion;
	private EstadoAutomata Estado_Siguiente;
	public Boolean Recorrido;
	
   // Constructor
	public Transicion (String Nombre_Transicion, EstadoAutomata Estado_Siguiente ) {
		this.Nombre_Transicion = Nombre_Transicion;
		this.Estado_Siguiente = Estado_Siguiente;
		this.Recorrido = false;
	}
	
	// Metodos GET 
	public String getNombre_Transicion() {
		return this.Nombre_Transicion;
	}
	
	public EstadoAutomata getEstado_Siguiente() {
		return this.Estado_Siguiente;
	}
}
