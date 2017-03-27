#include <string>
#include <string.h>
#include <stdio.h>
#define TAM_DEFECTO 256

#include "miString.h"

miString::miString() {
   length=0; size=TAM_DEFECTO; //tamaño por defecto
   s=new char[size];
   *s='\0';
};
miString::miString(long tam) {
   length=0; size=tam;
   s=new char[size];
   *s=0;
}
miString::miString(char *p) {
   length=strlen(p);
   if (length<TAM_DEFECTO) {
      size=TAM_DEFECTO;
   } else {
      size=length+1;
   }
   char *b=s=new char[size];
   while(*b++=*p++); //copiar cadenas
}
miString::miString(miString& str) {
   length=str.length;
   size=str.size;
   s=new char[size];
   char *p=str.s; char *b=s;
   while(*b++=*p++);
}
miString::miString(char ch) {
   length=0;size=TAM_DEFECTO;
   s=new char[size];
   *s=ch;
   s[1]=0;
}
miString::~miString() {
  delete s;
 }
bool miString::operator==(miString& str) {
   if (length!=str.length) {
      return false;
   }
   int i=0;
   if (ignorecase) {
      for(;i<length;i++) {
         if (toupper(s[i])!=toupper(str.s[i])) {
            return false;
         }
      }
   } else {
      for(;i<length;i++) {
         if (s[i]!=str.s[i]) {
            return false;
         }
      }
   }
   return true;
}
bool miString::operator!=(miString& str) {
   return !operator==(str);
}
bool miString::operator<(miString& str) {
   int i=0;
   if (ignorecase) {
      for(;i<length && toupper(s[i])==toupper(str.s[i]);i++);
      if (i<length) {
         return toupper(s[i])<toupper(str.s[i]);
      }
   } else {
      for(;i<length && s[i]==str.s[i];i++);
      if (i<length) {
         return s[i]<str.s[i];
      }
   }
   return true;
}
bool miString::operator >(miString& str) {
   return !(operator<(str) || operator==(str));
}
bool miString::operator <=(miString& str) {
   return operator<(str) || operator==(str);
}
bool miString::operator >=(miString& str) {
   return !operator<(str);
}
miString&  miString::operator=(miString& str) {
   length=str.length;
   size=str.size;delete s;s=new char[size];
   char *o=str.s;char *d=s;
   while(*d++=*o++); // copiar cadenas
   return new miString(this);
}
miString& miString::operator+(miString str) {
   miString *r=new miString(length+str.length+1);
   int i=0;
   for(;i<length;i++) {
      r->s[i]=s[i];
   }
   for(;i<=str.length;i++) {
      r->s[i]=str.s[i];
   }
   r->s[i]=0;
   return *r;
}
miString& miString::operator+=(miString str) {
   int i;
   if (size<length+str.length+1) {
      char *p=new char[length+str.length+1];
      for(i=0;i<length;i++) {
         p[i]=s[i];
      }
      delete s;
      s=p;
   }
   for(i=0;i<str.length;i++) {
      s[length+i]=str.s[i];
   }
   s[length+i]=0;
   return *this;
}
char miString::operator[](int i) {
   return s[i];
}
miString& miString::substr(int i) {
   miString *r;
   if (i<length) {
      r=new miString(length-i+1);
      for(;i<=length;i++) {
         r->s[i]=s[i];
      }
      r->length=length-i+1;
   } else {
      r=new miString();
   }
   return *r;
}
miString& miString::substr(int i,int j) {
   miString *r=new miString();
   return *r;
}

