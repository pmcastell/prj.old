/*
 * miString.h
 *
 *  Created on: 21/01/2012
 *      Author: usuario
 */

#ifndef MISTRING_H_
#define MISTRING_H_
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



#endif /* MISTRING_H_ */
