#!/usr/bin/python
# -*- coding: utf-8 -*-
from django.db import models

class Pagina (models.Model):
    titulo = models.CharField( 'Título' , max_length =128)
    contenido = models.TextField()
    
    feccre = models.DateTimeField('Fecha creación' , auto_now_add = True )
    fecmod = models.DateTimeField('Fecha últ modificación' , auto_now = True )
    
    def get_absolute_url(self):
        return "/paginas/%s/" % self.id
    
    def __unicode__(self):
        return self.titulo
    class Meta:
        ordering = ['titulo']
        verbose_name = u"página"
        verbose_name_plural = u"páginas"


