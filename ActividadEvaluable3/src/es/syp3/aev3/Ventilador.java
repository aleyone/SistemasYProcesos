package es.syp3.aev3;

public class Ventilador {
	int ventilador = 1;
	int tiempo = 2000;

	/**
	 * m�todos para encender y apagar el ventilador
	 */
	public void encenderVentilador() {
		while (true) {
			synchronized (this) {

				try {
					while (ventilador == 2)
						wait();
					System.out.println("------ Ventilador encendido\n");
					Thread.sleep(tiempo);
					ventilador = 2;
					notify();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void apagarVentilador() {
		while (true) {
			synchronized (this) {

				try {
					while (ventilador == 1)
						wait();
					System.err.println("------- Ventilador apagado.\n");
					Thread.sleep(tiempo);
					ventilador = 1;
					notify();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
