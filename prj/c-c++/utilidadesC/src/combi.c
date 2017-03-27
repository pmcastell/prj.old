#include <stdio.h>
int MAX=4;
int NUM_CHARS=4;

int finComb(int pos[],int p) {
   int i;
   
   for(i=0;i<=p;i++) {
      if (pos[i]>=NUM_CHARS) {
         return 1;
      }
   }
   return 0;
}
int incrementa(int pos[],int i) {
   int j;
   int llevo=1;

   for(j=i;j>=0 && llevo;j--) {
      pos[j]+=llevo;
      llevo=0;
      if (pos[j]>=NUM_CHARS) {
         pos[j]=0;
         llevo=1;
      }
   }
   return llevo;
}
            
int main(int argc,char**argv) {
   int i,j,fin;
   int pos[]={0,0,0,0,0,0,0,0,0,0};
   char *conjunto="ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";

   if (argc>1) {
	   NUM_CHARS=atoi(argv[1]);
   }
   if (argc>2) {
	   MAX=atoi(argv[2]);
   }
   if (argc>3) {
	   conjunto=argv[3];
	   if (NUM_CHARS>strlen(conjunto)) {
		   NUM_CHARS=strlen(conjunto);
	   }
   }
   
   for(i=0;i<MAX;i++) {
	  fin=0;
      while(!fin) {
         for(j=0;j<=i;j++) {
            printf("%c",conjunto[pos[j]]);
         }
         fin=incrementa(pos,i);
         printf("\n");
      }
   }
}   
         
         
      
