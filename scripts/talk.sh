#!/bin/bash

#(echo '(voice_cmu_us_bdl_arctic_clunits)' ; xsel) | festival --tts  --pipe
voices=("voice_cmu_us_rms_arctic_clunits" "voice_cmu_us_clb_arctic_clunits" "voice_cmu_us_slt_arctic_clunits" "voice_cmu_us_bdl_arctic_clunits" "voice_JuntaDeAndalucia_es_pa_diphone" "voice_JuntaDeAndalucia_es_sf_diphone")
[ "$1" != "" ] && DEFAULT="$1" || DEFAULT="2"
(echo "(${voices[$DEFAULT]})" ; echo "(SayText \"$(xsel)\")" )| festival --pipe
