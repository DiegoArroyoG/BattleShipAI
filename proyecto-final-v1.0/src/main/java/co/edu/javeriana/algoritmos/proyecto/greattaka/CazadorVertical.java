package co.edu.javeriana.algoritmos.proyecto.greattaka;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.RespuestaJugada;

public class CazadorVertical extends Cazador {
    public CazadorVertical(int dimension, double[][] probabilidad, Casilla primera_sangre, Casilla segunda_sangre) {
        super(dimension, probabilidad, primera_sangre, segunda_sangre);
        for (int i = 1; i < this.distancia_maxima; i++) {
            int mi = primera_sangre.getFila() - i;
            int pi = primera_sangre.getFila() + i;
            if (mi >= 0) {
                this.direccionar_ataque(mi, primera_sangre.getColumna(), distancia_maxima - i);
            }
            if (pi < this.dimension) {
                this.direccionar_ataque(pi, primera_sangre.getColumna(), distancia_maxima - i);
            }
        }
    }

    public String definir_direccion(PosibleAtaque anterior, PosibleAtaque actual) {
        if (actual.casilla.getFila() < anterior.casilla.getFila()) {
            return "arriba";
        } else {
            return "abajo";
        }
    }

    @Override
    public void procesarRespuesta(RespuestaJugada respuesta) {
        if (respuesta == RespuestaJugada.AGUA) {
            PosibleAtaque anterior = this.ataques_realizados.get(this.ataques_realizados.size() - 2);
            PosibleAtaque actual = this.ataques_realizados.get(this.ataques_realizados.size() - 1);
            switch (definir_direccion(anterior, actual)) {
                case "arriba":
                    posibles_ataques.removeIf((PosibleAtaque ataque) -> ataque.casilla.getFila() < actual.casilla.getFila());
                    break;
                case "abajo":
                    posibles_ataques.removeIf((PosibleAtaque ataque) -> ataque.casilla.getFila() > actual.casilla.getFila());
                    break;
            }
        }
    }
}
