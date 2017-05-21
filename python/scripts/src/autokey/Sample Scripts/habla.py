# -*- coding: utf-8 -*-
import os, gtk
#try:
#    # Python2
#    import Tkinter as tk
#except ImportError:
#    # Python3
#    import tkinter as tk

def habla(voz="voice_JuntaDeAndalucia_es_pa_diphone",mensaje = None):
    if (mensaje==None):
       mensaje=clipboard.get_selection()
       ##root = tk.Tk()
       # keep the window from showing
       ##root.withdraw()
       # read the clipboard
       ##mensaje = root.clipboard_get_selection()
    #for c in ["“","—"]:
    #   mensaje.replace(c,"")
    mensadef=""
    for c in mensaje:
       if not c in ["“","—"]:
          mensadef+=c
    mensaje=mensadef.encode("iso-8859-1")
    comando="echo '("+voz+") (SayText \""+mensaje+"\")' | festival --pipe"
    os.system(comando)
    
#import pygtk
#pygtk.require('2.0')
#import gtk

# get the clipboard
#clipboard = gtk.clipboard_get()

# read the clipboard text data. you can also read image and
# rich text clipboard data with the
# wait_for_image and wait_for_rich_text methods.
#text = clipboard.wait_for_text()