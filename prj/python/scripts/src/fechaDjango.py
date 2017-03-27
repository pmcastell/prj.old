'''
Created on 05/06/2012

@author: usuario
'''
#from django.http import HttpResponse
import datetime

def index(req):
    ahora = datetime.datetime.now()
    html = u"<html><body> D&iacute;a y hora actual : %s. </body> </html>" % ahora
    return html
