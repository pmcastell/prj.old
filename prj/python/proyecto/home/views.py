#!/usr/bin/python
# -*- coding: utf-8 -*-
from django.conf import settings
from django.http import HttpResponseRedirect
from django.shortcuts import render_to_response, get_object_or_404
from django.template import RequestContext
from models import Pagina
	
def index(request):
	return render_to_response('home/index.html',
		context_instance=RequestContext(request))
		
def ver_paginas(request):
    paginas = Pagina.objects.all()
    return render_to_response('home/ver_paginas.html'),
        {'paginas': paginas},
        context_instance=RequestContext(request))
        
def ver_pagina(request, pagina_id):
    pagina = get_object_or_404(Pagina, id=pagina_id)
    return render_to_response('home/ver_pagina.html'),
        {'pagina': pagina},
        context_intance=RequestContext(request))
        
                		
