#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#pragma warning(disable:4996)

/*
5.- Mira la librería string.h y realiza los algoritmos de todas las funciones que contenga 
que no hayamos mencionado ya..


String Operations <string.h>
s,t are strings, cs,ct are constant strings
length of s strlen(s)
copy ct to s strcpy(s,ct)
up to n chars strncpy(s,ct,n)
concatenate ct after s strcat(s,ct)
up to n chars strncat(s,ct,n)
compare cs to ct strcmp(cs,ct)
only first n chars strncmp(cs,ct,n)
pointer to first c in cs strchr(cs,c)
pointer to last c in cs strrchr(cs,c)
copy n chars from ct to s memcpy(s,ct,n)
copy n chars from ct to s (may overlap) memmove(s,ct,n)
compare n chars of cs with ct memcmp(cs,ct,n)
pointer to first c in first n chars of cs memchr(cs,c,n)
put c into first n chars of cs memset(s,c,n)
*/
//Esas funciones son las siguientes (precedidas de 'mi':*/
char *mistrcpy( char *strDestination, const char *strSource );
char *mistrncpy( char *strDest, const char *strSource, size_t count );
char *mistrncat( char *strDest, const char *strSource, size_t count );
int mistrcmp(const char *string1,const char *string2);
int mistrncmp(const char *string1,const char *string2,size_t count);
char *mistrchr(const char *string,int c);
char *mistrrchr(const char *string,int c);
void *mimemcpy(void *dest,const void *src,size_t count);
void *mimemmove(void *dest,const void *src, size_t count);
int mimemcmp(const void *buf1,const void *buf2,size_t count);
void *mimemchr(const void *buf,int c,size_t count);
void *mimemset(void *dest,int c,size_t count);

void main() {
    char c[50]="xxxxxxxxxxxxxxxxxxxxxxxxxxxxx", c2[]="hola que tal";
    int cmp;
    char *pc;
    void *at;

    mistrcpy(c,c2);
    mistrncpy(c,c2,strlen(c2)+2);
    mistrncat(c,"hola",7);
    mistrncat(c,c2,8);
    cmp=mistrcmp("adios","adio");
    cmp=mistrncmp("hola","hospedaje",2);
    pc=mistrchr("hola",'l');
    pc=mistrrchr("hola",'S');
    mimemcpy(c,c2,strlen(c2)+1);
    mimemmove(c,c2,strlen(c2)+1);
    cmp=mimemcmp("holas","hola",4);
    at=mimemchr("hola que tal",'m',12);
    mimemset(c,'a',49);
}
//copia una cadena sobre otra y devuelve la cadena destino
char *mistrcpy( char *strDestination, const char *strSource ) {
    char *res=strDestination;

    while(*strDestination++=*strSource++);
    return res;
}
//copia los count caracteres primeros de strSource sobre strDest
//si count es mayor que la longitud de strSource se añaden caracteres
//nulos hasta que el número de carácteres añadido sea igual a count
char *mistrncpy( char *strDest, const char *strSource, size_t count ) {
    unsigned int i;
    char *res=strDest;

    for(i=0; i<count; i++) {
        *strDest=*strSource;
        strDest++;
        if (*strSource) {
            strSource++;
        }
    }
    *strDest='\0';
    return res;
}
//concatena count caracteres de strSource al final de strDest
//si count es mayor que la longitud de strSource solo añade 
//un número de carácteres igual a la longitud de strSource
char *mistrncat( char *strDest, const char *strSource, size_t count ) {
    char *res=strDest;
    unsigned int i;

    while(*strDest) {
        *strDest++;
    } //legamos hasta el final de strDest
    for(i=0; i<count; i++) {
        *strDest++=*strSource++;
        if (*strSource=='\0') {
            break;
        }
    }
    *strDest='\0';
    return res;
}
//compara dos cadenas devuelve  0 si son iguales
//                             >0 string1>string2
//                             <0 string1<string2
int mistrcmp(const char *string1,const char *string2) {
    while(*string1==*string2 && *string1) {
        string1++;
        string2++;
    }
    return *string1-*string2;
}
//compara los count primeros caracteres de string1 y string2
//Devuelve lo mismo que la anterior
int mistrncmp(const char *string1,const char *string2,size_t count) {
    unsigned int i;

    for(i=0; i<count && *string1==*string2 && *string1; i++) {
        string1++;
        string2++;
    }
    if (i>=count) {
        return 0;
    } else {
       return *string1-*string2;
    }
}
//devuelve un puntero a la primera aparición de c dentro de string
char *mistrchr(const char *string,int c) {

    while(*string!=c && *string) {
        string++;
    }
    if (*string==c) {
        return (char *)string;
    } else {
        return NULL;
    }
}
//devuelve un puntero a la última aparición de c dentro de string
char *mistrrchr(const char *string,int c) {
    char *pf=(char *)string;

    while(*pf) {
        pf++;
    }//llegamos al final de string
    while(pf>=string && *pf!=c) {
        pf--;
    }
    if (pf<string) {
        return NULL;
    } else {
        return pf;
    }
}
/*
La función memcpy copia count bytes de src to dest. Si el origen y el destino
de los datos se solapan, esta función no asegura que los bytes originales en la
región de solapamiento sean copiados antes de ser sobreescritos. Es mejor usar
memmove para que no se dé este problema.
*/
void *mimemcpy(void *dest,const void *src,size_t count) {
    char *cdest=(char *)dest, *csrc=(char *)src;

    for(;count>0; count--) {
        *cdest=*csrc;
        cdest++;
        csrc++;
    }
    return dest;
}

/*
La función memmove copia count bytes desde src hasta dest. Si alguna región 
de src y dest se solapa, memmove asegura que los bytes originales de la región
donde hay solapamiento son copiados antes de ser sobreescritos
*/
void *mimemmove(void *dest,const void *src, size_t count) {
    char *cdest=(char *)dest, *csrc=(char *)src;
    char *buffer=NULL;
    unsigned int i;

    if (dest!=src) { // si la fuente y el destino son el mismo sitio no hacemos nada
        if (csrc<cdest && cdest<csrc+count) { //hay solapamiento
            csrc+=(count-1);
            cdest+=(count-1);
            for(i=0; i<count; i++) {
                *cdest=*csrc;
                cdest--;
                csrc--;
            }
        } else {
            memcpy(cdest,csrc,count);
        }
    }
    return dest;
}

/*
La función memcmp compara los primeros count bytes de buf1 y
buf2 y devuelve un valor indicando su relación
*/
int mimemcmp(const void *buf1,const void *buf2,size_t count) {
    unsigned int i;
    char *cbuf1=(char *)buf1, *cbuf2=(char *)buf2;

    for(i=0; i<count && *cbuf1==*cbuf2 ; i++) {
        cbuf1++;
        cbuf2++;
    }
    return *cbuf1-*cbuf2;
}

/*
La función memchr busca la primera aparación de c en los 
primeros count bytes de buf. Para cuando encuentra a c o
cuando se ha buscado dentro de los primeros count bytes.
*/
void *mimemchr(const void *buf,int c,size_t count) {
    unsigned int i;
    char *cbuf=(char *)buf;

    for(i=0; i<count && *cbuf!=c; i++,cbuf++);
    if (*cbuf==c) {
        return cbuf;
    } else {
        return NULL;
    }
}
/* 
La función memset inicializa los primeros count bytes de dest
con el carácter c
*/
void *mimemset(void *dest,int c,size_t count) {
    unsigned int i;
    char *cdest=(char *)dest;

    for(i=0; i<count; i++) {
        *cdest=c;
        cdest++;
    }
    return dest;
}
