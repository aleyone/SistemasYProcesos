package es.syp2.evaluable2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class llamadaNEOS2 {

	/**
	 * se crea una llamada para ejecutar la AEvaluable1.java con los parámetros que
	 * se le pasen. Se define un fichero de escritura cada vez que se ejecute la
	 * llamada desde main.java y construye el nombre con el nombre recibido + txt
	 * 
	 * @param args se le pasará el nombre, posición, velocidad del asteroide y un contador que viene de main.java
	 */
	public void crearNEOS(String[] args, int cont) {

		String nombreNEO = args[0];
		double posicionNEO = Double.parseDouble(args[1]);
		double velocidadNEO = Double.parseDouble(args[2]);
		int contador = cont;
		String fichero_Escritura = nombreNEO + ".txt";
		File fichero = new File(fichero_Escritura);

		String clase = "es.syp2.evaluable2.AEvaluable1";
		String javaHome = System.getProperty("java.home");
		String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
		String classPath = System.getProperty("java.class.path");
		String className = clase;

		ArrayList<String> command = new ArrayList<>();
		command.add(javaBin);
		command.add("-cp");
		command.add(classPath);
		command.add(className);
		command.add(nombreNEO);
		command.add(String.valueOf(posicionNEO));
		command.add(String.valueOf(velocidadNEO));

		ProcessBuilder builder = new ProcessBuilder(command);

		try {
			Process proceso = builder.redirectOutput(fichero).start();
			System.out.println("Este es el contador del lanzador de procesos: " + contador);
			if (contador >= 8) {
				System.out.println("Aquí hacemos el waitFor");
				proceso.waitFor();
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
