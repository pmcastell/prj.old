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
			
		
	
		
#def connect





# EDIT HERE #

user="adminprofes"
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







#try:
	#print "GOLEM LOGIN..."
	#print c.login(u,"Golem",u)
#except Exception as e:
#	print e

try:
   #print "GOLEM USER_LIST..."
   #print c.get_student_list(u,"Golem")
   #print c.get_student_passwords(u,"Golem")
   print "--------------------------------------------------------------"
   if (len(sys.argv)<2):
      print "Faltan argumentos"
      print "Uso: "+sys.argv[0]+" -b <usuario> | -c <usuario> <nueva-password>"
      print "-b busca al usuario e imprime información sobre el mismo si lo encuenntra"
      print "-c cambia la contraseña del <usuario> a <nueva-password>"
      sys.exit()
   if (sys.argv[1] == "-c"):
      if (len(sys.argv)<3):
         print "Faltan argumentos"
         sys.exit()
      userid=sys.argv[2]; userpass=sys.argv[3]
      if (c.change_student_password(u,"Golem",userid,userpass)):
         print "Password de "+userid+" cambiada a: "+userpass
      else:
         print "Error cambiando password."
   elif (sys.argv[1] == "-b"):
      allPasswords=c.get_all_passwords(u,"Golem")
      user=sys.argv[2]
      encontrado=False
      for i in range(len(allPasswords)):
         regActual=allPasswords[i]
         for key in regActual:
            #print "clave lower: "+regActual[key].lower()
            #print "user: "+user.lower()
            if (regActual[key].lower().find(user.lower())>=0):
               print regActual
               encontrado=True
               break
      if (not encontrado):
         print "No se ha encontrado ningún registro que contenga: "+user
         #if (allPasswords[i]["uid"] == user):
         #   print allPasswords[i]
         #   break
       
       #for key in allPasswords[i]:
              #if (allPasswords[i][key].lower().index(user.lower())>=0):
              
       #  if (allPasswords[i].lower().index(user.lower())>0):
       #     print allPasswords[i]
   	##print c.get_user_list(u,"Golem","gwasar")
       ##print 
   else:
      print "falta argumento."
   print "--------------------------------------------------------------"

except Exception as e:
	print e
	
#try:
#	print "LDAP MANUAL CONNECTION..."
	
#	connect()
	
	
#except Exception as e:
#	print e

