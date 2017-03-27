#ifndef __ARBOLCAD__
    #undef tipoBasico
    #define tipoBasico char *

    typedef struct nodo_arbol_cad {
        tipoBasico info;
        struct nodo_arbol_cad* hi;
        struct nodo_arbol_cad* hd;
    } NODO_ARBOL_CAD;

    typedef NODO_ARBOL_CAD * ARBOLCAD;

    void _arbolCadInicia(ARBOLCAD *pa);
    #define arbolCadInicia(p) _arbolCadInicia(&(p))
    int arbolCadVacio(ARBOLCAD a);
    void _arbolCadAnula(ARBOLCAD *pa);
    #define arbolCadAnula(a) _arbolCadAnula(&(a))
    ARBOLCAD arbolCadHi(ARBOLCAD a);
    ARBOLCAD arbolCadHd(ARBOLCAD a);
    tipoBasico arbolCadContenido(ARBOLCAD a);
    ARBOLCAD arbolCadLocaliza(tipoBasico x, ARBOLCAD a);
    void _arbolCadPonInfo(tipoBasico x, ARBOLCAD *pa);
    #define arbolCadPonInfo(x,a) _arbolCadPonInfo(x,&(a))
    void arbolCadPonHi(ARBOLCAD hi, ARBOLCAD a);
    void arbolCadPonHd(ARBOLCAD hd, ARBOLCAD a);
    void arbolCadPonInfoHi(tipoBasico x, ARBOLCAD a);
    void arbolCadPonInfoHd(tipoBasico x, ARBOLCAD a);
    int arbolCadComparaInfo(tipoBasico x, tipoBasico y);
    #define __ARBOLCAD__
#endif
