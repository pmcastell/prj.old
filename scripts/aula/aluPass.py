#!/usr/bin/env python
# -*- coding: utf-8 -*-

import xmlrpclib
import ldap
import os.path
import sys






def llxvar(var):
	
	return c.get_variable("","VariablesManager",var)
	
def connect():
		
	
	url=None
	remote=False
	try:
		url="ldap://"+llxvar("MASTER_SERVER_IP")
		
		if url!=None:
			
			remote=True
	except Exception as e:
		pass
		
	do_it=True
		
	if not remote:
		
		try:
			url=llxvar("CLIENT_LDAP_URI")
			count=1
		except Exception as e:
			print(e,"")
			return None
				
	else:
		count=2
		
		
	for x in range(0,count):
		print url	
		try:
			LDAP_SECRET1="/etc/lliurex-cap-secrets/ldap-master/ldap"
			LDAP_SECRET2="/etc/lliurex-secrets/passgen/ldap.secret"
			
			mldap=ldap.initialize(url)
			mldap.set_option(ldap.VERSION,ldap.VERSION3)
				
			if os.path.exists(LDAP_SECRET1):
				f=open(LDAP_SECRET1)
				lines=f.readlines()
				f.close()
				password=lines[0].replace("\n","")
			else:
				if os.path.exists(LDAP_SECRET2):
					f=open(LDAP_SECRET2)
					lines=f.readlines()
					f.close()
					password=lines[0].replace("\n","")
				
			try:
				path="cn=admin,"+llxvar("LDAP_BASE_DN")
			except:
				return None
			
			print "Binding with path: " + path

			mldap.bind_s(path,password)
				
			print("OK")
			return True
				
				
		except Exception as l_ex:
			print l_ex
			try:
				url=llxvar("CLIENT_LDAP_URI")
			except Exception as e:
				print(e,"")
				return None
			
def uso():
   print "Faltan argumentos"
   print "Uso: "+sys.argv[0]+" -b <usuario> | -c <usuario> <nueva-password> | -l | -R <fichero-usuarios>"
   print "-b busca al usuario e imprime información sobre el mismo si lo encuenntra"
   print "-c cambia la contraseña del <usuario> a <nueva-password>"
   print "-l lista todos los usuarios y sus contraseñas (sólo los alumnos)"
   print "-R cambia todas las contraseñas a los usuarios especificadas en <fichero-usuarios>"
   sys.exit(2)
		
def toString(reg):
   res=""
   #for k in reg:
      #res+=str(k).encode("utf-8")+": "+str(reg[k]).encode("utf-8")+" | "
      #res+=str(k).encode("utf-8")+": "+str(reg[k])+" | "
      #res+=k.encode("utf-8")+": "+reg[k].encode("utf-8")+" | "
   res="uid: "+reg['uid']+" | passwd: "+reg['passwd']+" | nombre(cn+sn): "+reg['cn']+" "+reg['sn']  
   return res.encode("utf-8")

def buscarUsuario(user=None):
	if (user==None and len(sys.argv)>2):
		user=sys.argv[2]
	allPasswords=c.get_all_passwords(u,"Golem")
	encontrado=False
	for i in range(len(allPasswords)):
		regActual=allPasswords[i]
		for key in regActual:
			if (regActual[key].lower().find(user.lower())>=0):
				print toString(regActual)
				encontrado=True
				
	if (not encontrado): print "No se ha encontrado ningún registro que contenga: "+user

def _cambiaPassAlu(userid,userpass):
	if (c.change_student_password(u,"Golem",userid,userpass)):
		print "Password de "+userid+" cambiada a: "+userpass
	else:
		print "Error cambiando password de ",userid,"a",userpass

	 	
def cambiaPassAlu():
	if (len(sys.argv)<4):
		uso()
	userid=sys.argv[2]; userpass=sys.argv[3]
	_cambiaPassAlu(userid, userpass)

def listarPassAlumnos():
	allPasswords=c.get_all_passwords(u,"Golem")
	for i in range(len(allPasswords)):
		alu=allPasswords[i]
		if (alu.keys()[0] == 'uid'):
			print alu['uid'].encode("utf-8"),"|",alu['passwd'].encode("utf-8")		
         	 	
def restaurarPassAlumnos():
	if (len(sys.argv)<3):
		uso()
	try:
		fich=sys.argv[2]
	except:
		print "error abriendo fichero",fich
		sys.exit(3)
	f=open(fich,"r")
	for l in f:
		user=l.split("|")
		if (len(user)>=2):
			userid=user[0].strip()
			userpass=user[1].strip()
			_cambiaPassAlu(userid, userpass)
			
	
         	 	
#def connect


# EDIT HERE #

user="netadmin"
password="nuci_admin"
# ================= #
u=(user,password)
host="https://localhost"
for i in range(0,len(sys.argv)):
   if (sys.argv[i] == "-h"):
      host="https://"+sys.argv[i+1]
      del sys.argv[i]; del sys.argv[i]
      break
host+=":9779"      

c=xmlrpclib.ServerProxy(host)

try:
   #print "GOLEM USER_LIST..."
   #print c.get_student_list(u,"Golem")
   #print c.get_student_passwords(u,"Golem")
   print "------------------------------------------------------------------------------"
   if (len(sys.argv)<2):
      uso()
   if (sys.argv[1] == "-c"):
      cambiaPassAlu()
   elif (sys.argv[1] == "-b"):
      	 buscarUsuario()
   elif (sys.argv[1] == "-l"):
   	listarPassAlumnos()
   elif (sys.argv[1] == "-R"):
   	restaurarPassAlumnos()
   else:
      uso()
   print "------------------------------------------------------------------------------"

except Exception as e:
	print e
	
#try:
#	print "LDAP MANUAL CONNECTION..."
	
#	connect()
	
	
#except Exception as e:
#	print e


#if (allPasswords[i]["uid"] == user):
         #   print allPasswords[i]
         #   break
       
       #for key in allPasswords[i]:
              #if (allPasswords[i][key].lower().index(user.lower())>=0):
              
       #  if (allPasswords[i].lower().index(user.lower())>0):
       #     print allPasswords[i]
   	##print c.get_user_list(u,"Golem","gwasar")
       ##print 








#try:
	#print "GOLEM LOGIN..."
	#print c.login(u,"Golem",u)
#except Exception as e:
#	print e
