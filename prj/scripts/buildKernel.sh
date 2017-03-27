#-j4 levanta 4 procesos de compilación en paralelo para mejorar el tiempo de compilación
#se recomiendo hacer make buildworld antes de make buildkernel
make buildworld KERNCONF=FRANC_GENERIC
make buildkernel KERNCONF=FRANC_GENERIC
