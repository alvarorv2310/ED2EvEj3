package Transporte;
import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Billete {
  static int numBilletes=0;
  int numero;
  int tipo;  //1-año 2-90 minutos
  //Para que todos los tipos sean del mismo tamaño se añade un kk al tipo2
  int ano,mes,dia, hora, minuto;  
  int activo;  //1 -sin usar, 0-usado
  String dni; // cliente
  
  final static int TAMANODNI=10;
  
  public Billete(int numBillete, int tipo, int ano,int mes, int dia, int hora, 
		  int minuto, int activo, String dni) {
	super();
	this.numero = numBillete;
	this.tipo = tipo;
	this.ano = ano;
	this.mes = mes;
	this.dia = dia;
	this.hora = hora;
	this.minuto = minuto;
	this.activo = activo;
	this.dni = dni;
  }

  Billete(){  }

  int getTamanoRegistro(){
	// tamaño del array con el dni + tamaño de 8 int
	  return (TAMANODNI + 8*4);   
  }

  byte [] pasarStringaArrayBytes(String dni){
	  byte dniB[]=new byte[TAMANODNI];
	  for (int i=0; i<dni.length() && i<TAMANODNI; i++)
		    dniB[i]=(byte) dni.charAt(i);
	  for (int i=dni.length(); i<TAMANODNI; i++)
		    dniB[i]=(byte) 0;
	  return dniB;
  }

  /**
   * Elimina del string los caracteres vacíos (0)
   * @param s
   * @return el string sin vacíos.
   */
  String eliminarVacios (String s) {
	  StringBuffer sb= new StringBuffer (s);
	  int i=0;
	  while ( sb.charAt(i) != (char) 0  ) 
		  i++;
	  sb.setLength(i);
	  return (sb.toString() );
  }
  void escribir (RandomAccessFile f) throws IOException {
	  byte dniB[];
	  dniB=pasarStringaArrayBytes(dni);
	  f.writeInt(numero);
	  f.writeInt(tipo);
	  f.writeInt(ano);
	  f.writeInt(mes);
	  f.writeInt(dia);
	  f.writeInt(hora);
	  f.writeInt(minuto);
	  f.writeInt(activo);
	  f.write (dniB);
  }

  boolean leer (RandomAccessFile f) throws IOException { 
	  //devuelve true si lee algo y false si no devuelve nada
	  try {
	    byte dniB[]=new byte[TAMANODNI];
	    numero=f.readInt();
	    tipo=f.readInt();
	    ano=f.readInt();
	    mes=f.readInt();
	    dia=f.readInt();
	    hora=f.readInt();
	    minuto=f.readInt();
	    activo=f.readInt();
	    f.read (dniB,0,TAMANODNI);
	 //  dni=new String(dniB, "UTF-8"); //convierte el array de bytes en un string con la notación UTF-8
	    dni=new String(dniB, "ISO-8859-1"); //Mejor 8859 para ñ y acentos.
	    dni=eliminarVacios(dni);
	    return true;
	  }catch (EOFException e) {
		  return false;
	  }
  }

  @Override
  public String toString() {
	return "Billete [numero=" + numero + ", tipo=" + tipo +", ano=" + ano +  ", mes=" + mes + ", dia=" + dia + ", hora=" + hora
			+ ", minuto=" + minuto + ", activo=" + activo + ", dni=" + dni + "]";
  }
  
 }

