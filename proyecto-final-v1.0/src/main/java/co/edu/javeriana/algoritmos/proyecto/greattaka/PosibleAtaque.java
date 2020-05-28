package co.edu.javeriana.algoritmos.proyecto.greattaka;

import co.edu.javeriana.algoritmos.proyecto.Casilla;

public class PosibleAtaque implements Comparable<Object> {
    Casilla casilla;
    double valor;

    public PosibleAtaque(Casilla casilla, double valor) {
        this.casilla = casilla;
        this.valor = valor;
    }

    @Override
    public int compareTo(Object o) {
        PosibleAtaque otro = (PosibleAtaque) o;
        if(otro.valor == this.valor){
            return 0;
        }else if(otro.valor > this.valor){
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public String toString() {
        return String.format("({%d,%d},%f)", this.casilla.getFila(), this.casilla.getColumna(), this.valor);
    }
}
