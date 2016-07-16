package tanquery;

import java.util.ArrayList;

//import TUIO.TuioObject;

public class Token{

	private int id;
	private String tipo;
	//private String simbolo;
	private String valorDisp;
	private String valorSql;
	private int izq;
	private int der;
	private int padre;
	ArrayList<Integer> hijos;
	private long idSesion;

	public Token(int id, String tipo, String valorDisp, String valorSql,
			int izq, int der, int padre, long idSesion) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.valorDisp = valorDisp;
		this.valorSql = valorSql;
		this.izq = izq;
		this.der = der;
		this.padre = padre;
		this.setIdSesion(idSesion);

	}

	public Token() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getValorDisp() {
		return valorDisp;
	}

	public void setValorDisp(String valorDisp) {
		this.valorDisp = valorDisp;
	}

	public String getValorSql() {
		return valorSql;
	}

	public void setValorSql(String valorSql) {
		this.valorSql = valorSql;
	}

	public int getIzq() {
		return izq;
	}

	public void setIzq(int izq) {
		this.izq = izq;
	}

	public int getDer() {
		return der;
	}

	public void setDer(int der) {
		this.der = der;
	}

	public int getPadre() {
		return padre;
	}

	public void setPadre(int padre) {
		this.padre = padre;
	}

	public long getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(long idSesion) {
		this.idSesion = idSesion;
	}

	

}
