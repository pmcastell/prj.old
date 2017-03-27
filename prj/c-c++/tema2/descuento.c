int calcularDescuento(int antiguedad, float volumenDeCompras, int especial)
{
	int descuento;
	if (especial==1) {
		if (volumenDeCompras>5000000) {
		   descuento=25;
		} else {
			descuento=20;
		}
	} else {
		if (antiguedad>5) {
			if (volumenDeCompras>5000000) {
				descuento=25;
			} else if (volumenDeCompras>=3000000) {
				descuento=15;
			} else {
				descuento=10;
			}
		} else if (antiguedad>=3) {
			if (volumenDeCompras>4000000) {
				descuento=11;
			} else {
				descuento=5;
			}
		} else {
			if (volumenDeCompras>4000000) {
				descuento=9;
			} else {
				descuento=0;
			}
		}
	}
	return descuento;
}
