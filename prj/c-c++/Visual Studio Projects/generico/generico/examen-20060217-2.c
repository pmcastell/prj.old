#include <stdio.h>

#pragma warning(disable:4996)

//estructura para ejercicio 1
typedef struct {
	int cod;
	char nom[7];
} REG;
//estructuras para ejercicio 2

typedef struct {
	unsigned long int numCuenta;
	char nombreCli[50];
	long int saldo;
} REG_CLI;

typedef struct {
	int dia,
		mes,
		anio;
} FECHA;

typedef struct {
	unsigned long int codCuenta;
	FECHA fecha;
	char tipoMov;
	int cantidad;
} REG_MOV;



//prototipos ejercicio 1
int borra(int,REG[],int);
long contar_registros(FILE *,int);
//prototipos ejercicio 2
void crearClientes();
void crearMovimientos();
void actualizarClientes();
void listarClientes();
void listarErrores();

int main(int argc, char **argv) {
	crearClientes();
	crearMovimientos();
	actualizarClientes();
	listarClientes();
	listarErrores();
}
/*
1.Escribir una función para borrar un registro de tipo REG (int cod, char nom[7]), de un fichero llamado codigo.dat. 
La función recibe un código, que debe ser localizado y eliminado en caso de encontrarse; en caso contrario: ERROR. 
Prototipo de la función: LOGICO borra(int,REG[],int);Tener en cuenta:
* El número máximo de registros del fichero será 21.
* El campo cod es entero y positivo.
*La función devolverá el número del registro borrado, o -1 en caso de error.


*/


int borra(int codigo,REG tabla[],int tam) {
	FILE *pf=fopen("codigo.dat","rb");
	int i=0,j;
	int numRegs=contar_registros(pf,sizeof(REG));
	//comprobamos si código correcto
	if (codigo<0 || codigo>=contar_registros(pf,sizeof(REG)) || codigo>20) {
		return -1;
	}
	//cargamos registros en tabla
	fread(&tabla[i],sizeof(REG),1,pf);
	while(!feof(pf) && (i<tam-1 || i==codigo)) {
		if (i==codigo) {
			codigo--;
		} else {
			i++;
		}
		fread(&tabla[i],sizeof(REG),1,pf);
	}
	if (feof(pf)) {
		i--; // i queda apuntando al último elemento válido leído en la tabla
	}
	fclose(pf);
	//volcamos tabla en fichero
	pf=fopen("codigo.dat","wb");
	for(j=0;j<=i; j++) {
		fwrite(&tabla[j],sizeof(REG),1,pf);
	}
	fclose(pf);
	//devolvemos el código del registro borrado
	return codigo+1;
}


long contar_registros(FILE *pf, int num_bytes) {
	long pos=ftell(pf),res;
	fseek(pf,0,SEEK_END);
	res=ftell(pf)/num_bytes;
	fseek(pf,pos,SEEK_SET);
	return res;
}

/*
3.Se dispone de un fichero clientes.dat con los datos de las cuentas de los clientes de un banco y de un fichero 
mvtos.dat con las operaciones realizadas por dichos clientes. Dichas operaciones pueden ser de dos tipos: 'D' – Debe 
(gasto) y 'H' – Haber (ingreso).  Se desea actualizar el fichero de clientes con el nuevo saldo y obtener un nuevo 
fichero errores.dat con los registros del fichero mvtos.dat erróneos, es decir, que no tenían una cuenta asociada en el 
fichero clientes.dat.
	La estructura de los registros de dichos ficheros es la siguiente:
clientes.dat						mvtos.dat
numCuenta: entero largo sin signo			codCuenta: entero largo sin signo
nombreCli: cadena de 50 caracteres máximo	fecha: tipo compuesto por dia, fecha, anio
saldo: entero largo					tipoMov: un carácter (D ó H)		
							cantidad: entero 
errores.dat
Tiene la misma estructura que mvtos.dat.
NOTAS. 
Puede haber cuentas sin movimientos. 
Puede haber movimientos sin cuentas correspondientes en el fichero clientes.dat (deben aparecer en el fichero 
errores.dat).
Puede haber varios registros de mvtos.dat de una misma cuenta.
Los ficheros clientes.dat y mvtos.dat están ordenados ascendentemente por el campo número de cuenta (numCuenta y 
codCuenta, respectivamente).
*/



void actualizarClientes() {
	FILE *fcli,*fmov,*ferror;
	REG_CLI cliente;
	REG_MOV mvto;

	fcli=fopen("clientes.dat","r+b");
	fmov=fopen("mvtos.dat","rb");
	ferror=fopen("errores.dat","wb");
	printf("%-50s %-20s %20s\n","NOMBRE CLIENTE","NÚMERO DE CUENTA","SALDO");
	printf("%-50s %-20s %20s\n","==============","================","=====");
	lecturaFichero(&cliente,sizeof(REG_CLI),1,fcli);
	fread(&mvto,sizeof(REG_MOV),1,fmov);
	while(!feof(fcli) && !feof(fmov)) {
		if (cliente.numCuenta==mvto.codCuenta) { // cuenta con movimientos
			while(!feof(fmov) && cliente.numCuenta==mvto.codCuenta) {
				cliente.saldo+=(mvto.tipoMov == 'H' ? + mvto.cantidad : - mvto.cantidad); //suponemos que tipoMov está bien (H ó D)
				fread(&mvto,sizeof(REG_MOV),1,fmov);
			}
			if (cliente.saldo<0) {
				printf("%-50s %20lu %20ld\n",cliente.nombreCli,cliente.numCuenta,cliente.saldo);
			}
			//retrocedeEscribeLeeCliente(&cliente,fcli);
			fseek(fcli,ftell(fcli)-sizeof(REG_CLI),SEEK_SET); //retroceder puntero al principio del registro
			fwrite(&cliente,sizeof(REG_CLI),1,fcli);
			fflush(fcli); // es importante cuando se hacen escrituras/lecturas hacer fflush
			fread(&cliente,sizeof(REG_CLI),1,fcli);
		} else if (cliente.numCuenta>mvto.codCuenta) { // movimientos erróneos
			fwrite(&mvto,sizeof(REG_MOV),1,ferror);
			fread(&mvto,sizeof(REG_MOV),1,fmov);
		} else { // cliente.numCuenta<mvto.codCuenta   // cuentas sin movimientos
			if (cliente.saldo<0) {
				printf("%50s %20lu %20ld\n",cliente.nombreCli,cliente.numCuenta,cliente.saldo);
			}
			fread(&cliente,sizeof(REG_CLI),1,fcli);
		}
	}
	while(!feof(fmov)) {
		fwrite(&mvto,sizeof(REG_MOV),1,ferror);
		fread(&mvto,sizeof(REG_MOV),1,fmov);
	}
	fclose(fcli);
	fclose(fmov);
	fclose(ferror);

}


void crearClientes() {
	int i;
	REG_CLI clientes[]={
		{100,"Francisco López",300},{200,"Santiago Segura",400},
		{300,"Alberto Pérez",900},{400,"Luisa Fernández",1200},
		{500,"Pedro Fuentes",1500},{600,"Luis Aguirre",1800}
	};
	FILE *fcli=fopen("clientes.dat","wb");
	for(i=0; i<sizeof(clientes)/sizeof(REG_CLI); i++) {
		fwrite(&clientes[i],sizeof(REG_CLI),1,fcli);
	}
	fclose(fcli);
}

void crearMovimientos() {
	int i;
	REG_MOV mvtos[]={
		{100,{02,01,2006},'H',200},
		{300,{03,02,2006},'D',450},
		{300,{03,02,2006},'D',450},
		{300,{03,02,2006},'D',450},
		{300,{03,02,2006},'D',450},
		{600,{02,01,2006},'H',1200},
		{600,{02,01,2006},'D',1000},
		{600,{02,01,2006},'D',1000},
		{600,{02,01,2006},'D',1000},
		{600,{02,01,2006},'D',1000},
		{700,{02,03,2006},'H',1500},
	};
	FILE *fmov=fopen("mvtos.dat","wb");
	for(i=0; i<sizeof(mvtos)/sizeof(REG_MOV); i++) {
		fwrite(&mvtos[i],sizeof(REG_MOV),1,fmov);
	}
	fclose(fmov);
}


void listarClientes() {
	FILE *fcli=fopen("clientes.dat","rb");
	REG_CLI cliente;

	printf("Fichero de clientes: \n");
	fread(&cliente,sizeof(REG_CLI),1,fcli);
	while(!feof(fcli)) {
		printf("%20lu %50s %20ld.\n",cliente.numCuenta,cliente.nombreCli,cliente.saldo);
		fread(&cliente,sizeof(REG_CLI),1,fcli);
	}
}

void listarErrores() {
	REG_MOV mvto;
	FILE *ferror=fopen("errores.dat","rb");
	
	printf("Fichero de errores: \n");
	fread(&mvto,sizeof(REG_MOV),1,ferror);
	while(!feof(ferror)) {
		printf("%20lu %02d/%02d/%04d %c %20d",mvto.codCuenta,mvto.fecha.dia,mvto.fecha.mes,mvto.fecha.anio,mvto.tipoMov,mvto.cantidad);
		fread(&mvto,sizeof(REG_MOV),1,ferror);
	}
	fclose(ferror);

}