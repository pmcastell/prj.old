#include <string.h>
#include <stdio.h>
#define TAM_DEFECTO 256

class string {
    private:
       char *s;
       long length;
       long size;
       static const bool ignorecase=false;
       
    public:
       string() {
          length=0; size=TAM_DEFECTO; //tamaño por defecto
          s=new char[size];
          *s='\0';
       }
       string(long tam) {
          length=0; size=tam;
          s=new char[size];
          *s=0;
       }
       string(char *p) {
          length=strlen(p);
          if (length<TAM_DEFECTO) {
             size=TAM_DEFECTO;
          } else {
             size=length+1;
          }
          char *b=s=new char[size];
          while(*b++=*p++); //copiar cadenas
       }
       string(string& str) {
          length=str.length;
          size=str.size;
          s=new char[size];
          char *p=str.s; char *b=s;
          while(*b++=*s++);
       }
       string(char ch) {
          length=0;size=TAM_DEFECTO;
          s=new char[size];
          *s=ch;
          s[1]=0;
       }
       bool operator==(string& str) {
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
       bool operator!=(string& str) {
          return !operator==(str);
       }
       bool operator<(string& str) {
          int i=0;
          if (ignorecase) {
             for(;i<length && toupper(s[i])==toupper(str.s[i]);i++) {
                if (i>=length) {
                   return false;
                } else {
                   return toupper(s[i]<toupper(str.s[i]);
                }
             }
          } else {
             for(;i<length;i++) {
                if (s[i]!=str.s[i]) {
                   return false;
                } else {
                   return s[i]<str.s[i];
                }
             }
          }
          return true;
       }
       bool operator >(string& str) {
          return !(operator==(str) || operator<(str));
       }
       bool operator <=(string& str) {
          return operator<(str) || operator==(str);
       }
       bool operator >=(string& str) {
          return !operator<(str);
       }
       string&  operator=(string& str) {
          length=str.length;
          size=str.size;delete s;s=new char[size];
          char *o=str.s;char *d=s;
          while(*d++=*o++); // copiar cadenas
       }
       string& operator+(string str) {
          string *r=new string(length+str.length+1);
          int i=0;
          for(;i<length;i++) {
             r->s[i]=s[i];
          }
          for(;i<=str.length;i++) {
             r->s[i]=str.s[i];
          }
          return *r;
       }
       string& operator+=(string str) {
          if (size<length+str.length+1) {
             char *p=new char[length+str.length+1];
             for(int i=0;i<length;i++) {
                p[i]=s[i];
             }
             delete s;
             s=p;
          }
          for(int i=0;i<str.length;i++) {
             s[length+i]=str.s[i];
          }
          return *this;
       }
       char operator[](int i) {
          return s[i];
       }
       string& substr(int i) {
          string *r;
          if (i<length) {
             r=new string(length-i+1);
             for(;i<=length;i++) {
                r->s[i]=s[i];
             }
             r->length=length-i+1;
          } else {
             r=new string();
          }
          return *r;
       }
       string& substr(int i,int j) {
          string *r=new string();
          return *r;
       }
}
;
