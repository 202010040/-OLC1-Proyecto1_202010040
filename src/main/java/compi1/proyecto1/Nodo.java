package compi1.proyecto1;

import java.util.ArrayList;

public class Nodo {
	/* Un nodo va a funcionar de la siguiente manera:
	 * 
	 * (S0)                --------a-------->      (S1)
	 * Numero de estado    Transicion (Si hay)      Estado siguiente (O nodo vacio para el final)
	 * 
	 * 1. Todos los estados van a tener un estado inicial, el cual va a ser el mismo
	 * 2. Todos los estados pueden tener o no un estado siguiente, el cual es un ArrayList de nodos
	 * 3. Puede ser o no un estado final, por defecto no sera final
	 * 4. La trancision puede llevarlo a un estado siguiente o a si mismo dependiendo si es final o no
	 * 5. Para evitar recursividad infinita tendra un booleano para ver si ya fue recorrido
	 * 6. En caso necesite un estado final se crea un nodo vacio
	 * 
	 * */
	private int Numero_Estado;
	private String Transicion;
	private ArrayList <Nodo> Siguientes;
	private Boolean Final;
	// Estado para saber si ha sido recorrido o no
	private Boolean Recorrido;
	
	public Nodo(int Numero_Estado) {
		this.Numero_Estado = Numero_Estado;
		this.Final = false;
		this.Siguientes = new ArrayList();
		this.Transicion = "";
	}
	
	//--------------> Metodos Get para cada una de las clases INICIO
	public int getNumero_Estado () {
		return this.Numero_Estado;
	}
	
	public String getTransicion () {
		return this.Transicion;
	}
	
	public Boolean getFinal () {
		return this.Final;
	}
	//--------------> Metodos Get para cada una de las clases FINAL
	
	
	
	//--------------> Metodos Set para cada una de las clases INICIO
	public void setNumero_Estado (int Numero_Estado) {
		this.Numero_Estado = Numero_Estado;
	}
	
	public void setTransicion (String Transicion) {
		this.Transicion = Transicion;
	}
	
	public void setFinal (Boolean Final) {
		this.Final = Final;
	}
	//--------------> Metodos Set para cada una de las clases FINAL
	
	
	//--------------> Metodos Para concatenar INICIO
	/*
	 * La concatenacion es simplemente crear una nueva instancia
	 * y que el siguiente apunte a ella
	 * */
	public void concatenar(Nodo nodo_siguiente) {
		this.Siguientes.add(nodo_siguiente);
	}
	//--------------> Metodos Para concatenar FIN
	
	
	//--------------> Metodos Para Crear un OR INICIO
	/*
	 * Las trancisiones para los OR se pueden resumir en concatenaciones con EPSILON
	 * Por ejemplo se tienen
	 * Nodo 1. 	(S0)                --------a-------->      (S1)
	 * Nodo 2. 	(S2)                --------b-------->      (S3)
	 * El procedimiento (a|b) para el OR seria el siguiente: 
	 * 
	 * 1. Se toma el numero de a (en este caso 0) y se crea una nueva instancia (S0)
	 * 2. Se toma el numero de la isntancia y se le suma 1 para Crear el nodo de transicion Epsilon
	 * 3. E
	 * 
	 * 				 -----> (S1) ---a-----> (S2)
	 * 				/e
	 * Inicial (SO)
	 * 				\e
	 * 				 ----> (S3) ----b-----> (S4)
	 * */
	//--------------> Metodos Para Crear un OR FIN
	
	
}
