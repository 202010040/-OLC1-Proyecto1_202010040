package compi1.proyecto1;

import java.util.ArrayList;

/*
 * La idea es que esta clase funcione como un nodo y sea de la siguiente manera:
 *
 *	Clase TRANCISION
 *	          STRING nombre
 *	          NODO nodo1
 *	
 *	Clase NODO
 *	        INT numero_Estado
 *	        ARRAYLIST DE TRANCISION trancisiones //Puede estar vacio
 *	
 *	Cabe aclarar que un nodo puede tener o no trancisiones,  pero una trancision debe tener un nodo
 * 
 * 	/* Un nodo va a funcionar de la siguiente manera:
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
 * 7. Para las operaciones OR y KLEENE se retornara el estado final resultante para dicha operacion
 * 8. Se asume que los inicios no tienen predecesores y los finales no tienen sucesores
 * 
 * */

public class EstadoAutomata implements Cloneable {

	private Boolean                Final;
	private int                    Numero_Estado;
	private Boolean                Recorrido;
	private ArrayList <Transicion> Transiciones;
	
	// Constructor
	public EstadoAutomata(int Numero_Estado) {
		this.Numero_Estado = Numero_Estado;
		this.Final = false;
		this.Recorrido = false;
		this.Transiciones = new ArrayList();
	}
	//--------------> Metodos Get para cada una de las clases INICIO
	public int getNumero_Estado () {
		return this.Numero_Estado;
	}
	
	public Boolean getFinal () {
		return this.Final;
	}
	
	public Boolean getRecorrido() {
		return this.Recorrido;
	}
	
	//Obtiene el siguiente inmediato 
	public EstadoAutomata getSiguiente() {
		return this.Transiciones.get(0).getEstado_Siguiente();
	}
	
	//Clona la instancia actual
	public EstadoAutomata Clonar() {
		EstadoAutomata clon = new EstadoAutomata(this.getNumero_Estado());
		clon.Final = this.Final;
		clon.Recorrido = this.Recorrido;
		for (Transicion i : this.Transiciones) {
			clon.Transiciones.add(i);
		}
		return clon;
	}
	//--------------> Metodos Get para cada una de las clases FINAL
	

	//--------------> Metodos Set para cada una de las clases INICIO
	public void setNumero_Estado (int Numero_Estado) {
		this.Numero_Estado = Numero_Estado;
	}
	
	public void setFinal (Boolean Final) {
		this.Final = Final;
	}
	
	public void setRecorrido (Boolean Recorrido) {
		this.Recorrido = Recorrido;
	}
	//--------------> Metodos Set para cada una de las clases FINAL
	
	//-------------> Metodos auxiliares INICIO
	
	// Metodo para sumar uno a todos los automatas que esten en esa cadena
	public void Recorrer_y_Sumar(EstadoAutomata estado) {
	    // Si el estado ya ha sido recorrido, retornamos
	    if (estado.getRecorrido()) {
	        return;
	    }

	    // Marcamos el estado como recorrido
	    estado.setRecorrido(true);

	    // Sumamos 1 al campo Numero_Estado
	    estado.setNumero_Estado(estado.getNumero_Estado() + 1);

	    // Recorremos todas las transiciones del estado
	    for (Transicion i : estado.Transiciones) {
	        // Recorremos el estado siguiente de la transición
	        Recorrer_y_Sumar(i.getEstado_Siguiente());
	    }
	}

	
	// Metodo para regresar el recorrido a false
	public void Recorrer_y_SetFalse(EstadoAutomata estado) {
	    // Si el estado ya ha sido recorrido, retornamos
	    if (!estado.getRecorrido()) {
	        return;
	    }

	    // Establecemos el campo Recorrido del estado a false
	    estado.setRecorrido(false);

	    // Recorremos todas las transiciones del estado
	    for (Transicion i : estado.Transiciones) {
	        // Establecemos el campo Recorrido de la transición a false
	        i.Recorrido = false;

	        // Recorremos el estado siguiente de la transición
	        Recorrer_y_SetFalse(i.getEstado_Siguiente());
	    }
	}

	
	
	//-------------> Metodos auxiliares FIN 
	
	//-------------> Metodo para recorrer los nodos 
	public void PrintEstados (EstadoAutomata estado) {
		// Se recorre el ArrayList si aun no ha sido recorrido
		if (estado.getRecorrido() == false ) {
			for (Transicion i : estado.Transiciones) {
				// Evita la recurividad, valida que el siguiente del siguiente no sea nulo o el mismo
				if (i.Recorrido == false) {
					// Se imprime el estado actual y la transicion al siguiente
					System.out.println(
							estado.getNumero_Estado()  + 
							" --- " + 
							i.getNombre_Transicion() + 
						    " ---> " + 
						    i.getEstado_Siguiente().getNumero_Estado() +
						    " ");
					i.Recorrido = true;
					PrintEstados(i.getEstado_Siguiente());
				}
				
			}
			
			// Una vez recorrido todos los estados se indica que ya se finalizo el recorrido
			// Esto para evitar los ciclos sin fin
			estado.setRecorrido(true);
		}
	}
	
	
	/*-------------> Unir INICIO
	 * La union es simplemente crear una nueva instancia
	 * y que el siguiente apunte a ella
	 * */
	
	public void SetEstadoSiguiente(EstadoAutomata estado_siguiente, String transicion) {
		// Crea una nueva transicion, hace que apunte al nodo siguiente y la agrega al actual nodo
		Transicion Nueva_Trancision = new Transicion(transicion, estado_siguiente);
		this.Transiciones.add(Nueva_Trancision);
	}
	//-------------> Unir FIN
	
	
	/*-------------> Metodo para concatenar INICIO
	 * La concatenacion es el apuntado que toma una transicion ya predefinida y la cambia
	 * (Se asume que S0 es inicial y S2 es final
	 * 
	 * TR 1. 	(S0)                --------a-------->      (S1)
	 * TR 2.    (Sx)                --------b-------->      (S2)
	 *       
	 * Paso 1. Toma el puntero o los punteros de Sx y se lo asigna a S1
	 * 
	 * (S0) ------ a ------> (S1) ------- b -------> (S2)
	 * 
	 * */
	// Asume this = S0
	// No importa que objeto concatene, lo que importan son los argumentos
	public void Concatenar(EstadoAutomata a_final, 
						   EstadoAutomata b_inicial) {
		// Paso 1
		a_final.Transiciones = b_inicial.Transiciones;
	}
	//-------------> Metodo para concatenar FIN
	
	
	//-------------> Metodo para OR INICIO
	/*
	 * Las trancisiones para los OR se pueden resumir en concatenaciones con EPSILON
	 * Por ejemplo se tienen
	 * TR 1. 	(S0)                --------a-------->      (S1)
	 * TR 2. 	(S2)                --------b-------->      (S3)
	 * 
	 * Donde S0, S1 son los estados iniciales y finales del conjunto a
	 *     y S2, S3 son los estados iniciales y finales del conjunto b
	 * El procedimiento (a|b) para el OR seria el siguiente: 
	 * 
	 * 1. Se crea una copia de S0 que sera el predecesor de a y S0 pasara a ser el nodo inicial y se limpia S0
	 * 
	 * 2. Se toma el numero del predecesor de a y se le suma 1, 
	 * tambien se le suma 1 al estado final 
	 * (S1) ------ a --------- > (S2)
	 * 
	 * 3. Se repite el paso 2 con b
	 * (S3) ----b-----> (S4)
	 * 
	 * 4. S0 se conecta con el predecesor de a y con el predecesor de b, mediante epsilon
	 * Nueva_trancision_1 = e ----> (S1)
	 * Nueva_trancision_2 = e ----> (S3)
	 * 
	 * S0.add(Nueva_trancision_1)
	 * S0.add(Nueva_trancision_2)
	 * 
	 * 				 -----> (S1) ---a-----> (S2)
	 * 				/e
	 * Inicial (SO)
	 * 				\e
	 * 				 ----> (S3) ----b-----> (S4)
	 * 
	 * 5. Se crea una instancia, cuyo numero es uno mayor que la instancia posterior de b
	 * (S5)
	 * 
	 * 6. se crean trancisiones hacia ese nodo y se apuntan
	 * Nueva_trancision_3 = e-------> (S5)
	 * final_a.add(Nueva_trancision_3)
	 * final_b.add(Nueva_trancision_3)
	 *   			 -----> (S1) ---a-----> (S2) \
	 * 				/e                            \
	 * Inicial (SO)									(S5)
	 * 				\e                            /
	 * 				 ----> (S3) ----b-----> (S4) /
	 * 
	 * 
	 * 7. Se retorna el nodo final
	 * */
	// Se asume que el estado actual es S0, es decir que this = a.inicial
	public EstadoAutomata HacerOR(EstadoAutomata a_final, 
			                      EstadoAutomata b_inicial, 
			                      EstadoAutomata b_final) {
		
		// Paso 1, se crea una copia de la instancia
		EstadoAutomata copia_reemplazo = this.Clonar();
		// Limpia el arreglo para no apuntar a nada
		this.Transiciones.clear();
		
		// Paso 2, suma 1 a la trancision "a"
		copia_reemplazo.Recorrer_y_Sumar(copia_reemplazo);
		copia_reemplazo.Recorrer_y_SetFalse(copia_reemplazo);
		
		// Paso 3, suma 1 a la la trancision "b"
		b_inicial.Recorrer_y_Sumar(b_inicial);
		b_inicial.Recorrer_y_SetFalse(b_inicial);
		
		// Paso 4, se conecta la S0 con inicial_a e inicial_b
		this.SetEstadoSiguiente(copia_reemplazo, "Epsilon");
		this.SetEstadoSiguiente(b_inicial, "Epsilon");
		
		// Paso 5, crear el nodo final
		int num_final_b = b_final.getNumero_Estado() + 1;
		EstadoAutomata nodo_final = new EstadoAutomata(num_final_b);
		
		// Paso 6, conectar el final de a y b con el nodo final
		a_final.SetEstadoSiguiente(nodo_final, "Epsilon");
		b_final.SetEstadoSiguiente(nodo_final, "Epsilon");
		
		// Paso 7, retornar una tupla de objetos
		return  nodo_final;
	}
	//-------------> Metodo para OR FIN
	
	
	//-------------> Metodo para cleene INICIO
	/*
	 * Suponer que a es un conjunto de la manera
	 * (S0) --------- a -----------> (S1)
	 * prev_a         a              final_a
	 * 
	 * Paso 1.
	 * Crear una copia de prev_a y prev_a se convierte en el nodo inicial
	 * 
	 * (S0) (S0_copia) --------- a -----------> (S1)
	 * 
	 * Paso 2. 
	 * Sumarle 1 al los estados de a
	 * 
	 * (S0) (S1) --------- a -----------> (S2)
	 * 
	 * Paso 3.
	 * Crear un nodo final con el estado final_a + 1
	 * nodo_final = S3
	 * 
	 * (S0) (S1) --------- a -----------> (S2) (S3)
	 * 
	 * Paso 4.
	 * Conectar final_a ---Epsilon --> copia
	 * 
	 * (S0) (S1) --------- a -----------> (S2) (S3)
	 *        ^                            |
	 *         ----------------------------
	 *  
	 * Paso 5.
	 * Conectar S0 con S3
	 *  (S0) (S1) --------- a -----------> (S2) (S3)
	 *    |    ^                            |     ^
	 *    |    ----------------------------       |
	 *    ----------------------------------------
	 * 
	 * Paso 6. Conectar (S0) con (S1) y (S2) con (S3)
	 * 
	 *  (S0)->(S1) --------- a -----------> (S2)->(S3)
	 *    |    ^                            |     ^
	 *    |    ----------------------------       |
	 *    ----------------------------------------
	 *    
	 * Paso 7. Setea el nodo actual al nodo inicial, se guarda un nodo temporal y devielve el nodo final
	 * 
	 * */
	public EstadoAutomata HacerKleene(EstadoAutomata estado_final) {
		// Paso 1, se crea una copia de la instancia, con la excepcion de copia no soportada
		EstadoAutomata copia_reemplazo = this.Clonar();
		// Limpia el arreglo para no apuntar a nada
		this.Transiciones.clear();
		
		// Paso 2
		copia_reemplazo.Recorrer_y_Sumar(copia_reemplazo);
		copia_reemplazo.Recorrer_y_SetFalse(copia_reemplazo);
		
		// Paso 3
		int num_final = estado_final.getNumero_Estado() + 1;
		EstadoAutomata nodo_final = new EstadoAutomata(num_final);
		
		// Paso 4
		estado_final.SetEstadoSiguiente(copia_reemplazo, "Epsilon");
		
		// Paso 5
		this.SetEstadoSiguiente(nodo_final, "Epsilon");
		
		// Paso 6
		this.SetEstadoSiguiente(copia_reemplazo, "Epsilon");
		estado_final.SetEstadoSiguiente(nodo_final,  "Epsilon");
		
		// Paso 7, retornar el nodo final
		return  nodo_final;
		
	}
	//-------------> Metodo para cleene FIN
	
}
