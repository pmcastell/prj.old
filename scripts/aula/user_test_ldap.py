#!/usr/bin/env python

import xmlrpclib
import ldap
import os.path

# EDIT HERE #

user="adminprofes"
password="nuci_admin"

# ================= #

u=(user,password)




c=xmlrpclib.ServerProxy("https://localhost:9779")





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












try:
	print "GOLEM LOGIN..."
	print c.login(u,"Golem",u)
except Exception as e:
	print e

try:
	print "GOLEM USER_LIST..."
	print c.get_student_list(u,"Golem")
except Exception as e:
	print e
	
try:
	print "LDAP MANUAL CONNECTION..."
	
	connect()
	
	
except Exception as e:
	print e
