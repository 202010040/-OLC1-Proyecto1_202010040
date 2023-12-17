package compi1.proyecto1;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.MutableGraph;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Arbol {
	public String lex;
	public ArrayList<Arbol> hijos; // Esto va a ser recursivo
	public Boolean ReCorrelativoido;
        public Conjunto conjunto;
        
        
	public Arbol(String lex) {
		this.lex = lex;
		this.hijos = new ArrayList();
                this.ReCorrelativoido = false;
	}
	
	public void addHijo(Arbol hijo) {
		this.hijos.add(hijo);
	}
	
	public void printArbol(Arbol raiz) {
		// Se reCorrelativoe el arrayList 
		for(Arbol hijo : raiz.hijos) {
			// Se usa recursividad, reCorrelativoiendo los hijos del hijo mapeado
			printArbol(hijo);
		}
		// Se imprime el lexema del padre
		System.out.println(raiz.lex); 
	}
        
        private void graficar_arbol(Arbol a, MutableGraph g, int Correlativoelativo){
                // Como no es recursiva no necesita validaciones
                        Correlativoelativo ++;
			for (Arbol i : a.hijos ) {
				// Evita la recurividad, valida que el siguiente del siguiente no sea nulo o el mismo
					// Se imprime el estado actual y la transicion al siguiente
					
					if (i.ReCorrelativoido == true){
                                            g.add(Factory.mutNode(a.lex + "(" + Correlativoelativo + ")").addLink(Factory.to(Factory.mutNode(i.lex + "(" + (Correlativoelativo + 2) + ")")).with(Label.of("a"))));
                                        }
                                        else{
                                            g.add(Factory.mutNode(a.lex + "(" + Correlativoelativo + ")").addLink(Factory.to(Factory.mutNode(i.lex + "(" + (Correlativoelativo + 1) + ")")).with(Label.of("a"))));
                                        }
					
					graficar_arbol(i, g, Correlativoelativo);
                                        i.ReCorrelativoido = true;
		} 
        }
        
        public void Graficar(String nombre_grafo){
            MutableGraph g = Factory.mutGraph().setDirected(true);
            this.graficar_arbol(this, g, 0);
            // Renderizar el gráfico a un archivo
            try {
                Graphviz.fromGraph(g).width(1000).render(Format.PNG).toFile(new File(nombre_grafo));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        //-------> Convierte el arbol sintactico con un AFN con reCorrelativoido 
        private void convertir_a_conjunto (int Correlativo){
            // Valida si el actual es una operacion o un id
            switch (this.lex){
                // Si es una operacion convierte los hijos a conjuntos y luego los ordena
                case ".":
                    this.hijos.get(0).convertir_a_conjunto(Correlativo);
                    Correlativo = this.hijos.get(0).conjunto.get_ultimo_num();
                    this.hijos.get(1).convertir_a_conjunto(Correlativo);
                    this.hijos.get(0).conjunto.Concatenar(this.hijos.get(1).conjunto);
                    Correlativo = this.hijos.get(0).conjunto.get_ultimo_num();
                    this.conjunto = this.hijos.get(0).conjunto;
                    break;
                case "|": // En el caso del OR se debe sumar uno mas
                    this.hijos.get(0).convertir_a_conjunto(Correlativo);
                    Correlativo = this.hijos.get(0).conjunto.get_ultimo_num() + 1;
                    this.hijos.get(1).convertir_a_conjunto(Correlativo);
                    this.hijos.get(0).conjunto.OR_Conjunto(this.hijos.get(1).conjunto);
                    Correlativo = this.hijos.get(0).conjunto.get_ultimo_num();
                    this.conjunto = this.hijos.get(0).conjunto;
                    break;
                case "*":
                    this.hijos.get(0).convertir_a_conjunto(Correlativo);
                    this.hijos.get(0).conjunto.Kleene();
                    Correlativo = this.hijos.get(0).conjunto.get_ultimo_num();
                    this.conjunto = this.hijos.get(0).conjunto;
                    break;
                case "+": // a+ = aa*
                    this.hijos.get(0).convertir_a_conjunto(Correlativo);
                    Conjunto conj_auxiliar = new Conjunto(this.lex, this.hijos.get(0).conjunto.get_ultimo_num());
                    conj_auxiliar.Kleene();
                    this.hijos.get(0).conjunto.Concatenar(conj_auxiliar);
                    Correlativo = this.hijos.get(0).conjunto.get_ultimo_num();
                    this.conjunto = this.hijos.get(0).conjunto;
                    break;
                case "?": // a? = (a|e)*
                    this.hijos.get(0).convertir_a_conjunto(Correlativo);
                    Conjunto conj_auxiliar_ = new Conjunto("\u03B5", this.hijos.get(0).conjunto.get_ultimo_num() + 1);
                    this.hijos.get(0).conjunto.OR_Conjunto(conj_auxiliar_);
                    Correlativo = this.hijos.get(0).conjunto.get_ultimo_num();
                    this.conjunto = this.hijos.get(0).conjunto;
                    break;
                default:
                    this.conjunto = new Conjunto(this.lex, Correlativo);
                    Correlativo = this.conjunto.get_ultimo_num();
                    break;
            }
        }
        public void ObtenerAFN (String nombre_grafo){
            convertir_a_conjunto(0);
            this.conjunto.Graficar(nombre_grafo);
        }
}
