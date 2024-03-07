package Transporte;
import java.util.Calendar;
import java.util.Date;

public class Fecha {
  int ano, mes, dia, hora, minuto;
  Fecha(int ano, int mes, int dia){
	  this.ano=ano;
	  this.mes=mes;
	  this.dia=dia;
  }

  	boolean menor90minutos(Fecha fPosterior) {
  		boolean ok=false;
  		int auxh=hora*60+minuto+90;
  		int auxhPost= fPosterior.hora*60+fPosterior.minuto;
  		System.out.println(fPosterior.hora+":"+fPosterior.minuto+",,"
  		  +auxh+" ->"+auxhPost);
  		if (ano==fPosterior.ano && mes==fPosterior.mes && 
  				dia==fPosterior.dia && auxhPost<=auxh)
  			ok=true;
  		return ok;	
  	}
	public Fecha(int ano, int mes, int dia, int hora, int minuto) {
	  this.ano = ano;
	  this.mes = mes;
	  this.dia = dia;
	  this.hora = hora;
	  this.minuto = minuto;
    }

	public static void main(String[] args) {
		Fecha f= new Fecha(1,11,1);
		System.out.println(f.ano+":"+f.mes+":"+f.dia+":"+f.hora+":"+
		f.minuto);
	}

}
