NKERNELS=$(dpkg -l | grep -i linux-headers | awk '{print $2;}' | grep -v generic | awk -F'-' '{print $3"-"$4;}' | sort | uniq | wc -l)
NKERNELS=$(($NKERNELS - 2))
KERNELS=$(dpkg -l | grep -i linux-headers | awk '{print $2;}' | grep -v generic | awk -F'-' '{print $3"-"$4;}' | sort | uniq | head -$NKERNELS)
if [ $NKERNELS -lt 1 ]; then echo no hay nada que borrar; exit 1; fi
for k in $KERNELS; do 
   sudo apt-get -y --purge remove linux-headers-$k linux-headers-$k-generic linux-image-$k-generic linux-image-extra-$k-generic; 
done

