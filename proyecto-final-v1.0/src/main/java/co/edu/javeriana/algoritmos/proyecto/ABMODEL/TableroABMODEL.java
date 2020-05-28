/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.algoritmos.proyecto.ABMODEL;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.RespuestaJugada;
import co.edu.javeriana.algoritmos.proyecto.ResumenTablero;
import co.edu.javeriana.algoritmos.proyecto.Tablero;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 *
 * @author Juan
 */
public class TableroABMODEL implements Tablero {
    
    private HashMap<Integer, ArrayList<Casilla> > mapaDeBarcos;
    private HashMap<Casilla, Pair<Integer, Boolean> > casillas;
    private int[][] tablero;
    private int dimension;

    public TableroABMODEL() {
        this.mapaDeBarcos = new HashMap<>();
        this.casillas = new HashMap<>();
    }

    public TableroABMODEL(int dimension) {
        this.mapaDeBarcos = new HashMap<>();
        this.casillas = new HashMap<>();
        this.tablero = new int[dimension][dimension];
        this.dimension = dimension;
    }

    public HashMap<Integer, ArrayList<Casilla>> getMapaDeBarcos() {
        return mapaDeBarcos;
    }

    public void setMapaDeBarcos(HashMap<Integer, ArrayList<Casilla>> mapaDeBarcos) {
        this.mapaDeBarcos = mapaDeBarcos;
    }

    public HashMap<Casilla, Pair<Integer, Boolean>> getCasillas() {
        return casillas;
    }

    public void setCasillas(HashMap<Casilla, Pair<Integer, Boolean>> casillas) {
        this.casillas = casillas;
    }

    public int[][] getTablero() {
        return tablero;
    }

    public void setTablero(int[][] tablero) {
        this.tablero = tablero;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

       
    @Override
    public ResumenTablero obtenerResumen() {
        int[] casillasOcupadasFilas = new int[this.dimension];
        int[] casillasOcupadasColumnas = new int[this.dimension];
        for(int i = 0; i < this.dimension; i++){
            for(int j = 0; j < this.dimension; j++){
                if(this.tablero[i][j] == 1){
                    casillasOcupadasFilas[i] += 1;
                }
            }
        }
        
        for(int i = 0; i < this.dimension; i++){
            for(int j = 0; j < this.dimension; j++){
                if(this.tablero[j][i] == 1){
                    casillasOcupadasColumnas[i] += 1;
                }
            }
        }
        ResumenTablero resultado = new ResumenTablero(casillasOcupadasFilas, casillasOcupadasColumnas);
        return resultado;
    }

    @Override
    public List<Casilla> obtenerCasillasOcupadasPorBarco(int numeroBarco) {
        return this.mapaDeBarcos.get(numeroBarco);
    }

    @Override
    public RespuestaJugada dispararACasilla(Casilla casilla) {
        if(this.casillas.get(casilla)!= null && !this.casillas.get(casilla).getValue()){
            Pair<Integer,Boolean> x = new Pair<>(this.casillas.get(casilla).getKey(),true);  
            this.casillas.replace(casilla,x);
            for (Casilla i : this.mapaDeBarcos.get(this.casillas.get(casilla).getKey())){
                if(i.equals(casilla)){
                    this.mapaDeBarcos.get(this.casillas.get(casilla).getKey()).remove(i);
                    break;
                }
            }
            if(this.mapaDeBarcos.get(this.casillas.get(casilla).getKey()).isEmpty()){
                return RespuestaJugada.HUNDIDO;
            }
            else{
                return RespuestaJugada.IMPACTO;
            }
        }
        else{
            return RespuestaJugada.AGUA;
        }
    }

    @Override
    public int numeroBarcosNoHundidos() {
        int cont = 0;
        for (int i = 0; i < this.mapaDeBarcos.size(); i++) {
           if(this.mapaDeBarcos.get(i)!=null) 
            if(this.mapaDeBarcos.get(i).size() > 0)
                cont++;
        }
        return cont;
    }
    
}
