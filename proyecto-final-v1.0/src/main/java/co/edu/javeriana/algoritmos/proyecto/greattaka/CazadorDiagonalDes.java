package co.edu.javeriana.algoritmos.proyecto.greattaka;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.RespuestaJugada;

public class CazadorDiagonalDes extends Cazador {
    public CazadorDiagonalDes(int dimension, double[][] probabilidad, Casilla primera_sangre, Casilla segunda_sangre) {
        super(dimension, probabilidad, primera_sangre, segunda_sangre);
        int x = primera_sangre.getFila(), y = primera_sangre.getColumna();
        for (int i = 1; i < this.distancia_maxima; i++) {
            if (x-i >= 0 && y-i >= 0) {
                this.direccionar_ataque(x-i, y-i, distancia_maxima - i);
            }
        }
        for (int i = 0; i < this.distancia_maxima; i++) {
            if (x+i < this.dimension && y+i < this.dimension) {
                this.direccionar_ataque(x+i, y+i, distancia_maxima - 1);
            }
        }
    }

    public String definir_direccion(PosibleAtaque anterior, PosibleAtaque actual) {
        if (actual.casilla.getFila() < anterior.casilla.getFila()) {
            return "izquierda-arriba";
        } else {
            return "derecha-abajo";
        }
    }

    @Override
    public void procesarRespuesta(RespuestaJugada respuesta) {
        if (respuesta == RespuestaJugada.AGUA) {
            PosibleAtaque anterior = this.ataques_realizados.get(this.ataques_realizados.size() - 2);
            PosibleAtaque actual = this.ataques_realizados.get(this.ataques_realizados.size() - 1);
            switch (definir_direccion(anterior, actual)) {
                case "izquierda-arriba":
                    posibles_ataques.removeIf((PosibleAtaque ataque) -> ataque.casilla.getFila() < actual.casilla.getFila());
                    break;
                case "derecha-abajo":
                    posibles_ataques.removeIf((PosibleAtaque ataque) -> ataque.casilla.getFila() > actual.casilla.getFila());
                    break;
            }
        }
    }

}
