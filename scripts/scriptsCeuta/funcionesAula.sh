macsCiclo1() {
[ "$1" != "" ] && (macsCiclo1 | grep -i $1) && return 0
echo "172.124.116.101    f4:6d:04:54:30:8d    ASUSTek COMPUTER INC.
172.124.116.102    f4:6d:04:54:30:95    ASUSTek COMPUTER INC.
172.124.116.103    f4:6d:04:54:30:e3    ASUSTek COMPUTER INC.
172.124.116.104    f4:6d:04:54:31:b7    ASUSTek COMPUTER INC.
172.124.116.105    f4:6d:04:54:30:d6    ASUSTek COMPUTER INC.
172.124.116.106    f4:6d:04:54:30:ec    ASUSTek COMPUTER INC.
172.124.116.107    f4:6d:04:54:30:d2    ASUSTek COMPUTER INC.
172.124.116.108    f4:6d:04:54:30:e0    ASUSTek COMPUTER INC.
172.124.116.109    f4:6d:04:54:30:e5    ASUSTek COMPUTER INC.
172.124.116.110    f4:6d:04:54:30:d3    ASUSTek COMPUTER INC.
172.124.116.111    f4:6d:04:54:31:1c    ASUSTek COMPUTER INC.
172.124.116.112    f4:6d:04:54:30:f0    ASUSTek COMPUTER INC.
172.124.116.113    f4:6d:04:54:30:db    ASUSTek COMPUTER INC.
172.124.116.114    f4:6d:04:54:31:c1    ASUSTek COMPUTER INC.
172.124.116.115    f4:6d:04:54:2d:99    ASUSTek COMPUTER INC.
172.124.116.116    f4:6d:04:54:2d:93    ASUSTek COMPUTER INC.
172.124.116.117    f4:6d:04:52:b3:3e    ASUSTek COMPUTER INC.
172.124.116.118    f4:6d:04:4f:be:79    ASUSTek COMPUTER INC.
172.124.116.119    f4:6d:04:54:31:1a    ASUSTek COMPUTER INC.
172.124.116.120    f4:6d:04:54:30:c4    ASUSTek COMPUTER INC.
172.124.116.121    f4:6d:04:54:30:fc    ASUSTek COMPUTER INC.
172.124.116.122    f4:6d:04:54:30:91    ASUSTek COMPUTER INC.
172.124.116.123    f4:6d:04:54:30:d5    ASUSTek COMPUTER INC.
172.124.116.124    f4:6d:04:54:30:f2    ASUSTek COMPUTER INC.
172.124.116.125    f4:6d:04:54:30:dc    ASUSTek COMPUTER INC.
172.124.116.126    f4:6d:04:54:30:cf    ASUSTek COMPUTER INC.
172.124.116.127    48:5b:39:f7:5f:33    ASUSTek COMPUTER INC.
172.124.116.128    f4:6d:04:52:b5:a5    ASUSTek COMPUTER INC.
172.124.116.129    f4:6d:04:54:31:44    ASUSTek COMPUTER INC.
172.124.116.130    f4:6d:04:54:30:8c    ASUSTek COMPUTER INC."
}

#macsCiclo2() {
#echo "172.124.117.19    1c:1b:0d:65:88:9e    GIGA-BYTE TECHNOLOGY CO.,LTD."
#}
macsCiclo2() {
[ "$1" != "" ] && (macsCiclo2 | grep -i $1) && return 0
echo "172.124.117.101	1c:1b:0d:0d:2d:85	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.102	1c:1b:0d:64:fc:c7	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.103	1c:1b:0d:64:fc:bf	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.104	1c:1b:0d:0c:68:d8	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.105	1c:1b:0d:0c:68:d9	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.106	1c:1b:0d:64:fc:b9	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.107	1c:1b:0d:65:88:61	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.108	1c:1b:0d:65:88:3b	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.109	1c:1b:0d:64:fc:bd	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.110	1c:1b:0d:65:88:39	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.111	1c:1b:0d:0c:69:24	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.112	1c:1b:0d:0d:3f:cc	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.113	1c:1b:0d:64:fc:e3	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.114	1c:1b:0d:65:88:5b	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.115	1c:1b:0d:0d:2d:87	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.116	1c:1b:0d:65:88:46	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.117	1c:1b:0d:65:88:8e	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.118	1c:1b:0d:65:88:a0	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.119	1c:1b:0d:ec:1f:a5	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.120	1c:1b:0d:0d:3f:ba	GIGA-BYTE TECHNOLOGY CO.,LTD.
172.124.117.121	1c:1b:0d:65:88:9b	GIGA-BYTE TECHNOLOGY CO.,LTD."
}

clavesCiclo1() {
[ "$1" != "" ] && (clavesCiclo1 | grep -i $1) && return 0
RED=172.124.116
echo "${RED}.101    27410    98402
${RED}.102    83441    10091
${RED}.103    58268    20746
${RED}.104    46121    70992
${RED}.105    39279    10052
${RED}.106    61961    74003
${RED}.107    22010    88131
${RED}.108    92295    73537
${RED}.109    29018    72420
${RED}.110    50516    83036
${RED}.111    49110    56939
${RED}.112    65820    60221
${RED}.113    83110    98342
${RED}.114    70133    85028
${RED}.115    19820    47516
${RED}.116    28331    35914
${RED}.117    12634    72023
${RED}.118    28912    76224
${RED}.119    59128    61098
${RED}.120    83011    45558
${RED}.121    67022    98430
${RED}.122    51223    87255
${RED}.123    16226    30623
${RED}.124    21717    72440
${RED}.125    81058    29010
${RED}.126    39284    45103
${RED}.127    91637    14002
${RED}.128    61923    30116
${RED}.129    11701    58117
${RED}.130    20730    48182"
}

clavesCiclo2() {
[ "$1" != "" ] && (clavesCiclo2 | grep -i $1) && return 0
RED=172.124.117
echo "${RED}.101    2145    8124
${RED}.102    3132    9856
${RED}.103    8714    1901
${RED}.104    3718    7323
${RED}.105    8137    2419
${RED}.106    1816    8412
${RED}.107    2434    1091
${RED}.108    5158    1750
${RED}.109    3978    4621
${RED}.110    2173    3724
${RED}.111    7913    5832
${RED}.112    7129    9427
${RED}.113    3721    2425
${RED}.114    3191    1696
${RED}.115    3115    4820
${RED}.116    8456    7462
${RED}.117    2245    8543
${RED}.118    1287    3578
${RED}.119    4856    1469
${RED}.120    1145    8523
${RED}.121    3687    9731"
}


_clavesCiclo1() {
RED=172.124.116
echo "${RED}.101    29583    74602
${RED}.102    59394    68201
${RED}.103    73309    62798
${RED}.104    87903    44301
${RED}.105    47689    26073
${RED}.106    39005    61242
${RED}.107    53509    28862
${RED}.108    16101    21573
${RED}.109    14589    37702
${RED}.110    64063    29503
${RED}.111    22410    83997
${RED}.112    27410    95036
${RED}.113    68440    33905
${RED}.114    57033    24795
${RED}.115    65991    39813
${RED}.116    82511    64274
${RED}.117    59003    28101
${RED}.118    63704    50909
${RED}.119    37411    83904
${RED}.120    25441    40309
${RED}.121    54369    84030
${RED}.122    62584    43799
${RED}.123    84030    70499
${RED}.124    70708    94309
${RED}.125    64459    37084
${RED}.126    84905    62848
${RED}.127    52905    38773
${RED}.128    27074    64208
${RED}.129    65670    48709
${RED}.130    69098    57043"
}
aluciclo1() {
[ "$1" != "" ] && (aluciclo1 | grep -i $1) && return 0
echo "Mustafa Abdelatif Ahmed
Carlos Bermejo del Valle
Rául Botella Chaves
José Manuel Chenard Gaona
Marina Contreras Oliva
Abderrahman Damoun Laarbi
Abdeslam El Kadouri Ahmed
Yonathan  Gómez Bouselham 
Jorge Gutiérrez Moreno
Rafael Hernández Cano
Fernando Luzón Orduña
Carlos Maldonado Navas
Sául Lecabel Martón Bermúdez
Yarell Jesús Martón Bermúdez
Alí Mekki Abdelkader
Inmaculada Menjíbar Jíménez
Brajím Mohamed Fadel
Omar Yannati Mohamed Mohamed
Naufel Mohamed Mojtar
Mohamed Mustafa Mohamed
David Osuna Villena 
Juan Manuel Pérez Cárdenas
José Manuel Pérez del Río
José Miguel Pérez Espinosa
Jesús Rivas Vázquez
Santiago Romero Miranda
Jesús Ruiz Rodríguez
Juan Manuel Serrano Alcaide
Iván Sierra Mérida"
}
aluciclo2() {
[ "$1" != "" ] && (aluciclo2 | grep -i $1) && return 0
echo "Yasser Abdeselam Ferrer
Abdunásar Ahmed Mohamed
Alejandro Alba Barrientos	
Mohamed (Lango) Ali Abdelkader
Raúl Baro Guerrero	
Brayan José Barrero Pérez
Javier González Raya
Adrián Hermosilla Ruiz
Jesús Hesles Morilla
Omar Kanyaa Lahasen
Sergio Lara Vega
Cynthia Mohamed Maese
Rául Ríos Enrique
Lucía Sorroche de la Rubia"
}

