#include <stdio.h>

int main(int argc, char**argv) {
   char c;
   while((c=getchar())!=EOF) {
      if (c!=13) {
         printf("%c",c);
      }
   }
}

