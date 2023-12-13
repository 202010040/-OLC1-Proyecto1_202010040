package compi1.proyecto1;

public class Conjunto {
	 private EstadoAutomata Primera_Pos;
	 private EstadoAutomata Ultima_Pos;
	 
	 // Un conjunto es una clase que simplificara la logica
	 // de los nodos y trancisiones, el conjunto mas sencillo
	 // sera:
	 // S0 ------------- a  ------------> S1
	 public Conjunto(String trancision, int num_inicial) {
		 // Crea el primer nodo y su nodo siguiente
		 this.Primera_Pos = new EstadoAutomata(num_inicial);
		 this.Ultima_Pos = new EstadoAutomata(num_inicial + 1);
		 
		 // Conecta ambos nodos
		 this.Primera_Pos.SetEstadoSiguiente(Ultima_Pos, trancision);
	 }
	 
	 // ----------> Metodos get y set INICIO
	 public EstadoAutomata getPrimeraPos() {
		 return this.Primera_Pos;
	 }
	 
	 public EstadoAutomata getUltimaPos() {
		 return this.Ultima_Pos;
	 }	 
	 
	 public int get_primer_num() {
		 return this.Primera_Pos.getNumero_Estado();
	 }
	 
	 public int get_ultimo_num() {
		 return this.Ultima_Pos.getNumero_Estado();
	 }	 
	 
	 // ----------> Metodos get y set FIN
	 
	 // ----------> OPERACIONES SIMPLIFICADAS INICIO
	 public void Concatenar(Conjunto conj_siguiente) {
		 this.Ultima_Pos.Concatenar(this.Ultima_Pos, conj_siguiente.getPrimeraPos());
		 // Como lo concateno, la ultima es la ultima del siguiente conjutno
		 this.Ultima_Pos = conj_siguiente.getUltimaPos();
	 }
	 
	 public void OR_Conjunto(Conjunto conj_siguiente) {
		 // Hace Or y elnodo final del conjunto
		 this.Ultima_Pos = this.Primera_Pos.HacerOR(this.Ultima_Pos, conj_siguiente.getPrimeraPos(), conj_siguiente.getUltimaPos());
		 
	 }
	 
	 public void Kleene() {
		 this.Ultima_Pos = this.Primera_Pos.HacerKleene(this.Ultima_Pos);
	 }
	 // ----------> OPERACIONES SIMPLIFICADAS FIN
	 
	 // ----------> Recorrer
	 public void Recorrer () {
		 this.Primera_Pos.PrintEstados(this.Primera_Pos);
	 }
}
