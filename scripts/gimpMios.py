#!/usb/bin/python
# -*- coding: utf-8 -*-


def setVisible(vsb=True,lista=None,nImg=0):
   if (lista==None):
      lista=range(len(gimp.image_list()[nImg].layers))
   img=gimp.image_list()[nImg]
   activa=pdb.gimp_image_get_active_layer(img)
   for i in lista:
      l=img.layers[i]
      l.visible=vsb
   pdb.gimp_image_set_active_layer(img,activa)


def setLinked(lnk=True,lista=None,nImg=0):
   if (lista==None):
      lista=range(len(gimp.image_list()[nImg].layers))
   img=gimp.image_list()[nImg]
   activa=pdb.gimp_image_get_active_layer(img)
   for i in lista:
      l=img.layers[i]
      l.linked=lnk
   pdb.gimp_image_set_active_layer(img,activa)
   
def cortar(lista=None,nImg=0):
   if (lista==None):
      lista=range(len(gimp.image_list()[nImg].layers))
   img=gimp.image_list()[nImg]
   activa=pdb.gimp_image_get_active_layer(img)
   for i in lista:
      l=img.layers[i]
      pdb.gimp_image_set_active_layer(img,l)
      pdb.gimp_edit_cut(pdb.gimp_image_active_drawable(img))
   pdb.gimp_image_set_active_layer(img,activa)
      
      
      
####cortar selección en todas las capas
###img=gimp.image_list()[0]
###for l in img.layers:
###   pdb.gimp_image_set_active_layer(img,l)
###   pdb.gimp_edit_cut(pdb.gimp_image_active_drawable(img))
   

####Ocultar todas las capas   
###img=gimp.image_list()[0]
###for l in img.layers:
###   l.visible=False
   
####Ocultar todas las capas   
###img=gimp.image_list()[0]
###for l in img.layers:
###   l.visible=True
      
