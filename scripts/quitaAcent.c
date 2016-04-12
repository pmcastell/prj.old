#include <stdio.h>

int main(int argc, char** argv) {
   char c;
   while((c=getchar())!=EOF) {
      if (c=='á') {
         printf("a");
      } else if (c=='é') {
         printf("e");
      } else if (c=='í') {
         printf("i");
      } else if (c=='ó') {
         printf("o");
      } else if (c=='ú') {
         printf("u");
      } else {
         printf("%c",c);
      }
   }
}
