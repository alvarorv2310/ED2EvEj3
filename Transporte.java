package Transporte;
import java.io.*;

public class Transporte {
	final static String FVIAJES="viajes.txt";
	final static String FBILLETES="billetes.dat";
	final static String FNOVALIDOS="NoValidos.txt"; 
	Teclado t= new Teclado();
	final static double PRECIO1=100;
	final static double PRECIO2=1;

	final static int ANO=2023;

	void irARegistro(RandomAccessFile f, int pos, int tamReg) throws IOException {
		  f.seek(pos* tamReg);	  
	}
	void adquirirBillete()  throws IOException{
		Billete b=new Billete();
		RandomAccessFile f= new RandomAccessFile (FBILLETES,"rw");
		System.out.println("Tipo billete 1-anual, 2-90 minutos");
		int tipo=t.leerInt();
		if (tipo >=1 && tipo <=2) {
		  System.out.println("DNI:");
		  String dni=t.leerString();
		  Fecha hoy= new Fecha();
		  int numero=(int)f.length()/b.getTamanoRegistro();
  	      b=new Billete(numero, tipo, hoy.ano, hoy.mes,hoy.dia,hoy.hora, hoy.minuto, 1,dni);
  	      f.seek(f.length());
		  b.escribir(f);
		}
		else 
			System.out.println("Tipo no v�lido");
	}
	void detectarIrregularidades()  throws IOException {
	  File fv= new File(FVIAJES);
	  if (fv.exists()) {
		BufferedReader br= new BufferedReader(new FileReader(FVIAJES));
		RandomAccessFile f= new RandomAccessFile (FBILLETES,"rw");
		BufferedWriter bw= new BufferedWriter(new FileWriter(FNOVALIDOS));

		String s="", v[];
		Billete b=new Billete();
		boolean correcto=false;
		while (br.ready()) {
			s=br.readLine();
			v=s.split("#");
			b.numero=Integer.parseInt(v[0]);
			Fecha fViaje= new Fecha(Integer.parseInt(v[1]),Integer.parseInt(v[2]),
			Integer.parseInt(v[3]),Integer.parseInt(v[4]),Integer.parseInt(v[5]));
			irARegistro(f, b.numero, b.getTamanoRegistro());
			b.leer(f);
			correcto=false;
			if (b.tipo==1 && b.ano==fViaje.ano )
			  correcto=true;
			if (b.tipo==2 && 
			     new Fecha(b.ano,b.mes, b.dia, b.hora,b.minuto).menor90minutos(fViaje))  
			  correcto=true;
			if (!correcto) {
			  bw.write(b.dni+"#"+b.numero+"#"+fViaje.ano+"#"+fViaje.mes
					+"#"+fViaje.dia+"#"+fViaje.hora+"#"+fViaje.minuto);
			  bw.newLine();
			  if (b.tipo==2) {
			    irARegistro(f, b.numero, b.getTamanoRegistro());
			    b.activo=0;
			    b.escribir(f);
			  }
			  
			}
	    }// while
		br.close();
		f.close();
		bw.close();
	  }
	  else 
		  System.out.println("El fichero de viajes no existe");
	}
	void billetesCliente() throws IOException{
		RandomAccessFile f= new RandomAccessFile (FBILLETES, "r");
		Billete b= new Billete();
		System.out.println("DNI:");
		String dni=t.leerString();
		double total=0;
		boolean hayDatos=b.leer(f);
		Fecha fBillete= new Fecha(b.ano, b.mes, b.dia,b.hora,b.minuto);
		while (hayDatos){
			if (dni.equals(b.dni))
				switch (b.tipo) {
				case 1:total+= PRECIO1 ;break;  //PRECIO1*fBillete.
				case 2:total+= PRECIO2;break;
				}	
			hayDatos=b.leer(f);
		}
		System.out.println("TOTAL:"+total);
	}
	   void menu() throws IOException {
	    	Teclado t= new Teclado();
	    	int opc;
	    	do {
	    		System.out.println("Men�");
	    		System.out.println("1-Adquirir Billete");
	    		System.out.println("2-Detectar Irregularidades");
	    		System.out.println("3-Total Billetes Cliente ");
	    		System.out.println("0-FIN");
	    		opc=t.leerInt();
	    		switch (opc) {
	    		  case 1: adquirirBillete();break;
	    		  case 2: detectarIrregularidades();break;
	    		  case 3: billetesCliente();break;
	    		}
	    	}while (opc!=0);
	    }
	public static void main(String[] args)  throws IOException {
		Transporte t= new Transporte();
		t.menu();
	}

}
