package Transporte;
import java.io.*;

public class Vuelo {
	boolean asientosOcupados[][];
	int numVuelo;
	Vuelo(int nVuelo){
		int filas=0, columnas=0;
		boolean error=false;
		try (BufferedReader bf=new BufferedReader(new FileReader("vuelo"+nVuelo+".txt"))){
			String linea=bf.readLine();
			String datos[]=linea.split("#");
			filas=Integer.parseInt(datos[1]);
			columnas=Integer.parseInt(datos[2]);
		}catch (Exception e) {
			try (BufferedWriter bw=new BufferedWriter(new FileWriter("vuelo"+nVuelo+".txt"))) {
				Teclado t= new Teclado();
				System.out.print("fila:");
				filas=t.leerInt();
				System.out.print("asiento en la fila:");
				columnas=t.leerInt();
				bw.write(nVuelo+"#"+filas+"#"+columnas);
				bw.newLine();
			}catch (Exception e2) {
				System.out.println("Error en la creación del fichero");
				error=true;
			}
		}
		if (!error) {
	  	  numVuelo=nVuelo;
		  asientosOcupados= new boolean[filas][columnas];
		  for (int i=0;i<filas;i++)
			  for (int j=0; j<columnas;j++)
				  asientosOcupados[i][j]=false;
		}
		else numVuelo=-1;
	}
	boolean errorYaVendido(int fila, int col, int asientos) {
		boolean error=false;
		for (int i=0; i<asientos;i++)
			if (asientosOcupados[fila][col+i])
				error=true;
		return error;
	}
	void vender(int fila, int col, int asientos) {
		for (int i=0; i<asientos;i++)
			asientosOcupados[fila][col+i]=true;
	}
	int errorVenta(int fila, int columna, int asientos) {
		int error=0;
		if (fila>=asientosOcupados.length || fila<0) 
		  error=1;
    	else
	      if (columna+asientos>=asientosOcupados[0].length || columna <0)
		    error=2;
	      else
	        if (errorYaVendido(fila, columna, asientos))
		      error=3;
		return error;
	}
	boolean comprobarFichero(int nVuelo){
		boolean error=false;
		int fila, columna, plazas;
		String dni;
		int errorLinea=0;
		try (BufferedReader bf=new BufferedReader(new FileReader("vuelo"+nVuelo+".txt"))){
			String linea=bf.readLine();
			int contLinea=1;
			while (bf.ready()) {
				linea=bf.readLine();
				String datos[]=linea.split(";");
				fila=Integer.parseInt(datos[0]);
				columna=Integer.parseInt(datos[1]);
				plazas=Integer.parseInt(datos[2]);
				dni=datos[3];
				errorLinea=errorVenta(fila, columna, plazas);
			//System.out.println(errorLinea);
				if (errorLinea!=0) {
					error=true;
					System.out.print(contLinea+"->"+linea);
					switch (errorLinea) {
					case 1-> System.out.println(" Fila incorrecta");
					case 2->System.out.println(" Asiento incorrecto");
					case 3->System.out.println(" Asiento ya vendido");
					}
				}
				else 
					vender(fila,columna,plazas);
				contLinea++;
				errorLinea=0;
			}
		}catch (Exception e) {
			error=true;
		}
		return error;
	}
	void realizarVenta(int nVuelo) throws IOException {
		int error=0;
		Teclado t= new Teclado();
		System.out.println("Introduzca los datos de la venta.");
		System.out.print("fila:");
		int fila=t.leerInt();
		System.out.print("asiento en la fila:");
		int asiento=t.leerInt();
		System.out.print("¿Cuántos asientos?:");
		int numAsientos=t.leerInt();
		if (( error=errorVenta(fila, asiento, numAsientos))==0){
			System.out.println("dni ");
			String dni=t.leerString();
			try (BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new 
				FileOutputStream("vuelo"+nVuelo+".txt",true)))){
				bw.write(fila+";"+asiento+";"+numAsientos+";"+dni);
			}catch (Exception e) {
				System.out.println("no se puede acceder al fichero."+"vuelo"+nVuelo+".txt");
			}
		}
		else 
			System.out.println("Hay errores en los datos:"+error);
	}
	public static void main(String[] args) throws IOException {
		System.out.println("Dar num vuelo");
		Teclado t= new Teclado();
		int nvuelo=t.leerInt();
		Vuelo v=new Vuelo(nvuelo);
		if (v.numVuelo!=-1)
			if (v.comprobarFichero(nvuelo))
				System.out.println("El fichero tiene errores");
			else
				v.realizarVenta(nvuelo);
	}
}
