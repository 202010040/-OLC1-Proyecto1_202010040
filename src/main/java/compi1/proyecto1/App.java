package compi1.proyecto1;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws CloneNotSupportedException
    {

    	PruebaSimplificada();
    	//pruebaCombinada();
 

}
    public static void pruebaKleene() {
    	
      System.out.println( "Prueba 2 AFN Proyecto 1" );
      // Suponga que se tiene la expresion regular a*
      // Se crean las intancias para (S0) ----- a ------> (S1)
      EstadoAutomata inicial_a = new EstadoAutomata(0);
      EstadoAutomata final_a = new EstadoAutomata(1);
      inicial_a.SetEstadoSiguiente(final_a, "a");
      // Se hacen un OR
      inicial_a.HacerKleene(final_a);
      inicial_a.PrintEstados(inicial_a);
      System.out.println( "Fin Prueba 2 AFN Proyecto 1" );
      
    }
    
    public static void PruebaSimplificada() {
    // ab
      Conjunto conj_a = new Conjunto("a", 0);
      Conjunto conj_b = new Conjunto("b", conj_a.get_ultimo_num());
      conj_a.Concatenar(conj_b);
      System.out.println(conj_a.get_ultimo_num());
      System.out.println(conj_b.get_ultimo_num());
    // 1|2
      Conjunto conj_1 = new Conjunto("1", conj_a.get_ultimo_num() );
      Conjunto conj_2 = new Conjunto("2", conj_1.get_ultimo_num() + 1);
      conj_1.OR_Conjunto(conj_2);
    // ab(1|2)
      conj_a.Concatenar(conj_1);
    // 1|2 ( El que se va a hacer Kleene
      Conjunto conj_1_ = new Conjunto("1", conj_1.get_ultimo_num());
      Conjunto conj_2_ = new Conjunto("2", conj_1_.get_ultimo_num() + 1);
      conj_1_.OR_Conjunto(conj_2_);
    // (1|2)*
      conj_1_.Kleene();
    // ab(1|2)(1|2)*
      conj_a.Concatenar(conj_1_);
      //conj_a.Recorrer();
    }
    
    public static void pruebaCombinada() {
    	// ----------> Prueba 4
        System.out.println( "Prueba 4 AFN Proyecto 1" );
        int num = 0;
        // Suponga que se tiene la expresion regular ab(1|2)(1|2)*
        //Expresion para a
        EstadoAutomata inicial_a = new EstadoAutomata(num);
        num++;
        EstadoAutomata final_a   = new EstadoAutomata(num);
        
        inicial_a.SetEstadoSiguiente(final_a, "a");
        
        EstadoAutomata inicial_b = new EstadoAutomata(num);
        num++;
        EstadoAutomata final_b   = new EstadoAutomata(num);

        inicial_b.SetEstadoSiguiente(final_b, "b");
        
        final_a.Concatenar(final_a, inicial_b);
        
        System.out.println(num);
        
        EstadoAutomata inicial_1 = new EstadoAutomata(num);
        num++;
        EstadoAutomata final_1   = new EstadoAutomata(num);
        num++;
        inicial_1.SetEstadoSiguiente(final_1, "1");
        EstadoAutomata inicial_2 = new EstadoAutomata(num);
        num++;
        EstadoAutomata final_2   = new EstadoAutomata(num);
        num++;
        inicial_2.SetEstadoSiguiente(final_2, "2");
        // Se une el 1|2
        EstadoAutomata final_12 = inicial_1.HacerOR(final_1, inicial_2, final_2);
        final_b.Concatenar(final_b, inicial_1);
        // El numero es el correlativo del estado siguiente, pero se tomaran esos punteros
        num = final_12.getNumero_Estado();
        //    
        // Se hacen el (1|2)*
        EstadoAutomata inicial_1_ = new EstadoAutomata(num);
        num++;
        EstadoAutomata final_1_   = new EstadoAutomata(num);
        num++;
        inicial_1_.SetEstadoSiguiente(final_1_, "1");
        EstadoAutomata inicial_2_ = new EstadoAutomata(num);
        num++;
        EstadoAutomata final_2_   = new EstadoAutomata(num);
        num++;
        inicial_2_.SetEstadoSiguiente(final_2_, "2");
        // Se une el 1|2
        EstadoAutomata final_12_ = inicial_1_.HacerOR(final_1_, inicial_2_, final_2_);
        // Se une hace el (1|2)*
        EstadoAutomata final_12_K = inicial_1_.HacerKleene(final_12_);
        final_12.Concatenar(final_12, inicial_1_);
        inicial_1_.PrintEstados(inicial_a);
        System.out.println( "Fin Prueba 4 AFN Proyecto 1" );
    }

}
