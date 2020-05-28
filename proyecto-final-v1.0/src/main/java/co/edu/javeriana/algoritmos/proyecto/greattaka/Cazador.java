package co.edu.javeriana.algoritmos.proyecto.greattaka;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.RespuestaJugada;

import java.util.*;

public abstract class Cazador {
    protected Queue<PosibleAtaque> posibles_ataques;
    protected int dimension;
    protected double[][] probabilidad;
    //Primer casilla en la que golpeamos el barco
    protected Casilla primera_sangre;
    //Segunda casilla en la que golpeamos el barco, nos permite determinar la direccion del barco
    protected Casilla segunda_sangre;
    protected int distancia_maxima;
    protected List<PosibleAtaque> ataques_realizados;

    public Cazador(int dimension, double[][] probabilidad, Casilla primera_sangre, Casilla segunda_sangre) {
        this.posibles_ataques = new ArrayDeque<>();
        this.ataques_realizados = new ArrayList<>();
        this.dimension = dimension;
        this.distancia_maxima = (this.dimension / 2) + 2;
        this.probabilidad = probabilidad;
        this.primera_sangre = primera_sangre;
        this.segunda_sangre = segunda_sangre;
        this.ataques_realizados.add(new PosibleAtaque(primera_sangre, 1000));
        this.ataques_realizados.add(new PosibleAtaque(segunda_sangre, 999));
    }

    protected void direccionar_ataque(int i, int j, double factor) {
        Casilla casilla = new Casilla(i, j);
        if(!casilla.equals(segunda_sangre)){
            this.posibles_ataques.offer(new PosibleAtaque(new Casilla(i, j), factor));
        }
    }

    public Casilla siguienteAtaque() {
        PosibleAtaque siguiente = this.posibles_ataques.poll();
        if (siguiente != null) {
            this.ataques_realizados.add(siguiente);
            return siguiente.casilla;
        }
        return null;
    }

    public abstract void procesarRespuesta(RespuestaJugada respuesta);
}
