package es.syp2.evaluable2;

import java.text.DecimalFormat;

public class AEvaluable1 {

	/**
	 * esta clase actua como una aplicación que recoge unos valores, calcula una
	 * probabilidad de colisión y devuelve un mensaje en pantalla en función de esa
	 * probabilidad con decimales en 2 dígitos.
	 * 
	 * @param args se le pasa nombre, posición y velocidad del asteroide
	 */
	public static void main(String[] args) {

		String nombreNEO = args[0];
		double posicionNEO = Double.parseDouble(args[1]);
		double velocidadNEO = Double.parseDouble(args[2]);
		double probabilidad = probabilidadColision(posicionNEO, velocidadNEO);
		String probabilidad2dig = String.format("%.2f", probabilidad);
		if (probabilidad <= 10) {
			System.out.println("Asteroide " + nombreNEO + " con probabilidad del " + probabilidad2dig
					+ "% de colisión: Todo va chachi, podéis vivir en paz.");

		} else // no lo imprimo en pantalla con el System.err.println ya que ese tipo de código
				// NO se imprime en el fichero con redirectOutput
			System.out.println("Asteroide " + nombreNEO + " con probabilidad del " + probabilidad2dig
					+ "% de colisión: ¡¡¡ VAIS A MORIR TODOS | TODAS | TODES !!!.");
	}

	/**
	 * método para calcular la probabilidad de colisión
	 * 
	 * @param posicionNEO  se recoge la posición del asteroide
	 * @param velocidadNEO se recoge la velocidad del asteroide
	 * @return se devuelve la probabilidad
	 */
	public static double probabilidadColision(double posicionNEO, double velocidadNEO) {
		double resultado = 0;
		double posicionTierra = 1;
		double velocidadTierra = 100;
		for (int i = 0; i < (50 * 365 * 24 * 60 * 60); i++) {
			try {
				posicionNEO = posicionNEO + velocidadNEO * i;
				posicionTierra = posicionTierra + velocidadTierra * i;
				resultado = 100 * Math.random()
						* Math.pow(((posicionNEO - posicionTierra) / (posicionNEO + posicionTierra)), 2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}
}