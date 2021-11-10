package es.syp3.aev3;

public class Mina {
	private int stock;

	public Mina(int stock) {
		this.stock = stock;
	}

	synchronized public void setStock(int stock) {
		this.stock = stock;
	}

	synchronized public int getStock() {
		return this.stock;
	}
}
