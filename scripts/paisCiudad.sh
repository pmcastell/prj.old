#!/bin/bash
function espacios() {
   CAD=$1; LEN=$2
   RES=""
   NESP=$(( $LEN - ${#CAD} ))
   #echo "MAX_LEN: $LEN | NESP: $NESP | LON_CAD: ${#CAD}"
   if [ "$3" = "C" ]; then INC=2; else INC=1; fi
   for((i=0;i<$NESP;i+=$INC)); do
      RES="$RES "
   done
   if [ "$3" = "R" ]; then
      RES="${CAD}${RES}"
   elif [ "$3" = "C" ]; then
      if [ "$(( $NESP % 2 ))" == 1 ]; then RES="${RES:1}${CAD}${RES}"; else RES="${RES}${CAD}${RES}"; fi
   else
      RES="${RES}${CAD}"
   fi
   echo "$RES"
   #echo "|$RES|" > /dev/stderr
}   
function repetir() {
   CHAR=$1; N=$2;
   CAD=""
   for((i=1;$i<=$N;i++)); do
      CAD="$CAD$CHAR";
   done
   echo $CAD
}   
function separador() {
   repetir - 100
}      
#LISTA=$(wget --no-check-certificate -O - https://www.iplocation.net/ 2>/dev/null | grep -aA1 -E '([0-9]{1,3}\.){3}[0-9]{1,3}' | grep -aE '<tr>|</tr>')
LISTA=$(curl https://www.iplocation.net/ 2>/dev/null | grep -aA1 -E '([0-9]{1,3}\.){3}[0-9]{1,3}' | grep -aE '<tr>|</tr>')
separador
echo "| $(espacios IP 15 C) | $(espacios PAIS 20 C) | $(espacios REGION 26 C) | $(espacios CIUDAD 26 C) |"
separador
echo "$LISTA" | while read TR; do
   read TR2
   TR="${TR}${TR2}"
   IP=$(echo "$TR" | awk -F'</td>' '{print $1}' | awk -F'<td>' '{print $2;}')
   PAIS=$(echo "$TR" | awk -F'</td>' '{print $2}' | awk -F'<td>' '{print $2;}' | awk -F'<img' '{print $1;}')
   REGION=$(echo "$TR" | awk -F'</td>' '{print $3}' | awk -F'<td>' '{print $2;}')
   CIUDAD=$(echo "$TR" | awk -F'</td>' '{print $4}' | awk -F'<td>' '{print $2;}')
   echo "| $(espacios "$IP" 15) | $(espacios "$PAIS" 20 R) | $(espacios "$REGION" 26 R) | $(espacios "$CIUDAD" 26 R) |"
done   
separador
exit 0
   
echo "
<tr><td>88.206.169.13</td><td>Sweden <img src='/assets/images/flags/se.gif'></td><td>Jonkopings Lan</td><td>Vetlanda</td></tr>
<tr><td>88.206.169.13</td><td>Sweden <img src='/assets/images/flags/se.gif'></td><td>Jönköping</td><td>Vetlanda</td></tr>
<tr><td>88.206.169.13</td><td>Sweden <img src='/assets/images/flags/se.gif'></td><td>Kronobergs Lan</td><td>Växjö</td></tr>
<tr><td>88.206.169.13</td><td>Sweden <img src='/assets/images/flags/se.gif'></td><td>Jönköping</td><td>Sävsjö</td></tr>
<tr><td>88.206.169.13</td><td>Sweden <img src='/assets/images/flags/se.gif'></td><td>08</td><td>Vetlanda</td></tr>

"


