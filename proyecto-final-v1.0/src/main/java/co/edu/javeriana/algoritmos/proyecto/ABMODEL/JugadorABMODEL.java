/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.algoritmos.proyecto.ABMODEL;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.Jugador;
import co.edu.javeriana.algoritmos.proyecto.RespuestaJugada;
import co.edu.javeriana.algoritmos.proyecto.ResumenTablero;
import co.edu.javeriana.algoritmos.proyecto.Tablero;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Juan
 */
public class JugadorABMODEL implements Jugador {

    private String nombreJugador;
    private HashMap<Casilla, Boolean> mapaRival;
    private ArrayList<Casilla> arregloDisparoConfirmado;
    private Casilla casillaDisparada;
    private TableroABMODEL tableroPersona;
    private Boolean vuelta;
    private int dimension;
    private boolean auxiliar;
    private int[] filas;
    private int[] columnas;

    public JugadorABMODEL() {
        this.mapaRival = new HashMap<>();
        this.arregloDisparoConfirmado = new ArrayList<>();
        this.tableroPersona = new TableroABMODEL();
        this.vuelta = true;
        this.casillaDisparada = new Casilla(0, 0);
        this.auxiliar = true;
        this.nombreJugador = "Alfa Buena Maravilla Onda Dinamita Escuadrón Lobo";
    }

    public JugadorABMODEL(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        this.mapaRival = new HashMap<>();
        this.arregloDisparoConfirmado = new ArrayList<>();
        this.tableroPersona = new TableroABMODEL();
        this.vuelta = true;
        this.casillaDisparada = new Casilla(0, -2);
        this.auxiliar = true;

    }

    public String getNombreJugador() {
        return "Alfa Buena Maravilla Onda Dinamita Escuadrón Lobo";
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public HashMap<Casilla, Boolean> getMapaRival() {
        return mapaRival;
    }

    public void setMapaRival(HashMap<Casilla, Boolean> mapaRival) {
        this.mapaRival = mapaRival;
    }

    public ArrayList<Casilla> getArregloDisparoConfirmado() {
        return arregloDisparoConfirmado;
    }

    public void setArregloDisparoConfirmado(ArrayList<Casilla> arregloDisparoConfirmado) {
        this.arregloDisparoConfirmado = arregloDisparoConfirmado;
    }

    public Casilla getCasillaDisparada() {
        return casillaDisparada;
    }

    public void setCasillaDisparada(Casilla casillaDisparada) {
        this.casillaDisparada = casillaDisparada;
    }

    public Tablero getTableroPersona() {
        return tableroPersona;
    }

    public void setTableroPersona(TableroABMODEL tableroPersona) {
        this.tableroPersona = tableroPersona;
    }

    public Boolean getVuelta() {
        return vuelta;
    }

    public void setVuelta(Boolean vuelta) {
        this.vuelta = vuelta;
    }

    //importantes
    @Override
    public String obtenerNombre() {
        return this.getNombreJugador();
    }

    public void marcarIlegales(HashMap<Casilla, Integer> xx, ArrayList<Casilla> colocadas) {
        ArrayList<Casilla> nmms = new ArrayList<Casilla>(xx.keySet());
        for (Casilla colocada : colocadas) {
            Casilla aux = colocada;
            int x;

            //Esquina superior izquierda
            if (aux.getFila() - 1 > -1 && aux.getColumna() - 1 > -1 && xx.get(new Casilla(aux.getFila() - 1, aux.getColumna() - 1)) != null) {

                xx.remove(new Casilla(aux.getFila() - 1, aux.getColumna() - 1));

            }
            //Arriba
            if (aux.getFila() - 1 > -1 && xx.get(new Casilla(aux.getFila() - 1, aux.getColumna())) != null) {

                xx.remove(new Casilla(aux.getFila() - 1, aux.getColumna()));
            }
            //Esquina superior derecha
            if (aux.getFila() - 1 > -1 && aux.getColumna() + 1 < this.dimension && xx.get(new Casilla(aux.getFila() - 1, aux.getColumna() + 1)) != null) {

                xx.remove(new Casilla(aux.getFila() - 1, aux.getColumna() + 1));
            }
            //Derecha
            if (aux.getColumna() + 1 < this.dimension && xx.get(new Casilla(aux.getFila(), aux.getColumna() + 1)) != null) {

                xx.remove(new Casilla(aux.getFila(), aux.getColumna() + 1));
            }
            //Esquina inferior derecha
            if (aux.getFila() + 1 < this.dimension && aux.getColumna() + 1 < this.dimension && xx.get(new Casilla(aux.getFila() + 1, aux.getColumna() + 1)) != null) {

                xx.remove(new Casilla(aux.getFila() + 1, aux.getColumna() + 1));
            }
            //Abajo
            if (aux.getFila() + 1 < this.dimension && xx.get(new Casilla(aux.getFila() + 1, aux.getColumna())) != null) {

                xx.remove(new Casilla(aux.getFila() + 1, aux.getColumna()));
            }
            //Esquina inferior izquierda
            if (aux.getFila() + 1 < this.dimension && aux.getColumna() - 1 > -1 && xx.get(new Casilla(aux.getFila() + 1, aux.getColumna() - 1)) != null) {

                xx.remove(new Casilla(aux.getFila() + 1, aux.getColumna() - 1));
            }
            //Izquierda
            if (aux.getColumna() - 1 > -1 && xx.get(new Casilla(aux.getFila(), aux.getColumna() - 1)) != null) {

                xx.remove(new Casilla(aux.getFila(), aux.getColumna() - 1));
            }
        }

    }

    public void posicionarDiagonales(int[][] tab, int val, Pair<Integer, Integer> x, HashMap<Casilla, Integer> xx) {

        ArrayList<Casilla> puestas = new ArrayList<>();
        switch (val) {
            case 0:
                Casilla a_ID = new Casilla(this.dimension - 1, this.dimension - x.getValue());
                tab[a_ID.getFila()][a_ID.getColumna()] = 1;

                Casilla b_ID = new Casilla(this.dimension - x.getValue(), this.dimension - 1);

                tab[b_ID.getFila()][b_ID.getColumna()] = 1;
                puestas.add(a_ID);
                puestas.add(b_ID);
                this.tableroPersona.getCasillas().put(a_ID, new Pair(x.getKey(), false));
                this.tableroPersona.getCasillas().put(b_ID, new Pair(x.getKey(), false));
                xx.remove(a_ID);
                xx.remove(b_ID);
                Casilla aux = new Casilla(a_ID.getFila() - 1, a_ID.getColumna() + 1);
                while (!aux.equals(b_ID)) {
                    tab[aux.getFila()][aux.getColumna()] = 1;
                    puestas.add(aux);
                    this.tableroPersona.getCasillas().put(aux, new Pair(x.getKey(), false));
                    xx.remove(aux);
                    aux = new Casilla(aux.getFila() - 1, aux.getColumna() + 1);
                }
                this.tableroPersona.getMapaDeBarcos().put(x.getKey(), puestas);
                marcarIlegales(xx, puestas);
                break;
            case 1:
                Casilla a_II = new Casilla(this.dimension - 1, x.getValue() - 1);
                Casilla b_II = new Casilla(this.dimension - x.getValue(), 0);
                tab[a_II.getFila()][a_II.getColumna()] = 1;
                tab[b_II.getFila()][b_II.getColumna()] = 1;
                puestas.add(a_II);
                puestas.add(b_II);
                this.tableroPersona.getCasillas().put(a_II, new Pair(x.getKey(), false));
                this.tableroPersona.getCasillas().put(b_II, new Pair(x.getKey(), false));
                xx.remove(a_II);
                xx.remove(b_II);
                Casilla aux2 = new Casilla(a_II.getFila() - 1, a_II.getColumna() - 1);
                while (!aux2.equals(b_II)) {
                    tab[aux2.getFila()][aux2.getColumna()] = 1;
                    puestas.add(aux2);
                    this.tableroPersona.getCasillas().put(aux2, new Pair(x.getKey(), false));
                    xx.remove(aux2);
                    aux2 = new Casilla(aux2.getFila() - 1, aux2.getColumna() - 1);
                }
                this.tableroPersona.getMapaDeBarcos().put(x.getKey(), puestas);
                marcarIlegales(xx, puestas);
                break;
            case 2:
                Casilla a_SI = new Casilla(0, x.getValue() - 1);
                Casilla b_SI = new Casilla(x.getValue() - 1, 0);
                tab[a_SI.getFila()][a_SI.getColumna()] = 1;
                tab[b_SI.getFila()][b_SI.getColumna()] = 1;
                puestas.add(a_SI);
                puestas.add(b_SI);
                this.tableroPersona.getCasillas().put(a_SI, new Pair(x.getKey(), false));
                this.tableroPersona.getCasillas().put(b_SI, new Pair(x.getKey(), false));
                xx.remove(a_SI);
                xx.remove(b_SI);
                Casilla aux3 = new Casilla(a_SI.getFila() + 1, a_SI.getColumna() - 1);
                while (!aux3.equals(b_SI)) {
                    tab[aux3.getFila()][aux3.getColumna()] = 1;
                    puestas.add(aux3);
                    this.tableroPersona.getCasillas().put(aux3, new Pair(x.getKey(), false));
                    xx.remove(aux3);
                    aux3 = new Casilla(aux3.getFila() + 1, aux3.getColumna() - 1);
                }
                this.tableroPersona.getMapaDeBarcos().put(x.getKey(), puestas);
                marcarIlegales(xx, puestas);
                break;
            case 3:

                Casilla a_SD = new Casilla(0, this.dimension - x.getValue());
                Casilla b_SD = new Casilla(x.getValue() - 1, this.dimension - 1);
                tab[a_SD.getFila()][a_SD.getColumna()] = 1;
                tab[b_SD.getFila()][b_SD.getColumna()] = 1;
                puestas.add(a_SD);
                puestas.add(b_SD);
                this.tableroPersona.getCasillas().put(a_SD, new Pair(x.getKey(), false));
                this.tableroPersona.getCasillas().put(b_SD, new Pair(x.getKey(), false));
                xx.remove(a_SD);
                xx.remove(b_SD);
                Casilla aux4 = new Casilla(a_SD.getFila() + 1, a_SD.getColumna() + 1);
                while (!aux4.equals(b_SD)) {
                    tab[aux4.getFila()][aux4.getColumna()] = 1;
                    puestas.add(aux4);
                    this.tableroPersona.getCasillas().put(aux4, new Pair(x.getKey(), false));
                    xx.remove(aux4);
                    aux4 = new Casilla(aux4.getFila() + 1, aux4.getColumna() + 1);
                }
                this.tableroPersona.getMapaDeBarcos().put(x.getKey(), puestas);
                marcarIlegales(xx, puestas);
                break;
        }
    }

    public TableroABMODEL PosicionarBarcos(Pair[] barcosPosicionar) {
        Pair<Integer, Integer> x = barcosPosicionar[barcosPosicionar.length - 1];
        Random rnd = new Random();
        Integer y = 1;
        int[][] tab = this.tableroPersona.getTablero();
        int val = (int) (rnd.nextDouble() * 4);//0..3

        HashMap<Casilla, Integer> xx = new HashMap<>();
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                xx.put(new Casilla(i, j), i + j);
            }
        }
        if ((int) barcosPosicionar[barcosPosicionar.length - 1].getValue() > 1) {
            posicionarDiagonales(tab, val, x, xx);
        }

        barcosPosicionar[barcosPosicionar.length - 1] = new Pair(-1, -1);

        for (int i = 0; i < barcosPosicionar.length; i++) {
            while (!barcosPosicionar[i].getKey().equals(-1)) {
                ArrayList<Casilla> colocadas = new ArrayList();
                val = (int) (rnd.nextDouble() * 2);
                //Horizontal
                if (val == 1) {
                    boolean flag = true;
                    List<Casilla> keysAsArray = new ArrayList<Casilla>(xx.keySet());
                    Random r = new Random();
                    Casilla get = keysAsArray.get(r.nextInt(keysAsArray.size()));

                    for (int j = 0; j < (int) barcosPosicionar[i].getValue(); j++) {
                        if (xx.get(new Casilla(get.getFila(), get.getColumna() + j)) == null || get.getColumna() + j >= this.dimension) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        for (int j = 0; j < (int) (barcosPosicionar[i].getValue()); j++) {
                            Casilla aux = new Casilla(get.getFila(), get.getColumna() + j);
                            tab[aux.getFila()][aux.getColumna()] = 1;
                            xx.remove(aux);
                            colocadas.add(aux);
                            this.tableroPersona.getCasillas().put(aux, new Pair(barcosPosicionar[i].getKey(), false));
                        }
                        this.tableroPersona.getMapaDeBarcos().put((Integer) barcosPosicionar[i].getKey(), colocadas);
                        barcosPosicionar[i] = new Pair(-1, -1);
                        marcarIlegales(xx, colocadas);
                        break;
                    } else {

                        flag = true;
                        for (int j = 0; j < (int) barcosPosicionar[i].getValue(); j++) {
                            if (xx.get(new Casilla(get.getFila(), get.getColumna() - j)) == null || get.getColumna() - j <= -1) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            for (int j = 0; j < (int) (barcosPosicionar[i].getValue()); j++) {
                                Casilla aux = new Casilla(get.getFila(), get.getColumna() - j);
                                tab[aux.getFila()][aux.getColumna()] = 1;
                                xx.remove(aux);
                                colocadas.add(aux);
                                this.tableroPersona.getCasillas().put(aux, new Pair(barcosPosicionar[i].getKey(), false));
                            }
                            this.tableroPersona.getMapaDeBarcos().put((Integer) barcosPosicionar[i].getKey(), colocadas);
                            barcosPosicionar[i] = new Pair(-1, -1);
                            marcarIlegales(xx, colocadas);
                            break;
                        } else {

                            flag = true;
                            for (int j = 0; j < (int) barcosPosicionar[i].getValue(); j++) {
                                if (xx.get(new Casilla(get.getFila() - j, get.getColumna())) == null || get.getFila() - j <= -1) {
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag) {
                                for (int j = 0; j < (int) (barcosPosicionar[i].getValue()); j++) {
                                    Casilla aux = new Casilla(get.getFila() - j, get.getColumna());
                                    tab[aux.getFila()][aux.getColumna()] = 1;
                                    xx.remove(aux);
                                    colocadas.add(aux);
                                    this.tableroPersona.getCasillas().put(aux, new Pair(barcosPosicionar[i].getKey(), false));
                                }
                                this.tableroPersona.getMapaDeBarcos().put((Integer) barcosPosicionar[i].getKey(), colocadas);
                                barcosPosicionar[i] = new Pair(-1, -1);
                                marcarIlegales(xx, colocadas);
                                break;
                            } else {
                                flag = true;
                                for (int j = 0; j < (int) barcosPosicionar[i].getValue(); j++) {
                                    if (xx.get(new Casilla(get.getFila() + j, get.getColumna())) == null || get.getFila() + j >= this.dimension) {
                                        flag = false;
                                        break;
                                    }
                                }
                                if (flag) {
                                    for (int j = 0; j < (int) (barcosPosicionar[i].getValue()); j++) {
                                        Casilla aux = new Casilla(get.getFila() + j, get.getColumna());
                                        tab[aux.getFila()][aux.getColumna()] = 1;
                                        xx.remove(aux);
                                        colocadas.add(aux);
                                        this.tableroPersona.getCasillas().put(aux, new Pair(barcosPosicionar[i].getKey(), false));
                                    }
                                    this.tableroPersona.getMapaDeBarcos().put((Integer) barcosPosicionar[i].getKey(), colocadas);
                                    barcosPosicionar[i] = new Pair(-1, -1);
                                    marcarIlegales(xx, colocadas);
                                    break;
                                }
                            }
                        }
                    } //Vertical

                } else {

                    List<Casilla> keysAsArray = new ArrayList<Casilla>(xx.keySet());
                    Random r = new Random();
                    Casilla get = keysAsArray.get(r.nextInt(keysAsArray.size()));
                    boolean flag = true;
                    for (int j = 0; j < (int) barcosPosicionar[i].getValue(); j++) {
                        if (xx.get(new Casilla(get.getFila() - j, get.getColumna())) == null || get.getFila() - j <= -1) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        for (int j = 0; j < (int) (barcosPosicionar[i].getValue()); j++) {
                            Casilla aux = new Casilla(get.getFila() - j, get.getColumna());
                            tab[aux.getFila()][aux.getColumna()] = 1;
                            xx.remove(aux);
                            colocadas.add(aux);
                            this.tableroPersona.getCasillas().put(aux, new Pair(barcosPosicionar[i].getKey(), false));
                        }
                        this.tableroPersona.getMapaDeBarcos().put((Integer) barcosPosicionar[i].getKey(), colocadas);
                        barcosPosicionar[i] = new Pair(-1, -1);
                        marcarIlegales(xx, colocadas);
                        break;
                    } else {
                        flag = true;
                        for (int j = 0; j < (int) barcosPosicionar[i].getValue(); j++) {
                            if (xx.get(new Casilla(get.getFila() + j, get.getColumna())) == null || get.getFila() + j >= this.dimension) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            for (int j = 0; j < (int) (barcosPosicionar[i].getValue()); j++) {
                                Casilla aux = new Casilla(get.getFila() + j, get.getColumna());
                                tab[aux.getFila()][aux.getColumna()] = 1;
                                xx.remove(aux);
                                colocadas.add(aux);
                                this.tableroPersona.getCasillas().put(aux, new Pair(barcosPosicionar[i].getKey(), false));
                            }
                            this.tableroPersona.getMapaDeBarcos().put((Integer) barcosPosicionar[i].getKey(), colocadas);
                            barcosPosicionar[i] = new Pair(-1, -1);
                            marcarIlegales(xx, colocadas);
                            break;
                        } else {
                            flag = true;
                            for (int j = 0; j < (int) barcosPosicionar[i].getValue(); j++) {
                                if (xx.get(new Casilla(get.getFila(), get.getColumna() + j)) == null || get.getColumna() + j >= this.dimension) {
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag) {
                                for (int j = 0; j < (int) (barcosPosicionar[i].getValue()); j++) {
                                    Casilla aux = new Casilla(get.getFila(), get.getColumna() + j);
                                    tab[aux.getFila()][aux.getColumna()] = 1;
                                    xx.remove(aux);
                                    colocadas.add(aux);
                                    this.tableroPersona.getCasillas().put(aux, new Pair(barcosPosicionar[i].getKey(), false));
                                }
                                this.tableroPersona.getMapaDeBarcos().put((Integer) barcosPosicionar[i].getKey(), colocadas);
                                barcosPosicionar[i] = new Pair(-1, -1);
                                marcarIlegales(xx, colocadas);
                                break;
                            } else {
                                flag = true;
                                for (int j = 0; j < (int) barcosPosicionar[i].getValue(); j++) {
                                    if (xx.get(new Casilla(get.getFila(), get.getColumna() - j)) == null || get.getColumna() - j <= -1) {
                                        flag = false;
                                        break;
                                    }
                                }
                                if (flag) {
                                    for (int j = 0; j < (int) (barcosPosicionar[i].getValue()); j++) {
                                        Casilla aux = new Casilla(get.getFila(), get.getColumna() - j);
                                        tab[aux.getFila()][aux.getColumna()] = 1;
                                        xx.remove(aux);
                                        colocadas.add(aux);
                                        this.tableroPersona.getCasillas().put(aux, new Pair(barcosPosicionar[i].getKey(), false));
                                    }
                                    this.tableroPersona.getMapaDeBarcos().put((Integer) barcosPosicionar[i].getKey(), colocadas);
                                    barcosPosicionar[i] = new Pair(-1, -1);
                                    marcarIlegales(xx, colocadas);
                                    break;
                                }
                            }
                        }
                    } //Vertical

                }
            }

        }
        this.tableroPersona.setTablero(tab);
        return this.tableroPersona;
    }

    @Override
    public Tablero iniciarTablero(int dimension, int[] barcos
    ) {

        this.tableroPersona = new TableroABMODEL(dimension);
        this.dimension = dimension;
        this.auxiliar = true;
        this.mapaRival = new HashMap<>();
        this.arregloDisparoConfirmado = new ArrayList<>();
        this.casillaDisparada = new Casilla(0, 0);
        //FUNCION PARA CREAR TABLERO 
        Pair[] barcosPosicionar = new Pair[barcos.length];
        for (int i = 0; i < barcos.length; i++) {
            barcosPosicionar[i] = (new Pair(i, barcos[i]));
        }
        Compare obj = new Compare();

        obj.compare(barcosPosicionar, barcos.length);

        this.tableroPersona = PosicionarBarcos(barcosPosicionar);
        this.filas = new int[this.dimension];
        this.columnas = new int[this.dimension];

        return this.tableroPersona;
    }

    public void deleteMachete() {
        for (int i = 0; i < this.dimension; i++) {
            if (this.filas[i] == 0) {
                for (int j = 0; j < this.dimension; j++) {
                    Casilla aux = new Casilla(i, j);

                    this.mapaRival.put(aux, false);
                }
                this.filas[i] = -1;
            }
        }

        for (int i = 0; i < this.dimension; i++) {
            if (this.columnas[i] == 0) {
                for (int j = 0; j < this.dimension; j++) {
                    Casilla aux = new Casilla(j, i);
                    this.mapaRival.put(aux, false);
                }
                this.columnas[i] = -1;
            }
        }
    }

    @Override
    public void recibirResumenRival(ResumenTablero resumen
    ) {
        for (int i = 0; i < this.dimension; i++) {
            this.filas[i] = resumen.getCasillasOcupadasEnFila(i);
            this.columnas[i] = resumen.getCasillasOcupadasEnColumna(i);
        }
        deleteMachete();
    }

    @Override
    public RespuestaJugada registrarDisparoAPosicion(Casilla posicion
    ) {
        return this.tableroPersona.dispararACasilla(posicion);
    }

    @Override
    public Casilla realizarDisparo() {
        if (this.auxiliar) {
            this.casillaDisparada = optimizacion();
            this.auxiliar = false;
            return this.casillaDisparada;
        }
        if (this.arregloDisparoConfirmado.size() > 0) {
            return HundirBarco(this.arregloDisparoConfirmado.get(this.arregloDisparoConfirmado.size() - 1));
        } else {
            return BuscarCasillaDisparar();
        }
    }

    @Override
    public void procesarResultadoDisparo(RespuestaJugada resultado) {
        if (resultado.getLetrero().equals(RespuestaJugada.AGUA.getLetrero())) {

            this.mapaRival.put(this.casillaDisparada, false);

        } else if (resultado.getLetrero().equals(RespuestaJugada.IMPACTO.getLetrero())) {
            this.mapaRival.put(this.casillaDisparada, true);
            this.arregloDisparoConfirmado.add(this.casillaDisparada);
            this.filas[this.casillaDisparada.getFila()]--;
            this.columnas[this.casillaDisparada.getColumna()]--;
            deleteMachete();
        } else if (resultado.getLetrero().equals(RespuestaJugada.HUNDIDO.getLetrero())) {
            this.mapaRival.put(this.casillaDisparada, true);
            this.arregloDisparoConfirmado.add(this.casillaDisparada);
            eliminarPosiciones();
            this.arregloDisparoConfirmado.clear();
            this.filas[this.casillaDisparada.getFila()]--;
            this.columnas[this.casillaDisparada.getColumna()]--;
            deleteMachete();
            this.auxiliar = true;
        }
        if ((this.casillaDisparada.getFila() + 1) >= this.dimension && (this.casillaDisparada.getColumna() + 1) >= this.dimension) {
            this.vuelta = false;
            this.casillaDisparada = optimizacion();
        }

    }

    @Override
    public int numeroBarcosNoHundidos() {
        return this.tableroPersona.numeroBarcosNoHundidos();
    }

    public Casilla HundirBarco(Casilla c) {
        if (this.arregloDisparoConfirmado.size() > 1) {
            //cuando dos tiros
            Casilla a = this.arregloDisparoConfirmado.get(this.arregloDisparoConfirmado.size() - 1);
            Casilla b = this.arregloDisparoConfirmado.get(this.arregloDisparoConfirmado.size() - 2);
            Casilla resta = new Casilla(a.getFila() - b.getFila(), a.getColumna() - b.getColumna());

            //Si hay diagonal
            if (Math.abs(resta.getFila()) == Math.abs(resta.getColumna())) {
                //Ejemplo 1
                if (resta.getFila() == resta.getColumna()) {
                    // 1,1   && -1,-1
                    Casilla aux = new Casilla(a.getFila() + resta.getFila(), a.getColumna() + resta.getColumna());
                    if (aux.getFila() > -1 && aux.getColumna() > -1 && this.mapaRival.get(aux) == null
                            && aux.getFila() < this.dimension && aux.getColumna() < this.dimension) {
                        this.casillaDisparada = aux;
                        return this.casillaDisparada;
                    }

                    resta = new Casilla(resta.getFila() * -1, resta.getColumna() * -1);
                    aux = this.arregloDisparoConfirmado.get(0);
                    aux = new Casilla(aux.getFila() + resta.getFila(), aux.getColumna() + resta.getColumna());
                    if (aux.getFila() > -1 && aux.getColumna() > -1 && this.mapaRival.get(aux) == null
                            && aux.getFila() < this.dimension && aux.getColumna() < this.dimension) {
                        this.arregloDisparoConfirmado.add(this.arregloDisparoConfirmado.get(0));
                        this.casillaDisparada = aux;
                        return this.casillaDisparada;
                    }
                    Random r = new Random();

                    int result = r.nextInt(this.dimension - 1);
                    int result2 = r.nextInt(this.dimension - 1);
                    this.casillaDisparada = new Casilla(result, result2);
                    return this.casillaDisparada;

                } else {
                    // -1,1  && 1,-1

                    Casilla aux = new Casilla(a.getFila() + resta.getFila(), a.getColumna() + resta.getColumna());
                    if (aux.getFila() > -1 && aux.getColumna() > -1 && this.mapaRival.get(aux) == null
                            && aux.getFila() < this.dimension && aux.getColumna() < this.dimension) {
                        this.casillaDisparada = aux;
                        return this.casillaDisparada;
                    }
                    resta = new Casilla(resta.getFila() * -1, resta.getColumna() * -1);
                    aux = this.arregloDisparoConfirmado.get(0);
                    aux = new Casilla(aux.getFila() + resta.getFila(), aux.getColumna() + resta.getColumna());
                    if (aux.getFila() > -1 && aux.getColumna() > -1 && this.mapaRival.get(aux) == null
                            && aux.getFila() < this.dimension && aux.getColumna() < this.dimension) {
                        this.arregloDisparoConfirmado.add(this.arregloDisparoConfirmado.get(0));
                        this.casillaDisparada = aux;
                        return this.casillaDisparada;
                    }
                    Random r = new Random();

                    int result = r.nextInt(this.dimension - 1);
                    int result2 = r.nextInt(this.dimension - 1);
                    this.casillaDisparada = new Casilla(result, result2);
                    return this.casillaDisparada;

                }

            } //Verticales
            else if ((resta.getFila() == 1 && resta.getColumna() == 0) || (resta.getFila() == -1 && resta.getColumna() == 0)) {
                Casilla aux = new Casilla(a.getFila() + resta.getFila(), a.getColumna() + resta.getColumna());
                if (aux.getFila() > -1 && aux.getColumna() > -1 && this.mapaRival.get(aux) == null
                        && aux.getFila() < this.dimension && aux.getColumna() < this.dimension) {
                    this.casillaDisparada = aux;
                    return this.casillaDisparada;
                }

                resta = new Casilla(resta.getFila() * -1, resta.getColumna() * -1);
                aux = this.arregloDisparoConfirmado.get(0);
                aux = new Casilla(aux.getFila() + resta.getFila(), aux.getColumna() + resta.getColumna());
                if (aux.getFila() > -1 && aux.getColumna() > -1 && this.mapaRival.get(aux) == null
                        && aux.getFila() < this.dimension && aux.getColumna() < this.dimension) {
                    this.arregloDisparoConfirmado.add(this.arregloDisparoConfirmado.get(0));
                    this.casillaDisparada = aux;
                    return this.casillaDisparada;
                }
                Random r = new Random();

                int result = r.nextInt(this.dimension - 1);
                int result2 = r.nextInt(this.dimension - 1);
                this.casillaDisparada = new Casilla(result, result2);
                return this.casillaDisparada;

            } //Horizontales
            else if ((resta.getFila() == 0 && resta.getColumna() == 1) || (resta.getFila() == 0 && resta.getColumna() == -1)) {

                Casilla aux = new Casilla(a.getFila() + resta.getFila(), a.getColumna() + resta.getColumna());
                if (aux.getFila() > -1 && aux.getColumna() > -1 && this.mapaRival.get(aux) == null
                        && aux.getFila() < this.dimension && aux.getColumna() < this.dimension) {

                    this.casillaDisparada = aux;
                    return this.casillaDisparada;
                }

                resta = new Casilla(resta.getFila() * -1, resta.getColumna() * -1);
                aux = this.arregloDisparoConfirmado.get(0);
                aux = new Casilla(aux.getFila() + resta.getFila(), aux.getColumna() + resta.getColumna());
                if (aux.getFila() > -1 && aux.getColumna() > -1 && this.mapaRival.get(aux) == null
                        && aux.getFila() < this.dimension && aux.getColumna() < this.dimension) {
                    this.arregloDisparoConfirmado.add(this.arregloDisparoConfirmado.get(0));
                    this.casillaDisparada = aux;
                    return this.casillaDisparada;
                }
                Random r = new Random();

                int result = r.nextInt(this.dimension - 1);
                int result2 = r.nextInt(this.dimension - 1);
                this.casillaDisparada = new Casilla(result, result2);
                return this.casillaDisparada;

            }
        } else {
            Casilla aux = this.arregloDisparoConfirmado.get(0);
            //Esquina superior izquierda
            if (aux.getFila() - 1 > -1 && aux.getColumna() - 1 > -1 && this.mapaRival.get(new Casilla(aux.getFila() - 1, aux.getColumna() - 1)) == null) {
                aux = new Casilla(aux.getFila() - 1, aux.getColumna() - 1);
                this.casillaDisparada = aux;
                return this.casillaDisparada;
            }
            //Arriba
            if (aux.getFila() - 1 > -1 && this.mapaRival.get(new Casilla(aux.getFila() - 1, aux.getColumna())) == null) {
                aux = new Casilla(aux.getFila() - 1, aux.getColumna());
                this.casillaDisparada = aux;
                return this.casillaDisparada;
            }
            //Esquina superior derecha
            if (aux.getFila() - 1 > -1 && aux.getColumna() + 1 < this.dimension && this.mapaRival.get(new Casilla(aux.getFila() - 1, aux.getColumna() + 1)) == null) {
                aux = new Casilla(aux.getFila() - 1, aux.getColumna() + 1);
                this.casillaDisparada = aux;
                return this.casillaDisparada;
            }
            //Derecha
            if (aux.getColumna() + 1 < this.dimension && this.mapaRival.get(new Casilla(aux.getFila(), aux.getColumna() + 1)) == null) {
                aux = new Casilla(aux.getFila(), aux.getColumna() + 1);
                this.casillaDisparada = aux;
                return this.casillaDisparada;
            }
            //Esquina inferior derecha
            if (aux.getFila() + 1 < this.dimension && aux.getColumna() + 1 < this.dimension && this.mapaRival.get(new Casilla(aux.getFila() + 1, aux.getColumna() + 1)) == null) {
                aux = new Casilla(aux.getFila() + 1, aux.getColumna() + 1);
                this.casillaDisparada = aux;
                return this.casillaDisparada;
            }
            //Abajo
            if (aux.getFila() + 1 < this.dimension && this.mapaRival.get(new Casilla(aux.getFila() + 1, aux.getColumna())) == null) {
                aux = new Casilla(aux.getFila() + 1, aux.getColumna());
                this.casillaDisparada = aux;
                return this.casillaDisparada;
            }
            //Esquina inferior izquierda
            if (aux.getFila() + 1 < this.dimension && aux.getColumna() - 1 > -1 && this.mapaRival.get(new Casilla(aux.getFila() + 1, aux.getColumna() - 1)) == null) {
                aux = new Casilla(aux.getFila() + 1, aux.getColumna() - 1);
                this.casillaDisparada = aux;
                return this.casillaDisparada;
            }
            //Izquierda
            if (aux.getColumna() - 1 > -1 && this.mapaRival.get(new Casilla(aux.getFila(), aux.getColumna() - 1)) == null) {
                aux = new Casilla(aux.getFila(), aux.getColumna() - 1);
                this.casillaDisparada = aux;
                return this.casillaDisparada;
            }

        }

        Random r = new Random();

        int result = r.nextInt(this.dimension - 1);
        int result2 = r.nextInt(this.dimension - 1);
        this.casillaDisparada = new Casilla(result, result2);
        return this.casillaDisparada;

    }

    public Casilla BuscarCasillaDisparar() {
        Casilla actual = this.casillaDisparada;
        while (true) {
            if (actual.getFila() + 1 >= this.dimension && actual.getColumna() + 1 >= this.dimension) {
                actual = new Casilla(0, 0);
            } else {
                if (actual.getColumna() + 1 < this.dimension) {
                    actual = new Casilla(actual.getFila(), actual.getColumna() + 1);
                    if (mapaRival.get(new Casilla(actual.getFila(), actual.getColumna())) == null) {
                        this.casillaDisparada = actual;
                        return this.casillaDisparada;
                    }
                } else {
                    if ((actual.getFila() + 1) % 2 == 0) {
                        actual = new Casilla((actual.getFila() + 1), 0);
                    } else {
                        actual = new Casilla((actual.getFila() + 1), 1);
                    }
                    if (actual.getFila() < this.dimension) {
                        if (mapaRival.get(new Casilla(actual.getFila(), actual.getColumna())) == null) {
                            this.casillaDisparada = actual;
                            return this.casillaDisparada;
                        }
                    }
                }
            }
        }
    }

    void eliminarPosiciones() {
        for (Casilla casilla : this.arregloDisparoConfirmado) {
            Casilla aux = casilla;
            //Esquina superior izquierda
            if (aux.getFila() - 1 > -1 && aux.getColumna() - 1 > -1 && this.mapaRival.get(aux) == null) {
                this.mapaRival.put(new Casilla(aux.getFila() - 1, aux.getColumna() - 1), false);
            }
            //Arriba
            if (aux.getFila() - 1 > -1 && this.mapaRival.get(aux) == null) {
                this.mapaRival.put(new Casilla(aux.getFila() - 1, aux.getColumna()), false);
            }
            //Esquina superior derecha
            if (aux.getFila() - 1 > -1 && aux.getColumna() + 1 < this.dimension && this.mapaRival.get(aux) == null) {
                this.mapaRival.put(new Casilla(aux.getFila() - 1, aux.getColumna() + 1), false);
            }
            //Derecha
            if (aux.getColumna() + 1 < this.dimension && this.mapaRival.get(aux) == null) {
                this.mapaRival.put(new Casilla(aux.getFila(), aux.getColumna() + 1), false);
            }
            //Esquina inferior derecha
            if (aux.getFila() + 1 < this.dimension && aux.getColumna() + 1 < this.dimension && this.mapaRival.get(aux) == null) {
                this.mapaRival.put(new Casilla(aux.getFila() + 1, aux.getColumna() + 1), false);
            }
            //Abajo
            if (aux.getFila() + 1 < this.dimension && this.mapaRival.get(aux) == null) {
                this.mapaRival.put(new Casilla(aux.getFila() + 1, aux.getColumna()), false);
            }
            //Esquina inferior izquierda
            if (aux.getFila() + 1 < this.dimension && aux.getColumna() - 1 > -1 && this.mapaRival.get(aux) == null) {
                this.mapaRival.put(new Casilla(aux.getFila() + 1, aux.getColumna() - 1), false);
            }
            //Izquierda
            if (aux.getColumna() - 1 > -1 && this.mapaRival.get(aux) == null) {
                this.mapaRival.put(new Casilla(aux.getFila(), aux.getColumna() - 1), false);
            }
        }
    }

    public Casilla optimizacion() {
        int idFila = 0, idCol = 0, maxFila = -1, maxCol = -1;
        for (int i = 0; i < this.dimension; i++) {
            if (maxFila < this.filas[i]) {
                maxFila = this.filas[i];
                idFila = i;
            }
        }

        for (int i = 0; i < this.dimension; i++) {

            if (maxCol < this.columnas[i]) {
                maxCol = this.columnas[i];
                idCol = i;
            }
        }
        if (idFila >= this.dimension) {
            idFila = idFila - (idFila - (this.dimension - 1));
        }
        if (idCol >= this.dimension) {
            idCol = idCol - (idCol - (this.dimension - 1));
        }
        this.casillaDisparada = new Casilla(idFila, idCol);
        return new Casilla(idFila, idCol);
    }
}

class Compare {

    static void compare(Pair arr[], int n) {
        // Comparator to sort the pair according to second element 
        Arrays.sort(arr, new Comparator<Pair>() {
            @Override
            public int compare(Pair p1, Pair p2) {
                return (int) p1.getValue() - (int) p2.getValue();
            }
        });
    }
}
