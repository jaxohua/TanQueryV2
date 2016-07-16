package tanquery;

public class NodoArbol {
	// miembros de acceso
	NodoArbol nodoizquierdo;
	int id;
	Token token;
	NodoArbol nododerecho;

	// iniciar dato y hacer de este nodo un nodo hoja
	public NodoArbol(int datosNodo) {
		id = datosNodo;
		nodoizquierdo = nododerecho = null; // el nodo no tiene hijos
	}

	// buscar punto de inserción e insertar nodo nuevo
	public synchronized void insertar(int valorInsertar, String ubicacion) {
		// insertar en subarbol izquierdo
		if (ubicacion == "izquierda") {
			// insertar en subarbol izquierdo
			if (nodoizquierdo == null)
				nodoizquierdo = new NodoArbol(valorInsertar);
			else
				// continua recorriendo subarbol izquierdo
				nodoizquierdo.insertar(valorInsertar, ubicacion);
		}

		// insertar nodo derecho
		else if (ubicacion == "derecha") {
			// insertar nuevo nodoArbol
			if (nododerecho == null)
				nododerecho = new NodoArbol(valorInsertar);
			else
				nododerecho.insertar(valorInsertar, ubicacion);
		}
	} // fin del metodo insertar
}

class Arbol {
	private NodoArbol raiz;

	// construir un arbol vacio
	public Arbol() {
		raiz = null;
	}

	// insertar un nuevo nodo en el arbol de busqueda binaria
	public synchronized void insertarPadre(int valorInsertar) {
		if (raiz == null){
			raiz = new NodoArbol(valorInsertar); // crea nodo raiz
			System.out.println("Nodo raiz=>"+raiz.id);
		}

	}

	public synchronized void insertarNodo(int valorInsertar, String ubicacion) {

		raiz.insertar(valorInsertar, ubicacion); // llama al metodo insertar
	}

	// EMPIEZA EL RECORRIDO EN PREORDEN
	public synchronized void recorridoPreorden() {
		ayudantePreorden(raiz);
	}

	// metodo recursivo para recorrido en preorden

	private void ayudantePreorden(NodoArbol nodo) {
		if (nodo == null)
			return;

		System.out.print(nodo.id + " "); // mostrar datos del nodo
		ayudantePreorden(nodo.nodoizquierdo); // recorre subarbol izquierdo
		ayudantePreorden(nodo.nododerecho); // recorre subarbol derecho
	}

	// EMPEZAR RECORRIDO INORDEN
	public synchronized void recorridoInorden() {
		ayudanteInorden(raiz);
	}

	// metodo recursivo para recorrido inorden
	private void ayudanteInorden(NodoArbol nodo) {
		if (nodo == null)
			return;

		ayudanteInorden(nodo.nodoizquierdo);
		System.out.println(nodo.id + "-> Hijo Izq:"+nodo.nodoizquierdo.id+"  Hijo Der:"+nodo.nododerecho.id);
		ayudanteInorden(nodo.nododerecho);
	}

	// EMPEZAR RECORRIDO PORORDEN
	public synchronized void recorridoPosorden() {
		ayudantePosorden(raiz);
	}

	// metodo recursivo para recorrido posorden
	private void ayudantePosorden(NodoArbol nodo) {
		if (nodo == null)
			return;

		ayudantePosorden(nodo.nodoizquierdo);
		ayudantePosorden(nodo.nododerecho);
		System.out.print(nodo.id + " ");
	}

}
