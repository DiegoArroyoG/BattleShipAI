package co.edu.javeriana.algoritmos.proyecto.greattaka;

import co.edu.javeriana.algoritmos.proyecto.*;
import java.util.*;

public class JugadorGT implements Jugador {
    protected String nombre;
    protected Tablero tablero;
    protected ResumenTablero resumen_contrario;
    protected int[] r_filas;
    protected int[] r_columnas;
    protected int dimension;
    protected int total;
    protected PriorityQueue<PosibleAtaque> agenda_ataques;
    protected double[][] probabilidad;
    protected List<Casilla> ataques_realizados;
    protected boolean cazando;
    boolean direccion_encontrada;
    protected Casilla casilla_cazada;
    protected Set<Casilla> rosa_vientos;
    protected Cazador cazador;

    public JugadorGT() {
        this.nombre = "GreatTaka";
        this.agenda_ataques = new PriorityQueue<>();
        this.ataques_realizados = new ArrayList<>();
        this.cazando = false;
        this.direccion_encontrada = false;
        this.rosa_vientos = new HashSet<>();
    }

    @Override
    public String obtenerNombre() {
        return this.nombre;
    }

    @Override
    public Tablero iniciarTablero(int dimension, int[] barcos) {
        this.dimension = dimension;
        this.total = this.dimension * this.dimension;
        this.tablero = new TableroGT(dimension, barcos);
        return tablero;
    }

    protected void procesarResumenInicial() {
        //Creamos la agenda de ataques
        this.agenda_ataques = new PriorityQueue<>();
        //Inicializamos la tabla de probabilidades en 0
        this.probabilidad = new double[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.probabilidad[i][j] = 0;
            }
        }
        //Calculamos la probabilidad con la interseccion de las probabilidades de las filas y las columnas
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.probabilidad[i][j] += this.r_filas[i] / (double) this.total;
                this.probabilidad[i][j] += this.r_columnas[j] / (double) this.total;
                if (this.probabilidad[i][j] > 0) {
                    this.agenda_ataques.offer(new PosibleAtaque(new Casilla(i, j), this.probabilidad[i][j]));
                }
            }
        }
    }

    protected void procesarResumen() {
        this.agenda_ataques = new PriorityQueue<>();
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if(probabilidad[i][j]!=0){
                    this.probabilidad[i][j] += this.r_filas[i] / (double) this.total;
                    this.probabilidad[i][j] += this.r_columnas[j] / (double) this.total;
                }
                if (this.probabilidad[i][j] > 0) {
                    this.agenda_ataques.offer(new PosibleAtaque(new Casilla(i, j), this.probabilidad[i][j]));
                }
            }
        }
    }

    @Override
    public void recibirResumenRival(ResumenTablero resumen) {
        this.resumen_contrario = resumen;
        this.r_filas = new int[this.dimension];
        this.r_columnas = new int[this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            this.r_filas[i] = resumen.getCasillasOcupadasEnFila(i);
            this.r_columnas[i] = resumen.getCasillasOcupadasEnColumna(i);
        }
        this.procesarResumenInicial();
    }

    @Override
    public RespuestaJugada registrarDisparoAPosicion(Casilla posicion) {
        return tablero.dispararACasilla(posicion);
    }

    @Override
    public Casilla realizarDisparo() {
        //Si tenemos un cazador activo atacamos desde su agenda
        if (this.cazador != null) {
                Casilla siguiente = this.cazador.siguienteAtaque();
                this.ataques_realizados.add(siguiente);
                return siguiente;
        }
        //Si no tenemos cazador, atacamos desde la agenda de probabilidades asegurandonos de no atacar un lugar previamente atacado
        while(true){
            PosibleAtaque siguiente = this.agenda_ataques.poll();
            if (siguiente != null) {
                if(!this.ataques_realizados.contains(siguiente.casilla)){
                    this.ataques_realizados.add(siguiente.casilla);
                    return siguiente.casilla;
                }
            }
        }
    }

    protected void encontrarSiguienteAtaque() {
        this.agenda_ataques = new PriorityQueue<>();
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.probabilidad[i][j] > 0) {
                    this.agenda_ataques.offer(new PosibleAtaque(new Casilla(i, j), this.probabilidad[i][j]));
                }
            }
        }
    }

    protected void procesarImpacto(Casilla ultimo_ataque) {
        //Si no estamos cazando
        if (!this.cazando) {
            //Aplicar la rosa de los vientos, aumentando la probabilidad por un factor a las casillas adyacentes
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    int x = ultimo_ataque.getFila() + i, y = ultimo_ataque.getColumna() + j;
                    try {
                        this.probabilidad[x][y] *= 5;
                        this.rosa_vientos.add(new Casilla(x, y));
                    } catch (Exception ignored) {
                    }
                }
            }
            //Registramos la casilla que dio comienzo a la caza, y empezamos a cazar
            this.casilla_cazada = ultimo_ataque;
            this.cazando = true;
        } else if (!this.direccion_encontrada) {
            //Retiramos la rosa de los vientos
            for (Casilla casilla : this.rosa_vientos) {
                this.probabilidad[casilla.getFila()][casilla.getColumna()] /= 5;
            }
            this.rosa_vientos = new HashSet<>();
            //Calculamos hacia donde esta ubicado el barco
            int hi = ultimo_ataque.getFila() - this.casilla_cazada.getFila();
            int hj = ultimo_ataque.getColumna() - this.casilla_cazada.getColumna();
            String h = String.format("%d.%d", hi, hj);
            switch (h) {
                //Diagonal descendente (Va de arriba a abajo, de izquierda a derecha)
                case "-1.-1":
                case "1.1":
                    this.cazador = new CazadorDiagonalDes(this.dimension, this.probabilidad, casilla_cazada, ultimo_ataque);
                    break;
                //Diagonal ascendente (Va de abajo a arriba, de izquierda a derecha)
                case "-1.1":
                case "1.-1":
                    this.cazador = new CazadorDiagonalAsc(this.dimension, this.probabilidad, casilla_cazada, ultimo_ataque);
                    break;
                //Vertical
                case "-1.0":
                case "1.0":
                    this.cazador = new CazadorVertical(this.dimension, this.probabilidad, casilla_cazada, ultimo_ataque);
                    break;
                //Horizontal
                case "0.-1":
                case "0.1":
                    this.cazador = new CazadorHorizontal(this.dimension, this.probabilidad, casilla_cazada, ultimo_ataque);
                    break;
            }
            this.direccion_encontrada = true;
        }
    }

    @Override
    public void procesarResultadoDisparo(RespuestaJugada resultado) {
        //Recuperamos el ataque al que se refiere la respuesta
        Casilla ultimo_ataque = this.ataques_realizados.get(this.ataques_realizados.size() - 1);
        //Como la situacion de la casilla esta expuesta, ya no tiene probabilidad
        this.probabilidad[ultimo_ataque.getFila()][ultimo_ataque.getColumna()] = 0;
        //Si tenemos un cazador, le informamos la respuesta
        if (this.cazador != null) {
            this.cazador.procesarRespuesta(resultado);
        }
        //Decidimos que hacer con el resultado
        switch (resultado) {
            case AGUA:
                this.encontrarSiguienteAtaque();
                break;
            case IMPACTO:
                //Retiramos la casilla de los resumenes para recalcular las probabilidades efectivamente
                this.r_filas[ultimo_ataque.getFila()]--;
                this.r_columnas[ultimo_ataque.getColumna()]--;
                //Procesamos el impacto
                this.procesarImpacto(ultimo_ataque);
                this.encontrarSiguienteAtaque();
                break;
            case HUNDIDO:
                //El barco que se estaba cazando ha sido hundido
                this.r_filas[ultimo_ataque.getFila()]--;
                this.r_columnas[ultimo_ataque.getColumna()]--;
                //En caso de que sea un barco de dos casillas
                for (Casilla casilla : this.rosa_vientos) {
                    this.probabilidad[casilla.getFila()][casilla.getColumna()] /= 5;
                }
                //Retiramos al cazador y volvemos a trabajar con el mapa de probabilidades
                this.rosa_vientos = new HashSet<>();
                this.cazando = false;
                this.cazador = null;
                this.direccion_encontrada = false;
                this.procesarResumen();
                break;
        }
    }

    @Override
    public int numeroBarcosNoHundidos() {
        return this.tablero.numeroBarcosNoHundidos();
    }
}
