#include "stdafx.h"
#include <iostream>
#include <time.h>
#include <string.h>
#include <process.h>
#include <winsock2.h>

//este programa necesita de la librería del sdk: wsock32.lib
void _tmain(int argc, char *argv[])
{
	char *meses[]={"","JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
	char recibido[1024];
    SOCKET s;
    struct sockaddr_in a;
    struct hostent *h;
    WSADATA wsaData;
	int total;
	SYSTEMTIME stime;
	

    if (argc != 2)    {
		std::cout << "Usage: " << argv[0] << " host\n";
        exit(1);
    }
    if(WSAStartup(0x101, &wsaData))    {
		std::cout << "Unable to initialize WinSock library.\n";
        exit(1);
    }
    h = gethostbyname(argv[1]);
    if (h == NULL)    {
		std::cout << "Cannot resolve hostname\n";
        WSACleanup();
        exit(1);
    }
	printf("%u.%u.%u.%u",((unsigned char *)h->h_addr_list[0])[0],((unsigned char *)h->h_addr_list[0])[1],((unsigned char *)h->h_addr_list[0])[2],((unsigned char *)h->h_addr_list[0])[3]);
	/*
    a.sin_family = AF_INET;
    a.sin_port = htons(13);
    memcpy(&(a.sin_addr.s_addr), h->h_addr, sizeof(int));
    s = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (s == 0)    {
		std::cout << "Cannot establish connection: "
             << WSAGetLastError() << '\n';
        WSACleanup();
        exit(1);
    }
    if (connect(s, (struct sockaddr *)&a, sizeof(a))) {
		std::cout << "No se puede establecer la conexión: "
             << WSAGetLastError() << '\n';
        WSACleanup();
        exit(1);
    }
	if ((total=recv(s, recibido, 1024, 0)) == 0) {
		std::cout << "Imposible obtener tiempo.\n";
		exit(1);
	} 
	recibido[total]='\0';

	char *dia, *mes, *anio, *hora, *min, *seg;
	size_t offset;
	int i;
	
	dia=strtok(recibido," ");
	offset=strlen(dia)+1;
	mes=strtok(recibido+offset," ");
	for(i=1; i<=12 && strcmp(meses[i],mes)!=0; i++);
	if (i>10) {
		mes[0]='1';
		mes[1]='0'+i-10;
	} else {
		mes[0]='0';
		mes[1]='0'+i;
	}
	mes[2]=0;
	offset+=4;
	anio=strtok(recibido+offset," ");
	offset+=5;
	hora=strtok(recibido+offset,":");
	offset+=3;
	min=strtok(recibido+offset,":");
	offset+=3;
	seg=strtok(recibido+offset," ");
	
	stime.wYear=atoi(anio);
	stime.wMonth=atoi(mes);
	stime.wDay=atoi(dia);
	stime.wHour=atoi(hora);
	stime.wMinute=atoi(min);
	stime.wSecond=atoi(seg);
	//stime.wDayOfWeek=0;
	stime.wMilliseconds=0;
	if (SetLocalTime(&stime)) {
		printf("Fecha establecida correctamente: %2s/%2s/%4s %2s:%2s:%2s.",dia,mes,anio,hora,min,seg);
	} else {
		printf("Error estableciendo la fecha del sistema.");
	}
	/*strcpy(command,getenv("COMSPEC"));
	if (_spawnl(_P_WAIT,command," /c date ",dia,"/",mes,"/",anio)==-1) {
		printf("Error estableciendo fecha. ");
	} else if (_spawnl(_P_WAIT,command," /c time ",hora,":",min,":",seg)==-1) {
		printf("Error estableciendo hora.");
	} else {
		printf("Fecha establecida correctamente: %2s/%2s/%4s %2s:%2s:%2s.",dia,mes,anio,hora,min,seg);
	}*/
	//printf("%s",recibido);
    //closesocket(s);
    //WSACleanup();
}