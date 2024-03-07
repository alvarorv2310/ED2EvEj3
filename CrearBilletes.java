package Transporte;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CrearBilletes {
	final static String FICHBILLETES="billetes.dat";
	void crearFichBilletes() throws IOException {
		RandomAccessFile f= new RandomAccessFile (FICHBILLETES,"rw");
		Billete b=new Billete(0,1,2023,11,13,14,3,1,"12345678A");
		b.escribir(f);
		b=new Billete(1,2,2023,11,13,14,5,1,"12345679B");
		b.escribir(f);
		b=new Billete(2,1,2023,11,13,14,6,1,"12345670C");
		b.escribir(f);
		b=new Billete(3,1,2023,11,13,14,8,1,"12345670C");
		b.escribir(f);
		b=new Billete(4,2,2023,11,13,14,18,1,"12345672D");
		b.escribir(f);
		b=new Billete(5,1,2023,11,13,14,42,1,"12345670C");
		b.escribir(f);
		f.close();
	}
	void listarFichBilletes () throws  IOException{
		RandomAccessFile f= new RandomAccessFile (FICHBILLETES, "r");
		Billete b= new Billete();
		boolean hayDatos=b.leer(f);
		while (hayDatos){
			System.out.println(b);
			hayDatos=b.leer(f);
		}
	}
	public static void main(String[] args) throws IOException {
		CrearBilletes c= new CrearBilletes();
		//c.crearFichBilletes();
		c.listarFichBilletes();
	}

}
