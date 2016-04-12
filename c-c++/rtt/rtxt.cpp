/*
 * rtxt.cpp
 *
 *  Created on: 21/01/2012
 *      Author: usuario
 */
#include <iostream>
using namespace std;
#include "miString.hpp"

int main()
{
	miString a((char *)"hola"),b((char *)" pepe");
	miString c;

	c=a+b;

	std::cout << "Welcome to the wonderful world of C++!!!\n";
	std::cout << "c vale: " << c.toString() << "\n";
	std::cout << "c[1,3]=" << c.substr(1,3).toString() << "\n";

	return 0;

}



