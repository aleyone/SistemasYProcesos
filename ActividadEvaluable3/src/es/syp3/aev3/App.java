package es.syp3.aev3;

import java.util.ArrayList;

public class App {

	@SuppressWarnings("deprecation") // A�adimos para poder usar el Thread.stop();
	public static void main(String[] args) {
		Mina miMina = new Mina(50);
		Minero soyMineroooo = null;
		int totalRecolectado = 0;

		// Se crea una lista de objetos mineros para poder extraer m�s tarde la bolsa ya
		// que si se obtiene la bolsa dentro de la creaci�n del hilo, se recupera antes
		// de que hayan acabado de extraer oro y est� inconsistente
		ArrayList<Minero> mineros = new ArrayList<Minero>();
		Ventilador ventilacion = new Ventilador();

		// Encendemos los ventiladores para que los mineros entren seguros en la mina
		Thread encenderVentilador = new Thread(new Runnable() {
			public void run() {
				ventilacion.encenderVentilador();
			}
		});

		Thread apagarVentilador = new Thread(new Runnable() {
			public void run() {
				ventilacion.apagarVentilador();
			}
		});

		encenderVentilador.start();
		apagarVentilador.start();

		// Creamos los mineros y los mandamos a la mina
		Thread hilo;
		for (int i = 1; i <= 10; i++) {
			soyMineroooo = new Minero(miMina);
			mineros.add(soyMineroooo);
			hilo = new Thread(soyMineroooo);
			hilo.setName("Minero " + i);
			hilo.start();
		}
		try {
			Thread.sleep(21000); // Se retarda un poco m�s que el sleep de los mineros para sacar el getBolsa
									// finalizado
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < mineros.size(); i++) {
			totalRecolectado = totalRecolectado + mineros.get(i).getBolsa();
		}

		try {
			Thread.sleep(21000); // Se retarda un poco para que el resultado total se muestre al final del resto
									// de procesos del c�digo
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Quedan " + miMina.getStock() + " monedas en la mina.");
		System.out.println("El total de monedas recogidas por los mineros es de: " + totalRecolectado);

		// No he sabido parar el proceso de otra forma a pesar de que est�
		// "depreciated". Al ser las �ltimas instrucciones del c�digo ya no afecta a
		// ning�n wait() o run()
		apagarVentilador.stop();
		encenderVentilador.stop();

//		}
	}

}
