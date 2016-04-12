#ifndef __miString_H__
#define __miString_H__

class miString {
    private:
       char *s;
       long length;
       long size;
       static const bool ignorecase=false;

    public:
       miString();
       miString(long tam);
       miString(char *p);
       miString(miString& str);
       miString(char ch);
       ~miString();
       bool operator==(miString& str);
       bool operator!=(miString& str);
       bool operator<(miString& str);
       bool operator >(miString& str);
       bool operator <=(miString& str);
       bool operator >=(miString& str);
       miString& operator=(miString& str);
       miString& operator+(miString str);
       miString& operator+=(miString str);
       char operator[](int i);
       miString& substr(int i);
       miString& substr(int i,int j);
};
#endif
