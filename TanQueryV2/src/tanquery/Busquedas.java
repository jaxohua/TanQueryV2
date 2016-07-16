package tanquery;

import java.util.ArrayList;

import TUIO.TuioObject;

public class Busquedas {
	// Regresa el numero de intervalo en donde se encuentra el numero de grados
	public static int getIndex(ArrayList<Intervalo> intervalos, float grados) {
		for (Intervalo inter : intervalos) {
			if (grados >= inter.inicio && grados <= inter.fin) {
				return intervalos.indexOf(inter);
			}

		}
		return -1;

	}

	public static ArrayList<Intervalo> generaIntervalo(int ini, int fin,
			int rango) {
		System.out.println("Generando Intervalos");
		ArrayList<Intervalo> inter = new ArrayList<Intervalo>();
		for (int i = ini; i < fin; i += rango) {
			inter.add(new Intervalo(i, i + rango));
		}
		return inter;
	}

	public static TuioObject buscaId(ArrayList<TuioObject> objectList, int id) {
		for (TuioObject objeto : objectList) {
			if (objeto.getSymbolID() == id) {
				return objeto;

			}
		}
		return null;
	}

	public static boolean buscaId(TanQuery frame, int id) {
		for (TuioObject objeto : frame.tuioClient.getTuioObjectList()) {
			if (objeto.getSymbolID() == id) {
				return true;
			}
		}
		return false;
	}

	public static int buscaId(TanQuery frame, String tipo) { //busca si esta en el rango y regresa el numero de id
		for (TuioObject objeto : frame.tuioClient.getTuioObjectList()) {
			if (Busquedas.isinRange(objeto.getScreenX(frame.width),
					objeto.getScreenY(frame.height))) {
				if (Valores.whatIs(objeto.getSymbolID()) == tipo) {
					return objeto.getSymbolID();
				}
			}
		}

		return -1;
	}

	public static int objectsInRange(TanQuery frame) {
		int contador = 0;
		ArrayList<TuioObject> lista = frame.tuioClient.getTuioObjectList();
		for (TuioObject objeto : lista) {
			if (Busquedas.isinRange(objeto.getScreenX(frame.width),
					objeto.getScreenY(frame.height))) {
				contador++;
			}
		}
		return contador;
	}

	public static boolean isInToken(ArrayList<Token> tokens, int id) {
		if (tokens.size() > 0) {
			for (Token token : tokens) {
				if (token.getId() == id) {
					return true;
				}
			}
		}
		else{
			System.out.println("La lista de tokens esta vacia.");
		}
		return false;
	}

	public static boolean isinRange(float x, float y) {
		float x1 = 0;
		float x2 = 400;
		float y1 = 0;
		float y2 = 400;
		if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
			// System.out.println("El objeto esta en rango:" + x + " , " + y);
			return true;
		}
		// System.out.println("Fuera de Rango x:" + x + " y:" + y);

		return false;

	}
	public static boolean isinRange(TanQuery frame, int id) {
		ArrayList<TuioObject> lista = frame.tuioClient.getTuioObjectList();
		for (TuioObject objeto : lista) {
			if (objeto.getSymbolID()==id) {
				return true;
			}
		}
		return false;
		
	}
	
	public static boolean isInTrabajo(float x, float y) {
		float x1 = 520;
		float x2 = x1+750;
		float y1 = 5;
		float y2 = 670;
		if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
			 System.out.println("El objeto esta en rango:" + x + " , " + y);
			return true;
		}
		System.out.println("Fuera de Rango x:" + x + " y:" + y);

		return false;

	}


	public static boolean isToSave(TanQuery frame) {
		for (TuioObject objeto : frame.tuioClient.getTuioObjectList()) {
			if (Valores.whatIs(objeto.getSymbolID()) == "SAVE"
					&& isinRange(objeto.getScreenX(frame.width),
							objeto.getScreenY(frame.height))) {
				return true;
			}
		}
		return false;

	}
	
}
