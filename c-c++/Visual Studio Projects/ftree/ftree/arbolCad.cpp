#include <string.h>
#include "arbolCad.h"

#define ARBOL ARBOLCAD
#define NODO NODO_ARBOL_CAD

#define _arbolInicia        _arbolCadInicia
#define arbolVacio          arbolCadVacio
#define _arbolAnula         _arbolCadAnula  
#define arbolHi             arbolCadHi
#define arbolHd             arbolCadHd
#define arbolContenido      arbolCadContenido
#define arbolLocaliza       arbolCadLocaliza
#define _arbolPonInfo       _arbolCadPonInfo
#define arbolPonHi          arbolCadPonHi
#define arbolPonHd          arbolCadPonHd
#define arbolPonInfoHi      arbolCadPonInfoHi
#define arbolPonInfoHd      arbolCadPonInfoHd
#define tipoBasicoCompara   arbolCadComparaInfo


#include "arbol.cpp"
int arbolCadComparaInfo(char *s1, char *s2) {
    return strcmp(s1,s2);
}
