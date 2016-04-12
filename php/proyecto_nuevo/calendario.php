<?php
						$nombremes=array(1=>"ENERO", 2=>"FEBRERO", 3=>"MARZO", 4=>"ABRIL",
										 5=>"MAYO", 6=>"JUNIO", 7=>"JULIO", 8=>"AGOSTO",
										 9=>"SEPTIEMBRE", 10=>"OCTUBRE", 11=>"NOVIEMBRE",
										 12=>"DICIEMBRE");
										 
					//------------------------------------------------------------------
					// Funci󮠱ue calcula el n򭥲o de d de un mes
					//------------------------------------------------------------------
					function numerodiasmes($mes,$anno)
					{
						//comprobaci󮠡񯠢isiesto
						$bisiesto=date("L"); //devuelve 1 si el a񯠥s bisiesto, 0 en otro caso
						switch($mes)
						{
							case 2:
								if ($bisiesto)
									$nd=29;
								else
									$nd=28;
								break;
							case 4: case 6: case 9: case 11:
								$nd=30;
								break;
							default:
								$nd=31;
						}
						return $nd;
					}					 

					//------------------------------------------------------------------
					// Funci󮠱ue genera el calendario de un mes concreto
					//------------------------------------------------------------------
					function calendariomes($mes,$anno)
					{
						global $nombremes;

						//comprobaci󮠤e mes actual
						$mes_hoy = date("m");
						$anno_hoy = date("Y");
						if (($mes_hoy <> $mes) || ($anno_hoy <> $anno))
							$hoy = 0;
						else
							$hoy = date("d");
							
						//c⭣ulo del mes anterior y el mes siguiente
						$mes_anterior = $mes - 1;
						$anno_anterior = $anno;
						if ($mes_anterior == 0)
						{
							$anno_anterior--;
							$mes_anterior=12;
						}
						
						$mes_siguiente = $mes + 1;
						$anno_siguiente = $anno;
						if ($mes_siguiente == 13)
						{
							$anno_siguiente++;
							$mes_siguiente=1;
						}
						
						//c⭣ulo del dde inicio del mes (0=dom...6=s⢩
						$diasemana=date("w",mktime(0,0,0,$mes,1,$anno));
						//los domingos se tratar⮠como 򬴩mo dde la semana
						if ($diasemana==0)
							$diasemana=7;
						//c⭣ulo del n򭥲o de d del mes actual
						$nd = numerodiasmes($mes,$anno);
						//generaci󮠤el calendario
						echo "<TABLE CELLSPACING=3 CELLPADDING=2 BORDER=0>
								<TR BGCOLOR=#083253>
									<TD><A HREF=proyecciones.php?mes=$mes_anterior&anno=$anno_anterior>
											<FONT COLOR=white>&lt;&lt;</A></TD>
									<TD COLSPAN=5 ALIGN=center>
										<FONT COLOR=white><FONT FACE='Comic Sans MS'>$nombremes[$mes] $anno</FONT></TD>
									<TD><A HREF=proyecciones.php?mes=$mes_siguiente&anno=$anno_siguiente>
										<FONT COLOR=white>&gt;&gt;</A></TD>
								</TR>";
						echo "<TR ALIGN=center BGCOLOR=#3399FF>
								<TD WIDTH=14%><FONT FACE='Comic Sans MS'>Lu</FONT></TD><TD WIDTH=14%><FONT FACE='Comic Sans MS'>Ma</FONT></TD>
								<TD WIDTH=14%><FONT FACE='Comic Sans MS'>Mi</FONT></TD><TD WIDTH=14%><FONT FACE='Comic Sans MS'>Ju</FONT></TD>
								<TD WIDTH=14%><FONT FACE='Comic Sans MS'>Vi</FONT></TD><TD WIDTH=14%><FONT FACE='Comic Sans MS'>Sa</FONT></TD>
								<TD WIDTH=14%><FONT FACE='Comic Sans MS'>Do</FONT></TD></TR>";
								
						echo "<TR>";
						$aux=1;
						//genera celdas en blanco hasta llegar al comienzo del mes
						while($aux<$diasemana)
						{
							echo "<TD>&nbsp;</TD>";
							$aux++;
						}
						for ($i=1;$i<=$nd;$i++)
						{
							$enlace = "<A HREF = cartelera.php?dia=$i&mes=$mes&anno=$anno>
									   <FONT SIZE=+1> $i </A>";
							if ($i==$hoy)
								echo "<TD BGCOLOR=red>$enlace</TD>";
							elseif (($diasemana==6) or ($diasemana==7))
								echo "<TD BGCOLOR=#99CCFF>$enlace</TD>";
							else
								echo "<TD>$enlace</TD>";
							
							$diasemana++;
							
							if ($diasemana==8)
							{
								//comienza nueva semana
								echo "</TR>";
								echo "<TR>";
								$diasemana=1;
							}
						}
						echo "</TR></TABLE>";
					}

					//--------------------------------------------------------
					// Programa principal con llamada a la funci󮍊					//--------------------------------------------------------
					if (!isset($mes, $anno))
					{
						//datos del mes actual
						$mes=date("n");
						$anno=date("Y");
					}
					calendariomes($mes,$anno);
					?>