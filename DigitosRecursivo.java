package Transporte;


public class DigitosRecursivo {
	int contarDigitos(int n, int d) {
		if (n==0)
			return 0;
		else
		if ( n%10==d)
		   return  1+contarDigitos(n/10,d);
		else
			return contarDigitos(n/10,d);
	}
	public static void main(String[] args) {
		int suma=new DigitosRecursivo().contarDigitos(20145100,0);
		System.out.println(suma);
	}

}
