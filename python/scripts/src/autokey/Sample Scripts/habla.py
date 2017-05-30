# -*- coding: utf-8 -*-
import os, gtk
#try:
#    # Python2
#    import Tkinter as tk
#except ImportError:
#    # Python3
#    import tkinter as tk

def habla(voz="voice_JuntaDeAndalucia_es_pf_diphone",mensaje = None):
    if (mensaje==None):
       mensaje=clipboard.get_selection()
       ##root = tk.Tk()
       # keep the window from showing
       ##root.withdraw()
       # read the clipboard
       ##mensaje = root.clipboard_get_selection()
    #for c in ["“","—"]:
    #   mensaje.replace(c,"")
    mensaje=mensaje.encode("iso-8859-1")
    mensaje=mensaje.replace("'","\\\'")
    mensadef=""
    for c in mensaje:
       if not c in ["“","—"]:
          mensadef+=c
    comando="echo $'("+voz+") (SayText \""+mensadef+"\")' | festival --pipe"
    print comando
    os.system(comando)

#habla(mensaje="Logans")

    
#import pygtk
#pygtk.require('2.0')
#import gtk

# get the clipboard
#clipboard = gtk.clipboard_get()

# read the clipboard text data. you can also read image and
# rich text clipboard data with the
# wait_for_image and wait_for_rich_text methods.
#text = clipboard.wait_for_text()
