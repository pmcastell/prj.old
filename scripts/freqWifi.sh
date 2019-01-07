#!/bin/bash
main() {
    [ "$1" = "" ] && freqs && exit 0
    [ ${#1} -le 2 ] && freqs | grep "^$1" || freqs | grep "${1}$"
#    freqs | grep "$1 "
#    echo $(( 2407 + $((5 * $1)) ))
}
freqs() {
echo "1 2412
2 2417
3 2422
4 2427
5 2432
6 2437
7 2442
8 2447
9 2452
10 2457
11 2462
12 2467
13 2472"
}
main "$@"
exit 0

