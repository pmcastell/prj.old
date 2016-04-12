/*
	Disponemos de un fichero denominado existen.dat donde se guarda la cantidad de piezas que
	hay en stock de una tienda. Asimismo, disponemos de un fichero denominado compras.dat 
	donde guardamos las compras realizadas a los proveedores de la tienda. En el fichero 
	compras.dat aparece la cantidad comprada de una determinada pieza, a partir de él se 
	deberá actualizar el fichero existen.dat con la nueva cantidad de piezas existente, que 
	será igual a la cantidad que había más la suma de las compras. Deberá generarse un fichero
	errores.dat con las piezas existentes en el fichero de compras que no aparecen en el 
	fichero maestro de existencias. Al mismo tiempo se deberá realizar un listado de aquellas
	piezas que después de realizadas las compras queden con una existencia por debajo de la 
	cantidad mínima que se desea haya almacenada (indicada en el campo existencia_minima).

La estructura de los registros de los ficheros es la siguiente:
existen.dat
codigo: entero sin signo
denominacion: cadena de 30 caract. máx.
existencia: entero sin signo
existencia_minima: entero sin signo
precio: entero sin signo
compras.dat
cod-pieza: entero sin signo
cantidad: entero sin signo
errores.dat
misma estructura que compras.dat

NOTAS:
Ambos ficheros están ordenados por el campo codigo y cod-pieza, respectivamente.
Puede haber piezas para las que no se ha realizado ninguna compra
Puede haber piezas con el código de pieza erróneo que no aparecen en existen.dat (deben aparecer en el fichero errores.dat)
Puede haber varias compras correspondientes a una misma pieza
*/
#include <stdio.h>
#pragma warning(disable:4996)

typedef struct {
	unsigned codigo;
	char denominacion[30];
	unsigned existencia,
		     existencia_minima;
	float	 precio;
} REG_EXISTENCIA;

typedef struct {
	unsigned cod_pieza,
		     cantidad;
} REG_COMPRAS;

void cabecera();
void imprimirExist();
void actualizarExistencias();
void escribirFichExist();
void escribirErrores();
void crearExistencias();
void crearCompras();

int main(int argc, char** argv) {
	crearExistencias();
	crearCompras();
	actualizarExistencias();
	escribirFichExist();
	escribirErrores();
}


void actualizarExistencias() {
	FILE *fexist, *fcompras, *ferror;
	REG_EXISTENCIA regExist;
	REG_COMPRAS regCompra;

	fexist=fopen("existen.dat","r+b");
	fcompras=fopen("compras.dat","rb");
	ferror=fopen("errores.dat","wb");
	fread(&regExist,sizeof(REG_EXISTENCIA),1,fexist);
	fread(&regCompra,sizeof(REG_COMPRAS),1,fcompras);
	cabecera();
	while (!feof(fexist) && !feof(fcompras)) { 
		if (regExist.codigo==regCompra.cod_pieza) { // pieza con compras
			while(!feof(fcompras) && regCompra.cod_pieza==regExist.codigo) {
				regExist.existencia+=regCompra.cantidad;
				fread(&regCompra,sizeof(REG_COMPRAS),1,fcompras);
			}
			if (regExist.existencia<regExist.existencia_minima) {
				imprimirExist(regExist); // imprimimos existencias que están debajo del mínimo
			}
			fseek(fexist,ftell(fexist)-sizeof(REG_EXISTENCIA),SEEK_SET);
			fwrite(&regExist,sizeof(REG_EXISTENCIA),1,fexist); // actualizamos el registro
			fflush(fexist); // vaciamos buffer de salida
			fread(&regExist,sizeof(REG_EXISTENCIA),1,fexist);
		} else if (regExist.codigo<regCompra.cod_pieza) { // pieza sin compras
			//no hay que actualizar nada 
			if (regExist.existencia<regExist.existencia_minima) {
				imprimirExist(regExist);
			}
			//sólo leer un nuevo registro
			fread(&regExist,sizeof(REG_EXISTENCIA),1,fexist);
		} else { //  (regExist.codigo>regCompra.cod_pieza)-> registro de compra erróneo
			fwrite(&regCompra,sizeof(REG_COMPRAS),1,ferror);
			fread(&regCompra,sizeof(REG_COMPRAS),1,fcompras);
		}
	}
	//tratamiento_colas fexist, fcompras;
	while(!feof(fexist)) {
		if (regExist.existencia<regExist.existencia_minima) {
			imprimirExist(regExist);
		}
		fread(&regExist,sizeof(REG_EXISTENCIA),1,fexist);
	}
	while(!feof(fcompras)) {
		fwrite(&regCompra,sizeof(REG_COMPRAS),1,fcompras);
		fread(&regCompra,sizeof(REG_COMPRAS),1,fcompras);
	}
	fclose(fexist);
	fclose(fcompras);
	fclose(ferror);
}
void cabecera() {
	printf("%-30s %+10s %+6s %+6s\n","DENOMINACION","EXISTENCIA","MÍNIMO","PRECIO");
	printf("%s %s %s %s\n","------------------------------","----------","------","------");
}

void imprimirExist(REG_EXISTENCIA ex) {
	printf("%-30s %10u %6u %6.2f\n",ex.denominacion,ex.existencia,ex.existencia_minima,ex.precio);
}

void crearExistencias() {
	REG_EXISTENCIA t[]={
		{10,"TUERCA",10,30,0.5F},{12,"TORNILLO",50,20,0.3F},{14,"DESTORNILLADOR",125,30,2.5F},
		{16,"ARANDELA",75,100,3.3F},{18,"ALCAYATA",33,20,0.75F},{20,"PUNTILLA",45,200,0.1F},
		{22,"LLAVE INGLESA",20,30,6.0F}
	};
	FILE * fexist=fopen("existen.dat","wb");
	int i;

	for(i=0; i<sizeof(t)/sizeof(REG_EXISTENCIA); i++) {
		fwrite(&t[i],sizeof(REG_EXISTENCIA),1,fexist);
	}
	fclose(fexist);
}

void crearCompras() {
	REG_COMPRAS t[]={
		{8,25},{10,20},{14,30},{14,30},{14,30},{18,13},{20,75},{20,79}
	};
	FILE *fcompras=fopen("compras.dat","wb");
	int i;

	for(i=0; i<sizeof(t)/sizeof(REG_COMPRAS);i++) {
		fwrite(&t[i],sizeof(REG_COMPRAS),1,fcompras);
	}
	fclose(fcompras);
}

void escribirFichExist() {
	FILE *fexist=fopen("existen.dat","rb");
	REG_EXISTENCIA r;

	printf("Fichero de existencias: \n");
	fread(&r,sizeof(REG_EXISTENCIA),1,fexist);
	while(!feof(fexist)) {
		imprimirExist(r);
		fread(&r,sizeof(REG_EXISTENCIA),1,fexist);
	}
	fclose(fexist);
}

void escribirErrores() {
	FILE *ferror=fopen("errores.dat","rb");
	REG_COMPRAS c;

	printf("Fichero de errores: \n");
	fread(&c,sizeof(REG_COMPRAS),1,ferror);
	while(!feof(ferror)) {
		printf("%d %d\n",c.cod_pieza,c.cantidad);
		fread(&c,sizeof(REG_COMPRAS),1,ferror);
	}
	fclose(ferror);
}
