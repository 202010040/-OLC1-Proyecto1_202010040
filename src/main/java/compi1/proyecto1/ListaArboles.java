/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi1.proyecto1;

import java.util.ArrayList;

/**
 *
 * @author Evelio_Cruz
 */
public class ListaArboles {
    public ArrayList<Arbol> Arboles;
    public ArrayList<ListaArboles> Siguientes;
    public ListaArboles(){
        this.Arboles = new ArrayList();
        this.Siguientes = new  ArrayList();
    }
    public void addArbol(Arbol a){
        this.Arboles.add(a);
    }
    public void addSig(ListaArboles ls){
        this.Siguientes.add(ls);
    }
    public void GraficarArboles(ListaArboles lista, String nombre, int correlativo){
        for (Arbol i: lista.Arboles){
            i.ObtenerAFN( nombre + correlativo + "");
            correlativo ++;
        }
        for (ListaArboles ls: lista.Siguientes ){
            correlativo ++;
            ls.GraficarArboles(ls, nombre, correlativo);
        }

    }
}
