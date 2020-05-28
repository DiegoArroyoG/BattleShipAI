package co.edu.javeriana.algoritmos.proyecto.greattaka;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.RespuestaJugada;

public class CazadorHorizontal extends Cazador {
    public CazadorHorizontal(int dimension, double[][] probabilidad, Casilla primera_sangre, Casilla segunda_sangre) {
        super(dimension, probabilidad, primera_sangre, segunda_sangre);
        for (int i = 1; i < this.distancia_maxima; i++) {
            int mj = primera_sangre.getColumna() - i;
            int pj = primera_sangre.getColumna() + i;
            if (mj >= 0) {
                this.direccionar_ataque(primera_sangre.getFila(), mj, distancia_maxima - i);
            }
            if (pj < this.dimension) {
                this.direccionar_ataque(primera_sangre.getFila(), pj, distancia_maxima - i);
            }
        }
    }

    public String definir_direccion(PosibleAtaque anterior, PosibleAtaque actual) {
        if (actual.casilla.getColumna() < anterior.casilla.getColumna()) {
            return "izq";
        } else {
            return "der";
        }
    }

    @Override
    public void procesarRespuesta(RespuestaJugada respuesta) {
        if (respuesta == RespuestaJugada.AGUA) {
            PosibleAtaque anterior = this.ataques_realizados.get(this.ataques_realizados.size() - 2);
            PosibleAtaque actual = this.ataques_realizados.get(this.ataques_realizados.size() - 1);
            switch (definir_direccion(anterior, actual)) {
                case "izq":
                    posibles_ataques.removeIf((PosibleAtaque ataque) -> ataque.casilla.getColumna() < actual.casilla.getColumna());
                    break;
                case "der":
                    posibles_ataques.removeIf((PosibleAtaque ataque) -> ataque.casilla.getColumna() > actual.casilla.getColumna());
                    break;
            }
        }
    }
}
