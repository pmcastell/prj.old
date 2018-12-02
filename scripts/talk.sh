#!/bin/bash

#(echo '(voice_cmu_us_bdl_arctic_clunits)' ; xsel) | festival --tts  --pipe
#voices=("voice_cmu_us_rms_arctic_clunits" "voice_cmu_us_clb_arctic_clunits" "voice_cmu_us_slt_arctic_clunits" "voice_cmu_us_bdl_arctic_clunits" "voice_JuntaDeAndalucia_es_pa_diphone" "voice_JuntaDeAndalucia_es_sf_diphone")
#voices=("cmu_us_awb_arctic" "cmu_us_bdl_arctic" "cmu_us_clb_arctic" "cmu_us_jmk_arctic" "cmu_us_ksp_arctic" "cmu_us_rms_arctic" "cmu_us_slt_arctic")
voices=($(ls /usr/share/festival/voices/* | grep -v ':' | egrep -v '^$'))


[ "$1" != "" ] && DEFAULT="$1" || DEFAULT="2"
(echo "(voice_${voices[$DEFAULT]})" ; echo "(SayText \"$(xsel)\")" )| festival --pipe
