package es.syp3.aev3;

public class Minero implements Runnable {
	private int bolsa;
	Mina mina;

	public Minero(Mina mina) {
		this.bolsa = 0;
		this.mina = mina;
	}

	/**
	 * Se recupera el nombre del minero, la cantidad de oro que extrae y se llama a
	 * extraerRecurso
	 */
	public void run() {
		int extraccion = 1;
		String nombre = Thread.currentThread().getName();
		extraerRecurso(nombre, extraccion);
	}

	/**
	 * mientras que la mina tenga oro, seguimos con la extracción. Se descuenta una
	 * unidad de oro en la mina y se suma esa unidad a la bolsa del minero
	 * 
	 * @param nombre     se le pasa el nombre del minero
	 * @param extraccion se le pasa la cantidad de oro a extraer de la mina en un
	 *                   turno
	 */
	synchronized public void extraerRecurso(String nombre, int extraccion) {
		while (true) {
			if (extraccion <= mina.getStock()) {
				mina.setStock(mina.getStock() - extraccion);
				this.setBolsa(extraccion);

				// Bucle ficticio para que el procesador pueda retardar el proceso de los hilos
				for (int i = 0; i < 1e7; i++) {
					double resultadoTonto = Math.sqrt((double) (i));
				}

				// Se llama al descanso tras extraer el oro
				descansar();

				System.out.println("El minero " + nombre + " ha extraido " + extraccion + " monedas.");

			} else {
				try {
					Thread.sleep(30000); // Para mostrar el resultado de lo recolectado a la vez
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.err.println("La bolsa de " + nombre + " tiene: " + bolsa + " monedas.");
				break;
			}

		}
	}

	/**
	 * actualizmos la bolsa del minero con lo extraido
	 * 
	 * @param extraccion cantidad de oro extraida por el minero
	 */
	public void setBolsa(int extraccion) {
		this.bolsa = this.bolsa + extraccion;
	}

	/**
	 * recuperamos la bolsa del minero tras todos los turnos
	 * 
	 * @return
	 */
	public int getBolsa() {
		return this.bolsa;
	}

	/**
	 * se genera el descanso del minero tras recolectar. Se sincroniza para que el
	 * descanso sea secuencial
	 */
	synchronized public void descansar() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
