#!/bin/bash
function espacios() {
   CAD=$1; LEN=$2
   RES=""
   NESP=$(( $LEN - ${#CAD} ))
   #echo "MAX_LEN: $LEN | NESP: $NESP | LON_CAD: ${#CAD}"
   for((i=0;i<$NESP;i++)); do
      RES="$RES "
   done
   if [ "$3" = "R" ]; then
      RES="${CAD}${RES}"
   else
      RES="${RES}${CAD}"
   fi
   echo "$RES"
   #echo "|$RES|" > /dev/stderr
}   

LISTA=$(wget -O - https://www.iplocation.net/ 2>/dev/null | grep -A1 -E '([0-9]{1,3}\.){3}[0-9]{1,3}' | grep -E '<tr>|</tr>')
echo "--------------------------------------------------------------------------------------------------"
echo "| $(espacios "IP" 15 R) | $(espacios "PAIS" 20 R) | $(espacios "REGION" 25 R) | $(espacios "CIUDAD" 25 R) |"
echo "--------------------------------------------------------------------------------------------------"
echo "$LISTA" | while read TR; do
   read TR2
   TR="${TR}${TR2}"
   IP=$(echo "$TR" | awk -F'</td>' '{print $1}' | awk -F'<td>' '{print $2;}')
   PAIS=$(echo "$TR" | awk -F'</td>' '{print $2}' | awk -F'<td>' '{print $2;}' | awk -F'<img' '{print $1;}')
   REGION=$(echo "$TR" | awk -F'</td>' '{print $3}' | awk -F'<td>' '{print $2;}')
   CIUDAD=$(echo "$TR" | awk -F'</td>' '{print $4}' | awk -F'<td>' '{print $2;}')
   echo "| $(espacios "$IP" 15) | $(espacios "$PAIS" 20 R) | $(espacios "$REGION" 25 R) | $(espacios "$CIUDAD" 25 R) |"
done   
echo "--------------------------------------------------------------------------------------------------"
exit 0
   
echo "
<tr><td>88.206.169.13</td><td>Sweden <img src='/assets/images/flags/se.gif'></td><td>Jonkopings Lan</td><td>Vetlanda</td></tr>
<tr><td>88.206.169.13</td><td>Sweden <img src='/assets/images/flags/se.gif'></td><td>Jönköping</td><td>Vetlanda</td></tr>
<tr><td>88.206.169.13</td><td>Sweden <img src='/assets/images/flags/se.gif'></td><td>Kronobergs Lan</td><td>Växjö</td></tr>
<tr><td>88.206.169.13</td><td>Sweden <img src='/assets/images/flags/se.gif'></td><td>Jönköping</td><td>Sävsjö</td></tr>
<tr><td>88.206.169.13</td><td>Sweden <img src='/assets/images/flags/se.gif'></td><td>08</td><td>Vetlanda</td></tr>

"


