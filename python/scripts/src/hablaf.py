# -*- coding: utf-8 -*-

import os

mensaje="\"windows\""
mensaje=mensaje.encode("iso-8859-1")
voices=["cmu_us_rms_arctic_clunits", "cmu_us_clb_arctic_clunits", "cmu_us_slt_arctic_clunits", 
"cmu_us_bdl_arctic_clunits"]
v=3
voice="voice_"+voices[v]
comando="echo '("+voice+") (SayText \""+mensaje+"\")' | festival --pipe"
print os.system(comando)

#hablaingles(clipboard.get_selection(),0)    
#hablaingles("windows",0)