package co.edu.javeriana.algoritmos.proyecto;

import co.edu.javeriana.algoritmos.proyecto.ABMODEL.JugadorABMODEL;
import co.edu.javeriana.algoritmos.proyecto.greattaka.JugadorGT;
import co.edu.javeriana.algoritmos.proyecto.javaDabbaDoo.JugadorClass;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int prom = 0;
		int x;
		for(int i = 0; i<100;i++) {
			
	        int n = (int) ((Math.random()*99)+1);
	        int n_barcos =(int) ((Math.random()*(n/2-1))+1);
	        int[] barcos = new int[n_barcos];
	        for (int j = 0; j < n_barcos; j++) {
	            barcos[j] = (int) ((Math.random()*(n/2-1))+1);;
	        }
	        
			Jugador jugadorA = new JugadorClass();
			Jugador jugadorB = new JugadorABMODEL();
			
			Tablero tableroA = jugadorA.iniciarTablero(n, barcos);
			Tablero tableroB = jugadorB.iniciarTablero(n, barcos);
			ResumenTablero rtA = tableroA.obtenerResumen();
			ResumenTablero rtB = tableroB.obtenerResumen();
			
			jugadorA.recibirResumenRival(rtB);
			jugadorB.recibirResumenRival(rtA);
		
			x = 0;
			while(jugadorA.numeroBarcosNoHundidos()!=0 && jugadorB.numeroBarcosNoHundidos()!=0)
			{
				Casilla da = jugadorA.realizarDisparo();
				RespuestaJugada rjB = jugadorB.registrarDisparoAPosicion(da);
				jugadorA.procesarResultadoDisparo(rjB);
				Casilla db = jugadorB.realizarDisparo();
				RespuestaJugada rjA = jugadorA.registrarDisparoAPosicion(db);
				jugadorB.procesarResultadoDisparo(rjA);
				
				x++;
			}
			prom = prom + x;
			if(jugadorA.numeroBarcosNoHundidos()==0)
			{
				System.out.println("EL GANADOR ES " + jugadorB.obtenerNombre() +" despues de " + x + " disparos");
			}else System.out.println("EL GANADOR ES " + jugadorA.obtenerNombre()+ " despues de " + x + " disparos");
		}
		System.out.println("EL PROMEDIO DE DISPAROS ES "+ prom/100);
	}

}
