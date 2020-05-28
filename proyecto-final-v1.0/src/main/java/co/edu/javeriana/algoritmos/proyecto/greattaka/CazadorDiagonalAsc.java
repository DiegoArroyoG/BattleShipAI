package co.edu.javeriana.algoritmos.proyecto.greattaka;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.RespuestaJugada;

public class CazadorDiagonalAsc extends Cazador {
    public CazadorDiagonalAsc(int dimension, double[][] probabilidad, Casilla primera_sangre, Casilla segunda_sangre) {
        super(dimension, probabilidad, primera_sangre, segunda_sangre);
        //El cazador se extiende por la direccion del barco, tantas casillas como el maximo barco desde la primera sangre
        for (int i = 1; i < this.distancia_maxima; i++) {
            int mi = primera_sangre.getFila() - i;
            int pi = primera_sangre.getFila() + i;
            int mj = primera_sangre.getColumna() - i;
            int pj = primera_sangre.getColumna() + i;
            if (pi < this.dimension && mj >= 0) {
                this.direccionar_ataque(pi, mj, distancia_maxima - i);
            }
            if (mi >= 0 && pj < this.dimension) {
                this.direccionar_ataque(mi, pj, distancia_maxima - i);
            }
        }
    }

    public String definir_direccion(PosibleAtaque anterior, PosibleAtaque actual) {
        if (actual.casilla.getFila() < anterior.casilla.getFila()) {
            return "derecha-arriba";
        } else {
            return "izquierda-abajo";
        }
    }

    @Override
    public void procesarRespuesta(RespuestaJugada respuesta) {
        if (respuesta == RespuestaJugada.AGUA) {
            PosibleAtaque anterior = this.ataques_realizados.get(this.ataques_realizados.size() - 2);
            PosibleAtaque actual = this.ataques_realizados.get(this.ataques_realizados.size() - 1);
            switch (definir_direccion(anterior, actual)) {
                case "derecha-arriba":
                    posibles_ataques.removeIf((PosibleAtaque ataque) -> ataque.casilla.getFila() < actual.casilla.getFila());
                    break;
                case "izquierda-abajo":
                    posibles_ataques.removeIf((PosibleAtaque ataque) -> ataque.casilla.getFila() > actual.casilla.getFila());
                    break;
            }
        }
    }
}
