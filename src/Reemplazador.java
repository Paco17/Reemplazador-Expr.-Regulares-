import javax.swing.JOptionPane;
/**
 * Mates Computacionales - Proyecto Reemplazador de Cadenas
 * 
 * @date 14/09/2020
 * @author Francisco Ramos A01636425*/
public class Reemplazador {
	
	/**
	 * Reemplazador de cadenas (Expresiones Regulares)
	 * Este programa se creo con el objetivo de implementar en software
	 * lo aprendido en Matem�ticas Computacionales sobre Expresiones Regulares
	 * 
	 * Haciendo un reemplazador de cadenas basandose en patrones ingresados por el usuario patrones.
	 * **/
	private String patron, reemplazador;
	private String[] patrones;
	private StringBuilder cadena;
	
	/**
	 * Constructor el cual cumple las siguientes funciones:
	 * 	1.- Inicializa los atributos
	 * 	2.- Divide lel atributo patron por cada uni�n '+'
	 * 	3.- Llama al m�todo matching para comenzar con el reemplazamiento */
	public Reemplazador() {
		//Poner datos
		inputDatos();
		
		//Divisi�n de patrones
		patrones = patron.split("\\+",0);
		
		//Se supone que todas tienen concatenacion con un caracter que no tenga *
		System.out.println("Cadena antes de reemplazar: "+cadena+"\n");
		matching();
		
	}
	
	/**
	 * Este m�todo compara cada letra del atributo cadena con el objetivo de hacer match con
	 * uno de los patrones
	 * 
	 * Sus funciones principales es la comparaci�n (si hay match llama al m�todo validacion(int, int))
	 * y reemplazar el substring de la cadena actual por el atributo de reemplazamiento.
	 * */
	private void matching() {
		//Verificar si hay estan en el arreglo
		int index; //variable para recorrer los distintos patrones del atributo patrones
		int inicio = 0;
		int ultimo = inicio;//Ultima posici�n del substring
		boolean match=false;
		while(inicio<cadena.length()) {
			index=0;
			while(index<patrones.length) {
				match = false;
				if(cadena.charAt(inicio)==patrones[index].charAt(0)||(patrones[index].length()>1&&patrones[index].charAt(1)=='*')){
					//Si coincide el primer caracter o si el caracter que no coincidio tiene * despues 
					ultimo = validacion(inicio, index);
					if(inicio<ultimo) {//Si ultimo>inicio significa si coincidio el patr�n
						inicio = reemplazo(inicio, ultimo);
						match = !match;
					}			
				}index = (match)?patrones.length:index+1;
			}inicio = (match)?inicio:inicio+1;	
		} System.out.print("Cadena despues de reemplazar: "+getCadena());
	}
	
	/**
	 * M�todo que se encarga de validar si el patron coincide con la sucesi�n de la cadena 
	 * desde el indice inicio que se as� como par�metro
	 * 
	 * @param int inicio (Letra donde coincide con el primer caracter del patr�n)
	 * @param int index (Indica cual de todos los patrones se esta comparando con la cadena)
	 * 
	 * @return int (Si coincidio el patr�n regresa la ultim� posici�n donde coincidio, 
	 * 				sino regresa el par�metro inicio)
	 * */
	private int validacion(int inicio, int index) {
		int end = inicio;
		int caracterIndex = 0;
		while(end<cadena.length()&&caracterIndex<patrones[index].length()) {
			if(cadena.charAt(end)==patrones[index].charAt(caracterIndex)) {
				//Mismo Character en la cadena y en el patr�n 
				end++; caracterIndex++;
			}else{
				if(patrones[index].charAt(caracterIndex)=='*') {
					//Si se encontr� una cerradura en el patr�n
					if(end>0&&cadena.charAt(end) == cadena.charAt(end-1))
						//Si se repite el mismo Character que la posici�n anterior ademas comprobando 
						end++;
					else {
						//Si ya se acabo la sucesi�n de a's entonces verifiquemos si no hay mas espacio en el patron
						caracterIndex++;
					}
				}else if(caracterIndex<patrones[index].length()-1 && patrones[index].charAt(caracterIndex+1)=='*'){
					//Si existe el caso de que no sea la misma letra pero tenga cerradura = 0
					caracterIndex++;
				}else {
					return inicio; //Si es una letra que obligatoriamente tiene que estar ahi
				}
			}
		}
		return checkIndexPatron(caracterIndex, end, inicio, index);
	}
	
	/**
	 * checkIndexPatron se encarga de una sub-verificaci�n de que termino primero si la cadena o el patr�n
	 * 
	 * @param int caracterIndex (Index donde se qued el patr�n)
	 * @param int end (�ltimo car�cter que se avanz� en la cadena)
	 * @param int inicio (Inicio de donde se comenz� la sub-cadena)
	 * @param int index (Posici�n que nos indica cu�l patr�n se est� utilizando)
	 * 
	 * @return int Regresa end si pertenece la subcadena al patr�n, de lo contrario regresa inicio
	 * */
	private int checkIndexPatron(int caracterIndex, int end, int inicio, int index) {
		if(caracterIndex>=patrones[index].length()-1) {
			return end; //Si se logr� completar e�l patr�n
		}else{
			while(caracterIndex<patrones[index].length()) {
				caracterIndex++;
				if(caracterIndex==patrones[index].length()) {
					//Si hay una letra y ya no hay espacio para *
					return inicio;
				}
				caracterIndex++;
				if(caracterIndex>=patrones[index].length()||patrones[index].charAt(caracterIndex)!='*'){
					//Si se llego al limite o tiene otro caracter en vez de la cerradura
					return inicio; 
					
				}else {
					if(caracterIndex==patrones[index].length()-1)
						//Si encontr� la cerradura y adem�s es el �ltimo caracter del patr�n
						return end;
				}
			}return inicio;
		}
	}
	
	/**
	 * M�todo que se encarga de hacer el reemplazo de la sub-cadena por la cadena de reemplazamiento
	 * 
	 * @param inicio 
	 * @param ultimo
	 * 
	 * @return int (Regresar� el �ltimo caracter de la sub-cadena)*/
	private int reemplazo(int inicio, int ultimo) {
		cadena.replace(inicio, ultimo, reemplazador);
		return inicio+reemplazador.length();
	}
	
	/**
	 * M�todo el cual su �nica funci�n es asignar valores a los atributos*/
	public void inputDatos() {
		/*Case Example cadena: "abaa aab aaanbbb"
					   patron: "a*b*"
					   reemplazador "X"*/
		
		cadena = new StringBuilder(JOptionPane.showInputDialog("Inserte Cadena a Reemplazar"));
		patron = JOptionPane.showInputDialog("\nInserte Patr�n a Observar");
		reemplazador = JOptionPane.showInputDialog("\nInserte el Reemplazador");
	}
	
	public String getCadena() {
		return cadena.toString();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Reemplazador remplazador = new Reemplazador();
	}

}
