package es.syp2.evaluable2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class main {

	/**
	 * recorremos el fichero dado NEOs.txt y en cada fila creamos un proceso
	 * llamando a llamadaNEOS2.java. Se le pasa por par�metros un array de strings
	 * que es el contenido de una l�nea de texto del fichero separando los
	 * componentes con split. Se calcula el tiempo del proceso parcial y el tiempo
	 * total
	 * 
	 * @param args los argumentos los recoge directamente desde el fichero de texto
	 */
	public static void main(String[] args) {
		llamadaNEOS2 llamada = new llamadaNEOS2();
		String ficheroNEO = "NEOs.txt";
		int procesadores = Runtime.getRuntime().availableProcessors();
		long tiempoInicio = 0, tiempoFinal = 0, tiempoParcial = 0, tiempoTotal = 0;

		try {
			File fichero = new File(ficheroNEO);
			FileReader fr = new FileReader(fichero);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			int contador = 1; // se crea una variable contador para enviarlo en la llamada a crerNEOS. De esa
								// forma podemos pasarle un par�metro para controlar el waitFor();
			while (linea != null) {
				if (contador <= procesadores) {
					String[] NEO = linea.split(",");
					tiempoInicio = System.currentTimeMillis();
					llamada.crearNEOS(NEO, contador);
					tiempoFinal = System.currentTimeMillis();
					tiempoParcial = tiempoFinal - tiempoInicio;
					System.out.println(
							"Hola, esta es la l�nea: " + contador + "---" + NEO[0] + " " + NEO[1] + " " + NEO[2] + " ");
					linea = br.readLine();
					contador++;
					System.out.println("Tiempo de ejecuci�n parcial: " + tiempoParcial);
					tiempoTotal += tiempoTotal + tiempoParcial;

				} else {
					contador = 1;
				}

			}
			System.out.println("El tiempo total de ejecuci�n es: " + tiempoTotal + "\nEl tiempo medio de ejecuci�n es: "
					+ (tiempoTotal / 12));

		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

}
