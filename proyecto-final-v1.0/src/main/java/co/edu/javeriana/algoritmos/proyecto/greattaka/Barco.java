package co.edu.javeriana.algoritmos.proyecto.greattaka;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.RespuestaJugada;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Barco{
    protected int nombre;
    protected int partes_count;
    protected Set<Casilla> partes;
    protected int partes_destruidas;
    protected Set<Casilla> partes_hundidas;

    public Barco(int nombre, int dimension) {
        this.nombre = nombre;
        this.partes_count = dimension;
        this.partes = new HashSet<>();
        this.partes_destruidas = 0;
        this.partes_hundidas = new HashSet<>();
    }

    public void addCasilla(Casilla casilla) {
        this.partes.add(casilla);
    }

    public void removeCasilla(Casilla casilla) {
        this.partes.remove(casilla);
    }

    public void registrarBarco(Map<Casilla, Barco> mapa_naval) {
        for (Casilla casilla : this.partes) {
            mapa_naval.put(casilla, this);
        }
    }

    public RespuestaJugada ataque(Casilla casilla) {
        this.partes_hundidas.add(casilla);
        if (this.partes.contains(casilla)) {
            if (this.partes_hundidas.size() < this.partes.size()) {
                return RespuestaJugada.IMPACTO;
            } else {
                return RespuestaJugada.HUNDIDO;
            }
        }
        return RespuestaJugada.AGUA;
    }

    public Boolean hundido() {
        return this.partes_hundidas.size() == this.partes.size();
    }

    public int getPartes_count() {
        return partes_count;
    }

    public Set<Casilla> getPartes() {
        return partes;
    }

    public void borrarFantasma() {
        this.partes = new HashSet<>();
    }

    @Override
    public String toString() {
        StringBuilder sc = new StringBuilder();
        sc.append(String.format("Barco %d: ", this.nombre));
        sc.append("[");
        for (Casilla c : this.getPartes()) {
            sc.append(String.format("{%d,%d}", c.getFila(), c.getColumna())).append(", ");
        }
        sc.append("]");
        return sc.toString();
    }
}
