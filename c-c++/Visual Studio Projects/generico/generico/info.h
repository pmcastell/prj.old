#ifndef __INFO__
#define __INFO__

typedef struct {
   int num;
   char nombre[11];
} INFO;

void infoLee(INFO *info);
void infoEscribir(INFO info);
char * infoToString(INFO info);
int infoCompara(INFO info1, INFO info2);


#endif
