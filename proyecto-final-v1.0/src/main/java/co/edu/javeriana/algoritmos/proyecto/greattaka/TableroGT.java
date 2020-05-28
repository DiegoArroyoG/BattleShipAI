package co.edu.javeriana.algoritmos.proyecto.greattaka;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.RespuestaJugada;
import co.edu.javeriana.algoritmos.proyecto.ResumenTablero;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TableroGT implements Tablero {

    protected Map<Casilla, Barco> mapa_naval;
    protected Map<Integer, Barco> flota;
    protected int dimension;

    protected boolean verificarCasilla(Casilla casilla) {
        int fila = casilla.getFila();
        int columna = casilla.getColumna();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Casilla casillaAux = new Casilla(fila + i, columna + j);
                if (this.mapa_naval.get(casillaAux) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public TableroGT(int dimension, int[] barcos) {
        this.dimension = dimension;
        this.mapa_naval = new HashMap<>();
        this.flota = new HashMap<>();
        for (int i = 0; i < barcos.length; i++) {
            Barco barco = new Barco(i, barcos[i]);
            this.flota.put(i, barco);
        }
        for (Barco barco : this.flota.values()) {
            ThreadLocalRandom gen = ThreadLocalRandom.current();
            int tam = barco.partes_count;
            int casillas_puestas = 0;
            while (casillas_puestas < tam) {
                int fila = gen.nextInt(0, this.dimension);
                int columna = gen.nextInt(0, this.dimension);
                for (int k = 0; k < 4; k++) {
                    casillas_puestas = 0;
                    barco.borrarFantasma();
                    int direccion = gen.nextInt(1, 9);
                    switch (direccion) {
                        case 1:
                            if ((fila - tam) >= 0 && (columna - tam) >= 0) {
                                for (int i = 0; i < tam; i++) {
                                    Casilla casilla = new Casilla(fila - i, columna - i);
                                    if (this.verificarCasilla(casilla)) {
                                        barco.addCasilla(casilla);
                                        casillas_puestas++;
                                    }
                                }
                            }
                            break;
                        case 2:
                            if ((fila - tam) >= 0) {
                                for (int i = 0; i < tam; i++) {
                                    Casilla casilla = new Casilla(fila - i, columna);
                                    if (this.verificarCasilla(casilla)) {
                                        barco.addCasilla(casilla);
                                        casillas_puestas++;
                                    }
                                }
                            }
                            break;
                        case 3:
                            if ((fila - tam) >= 0 && (columna + tam) < this.dimension) {
                                for (int i = 0; i < tam; i++) {
                                    Casilla casilla = new Casilla(fila - i, columna + i);
                                    if (this.verificarCasilla(casilla)) {
                                        barco.addCasilla(casilla);
                                        casillas_puestas++;
                                    }
                                }
                            }
                            break;
                        case 4:
                            if ((columna - tam) >= 0) {
                                for (int i = 0; i < tam; i++) {
                                    Casilla casilla = new Casilla(fila, columna - i);
                                    if (this.verificarCasilla(casilla)) {
                                        barco.addCasilla(casilla);
                                        casillas_puestas++;
                                    }
                                }
                            }
                            break;
                        case 5:
                            break;
                        case 6:
                            if ((columna + tam) <= (this.dimension - 1)) {
                                for (int i = 0; i < tam; i++) {
                                    Casilla casilla = new Casilla(fila, columna + i);
                                    if (this.verificarCasilla(casilla)) {
                                        barco.addCasilla(casilla);
                                        casillas_puestas++;
                                    }
                                }
                            }
                            break;
                        case 7:
                            if ((fila + tam) <= (this.dimension - 1) && (columna - tam) >= 0) {
                                for (int i = 0; i < tam; i++) {
                                    Casilla casilla = new Casilla(fila + i, columna - i);
                                    if (this.verificarCasilla(casilla)) {
                                        barco.addCasilla(casilla);
                                        casillas_puestas++;
                                    }
                                }
                            }
                            break;
                        case 8:
                            if ((fila + tam) <= (this.dimension - 1)) {
                                for (int i = 0; i < tam; i++) {
                                    Casilla casilla = new Casilla(fila + i, columna);
                                    if (this.verificarCasilla(casilla)) {
                                        barco.addCasilla(casilla);
                                        casillas_puestas++;
                                    }
                                }
                            }
                            break;
                        case 9:
                            if ((fila + tam) <= (this.dimension - 1) && (columna + tam) <= (this.dimension - 1)) {
                                for (int i = 0; i < tam; i++) {
                                    Casilla casilla = new Casilla(fila + i, columna + i);
                                    if (this.verificarCasilla(casilla)) {
                                        barco.addCasilla(casilla);
                                        casillas_puestas++;
                                    }
                                }
                            }
                            break;
                    }
                }
            }
            barco.registrarBarco(this.mapa_naval);
        }
    }

    @Override
    public ResumenTablero obtenerResumen() {
        int[] filas = new int[this.dimension];
        int[] columnas = new int[this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            filas[i] = 0;
            columnas[i] = 0;
        }
        for (Barco barco : this.flota.values()) {
            for (Casilla casilla : barco.partes) {
                filas[casilla.getFila()]++;
                columnas[casilla.getColumna()]++;
            }
        }
        return new ResumenTablero(filas, columnas);
    }

    @Override
    public List<Casilla> obtenerCasillasOcupadasPorBarco(int numeroBarco) {
        Barco barco = this.flota.get(numeroBarco);
        return new ArrayList<>(barco.partes);
    }

    @Override
    public RespuestaJugada dispararACasilla(Casilla casilla) {
        Barco barco = this.mapa_naval.get(casilla);
        if (barco != null) {
            return barco.ataque(casilla);
        }
        return RespuestaJugada.AGUA;
    }

    @Override
    public int numeroBarcosNoHundidos() {
        int n_hundidos = 0;
        for (Barco barco : this.flota.values()) {
            if (!barco.hundido()) {
                n_hundidos++;
            }
        }
        return n_hundidos;
    }
}