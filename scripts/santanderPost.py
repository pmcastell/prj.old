POST={}
POST['isInNewPages']='1'
POST['dse_nextEventName']='up'
POST['dse_applicationName']='Seguridad'
POST['dse_sessionId']='m6zXCC_JhOottoZgkOg9fcb'
POST['dse_operationName']='OP_LOGON_MULTI'
POST['dse_threadId']='defaultExecutionThreadIdentifier'
POST['dse_pageId']='0'
POST['dse_processorState']='AuthenticationPage'
POST['dse_processorId']='AC1615575057AD1924D93C36'
POST['dse_cmd']='continue'
POST['riskResultParam']=''
POST['variableFieldSelector']='tipoDocumento'
POST['tipoDocumento']='N'
POST['numeroDocumento']='25126190R'
POST['password']='19371975'


#https://particulares.gruposantander.es/SUPFPA_ENS/BtoChannelDriver.ssobto?dse_contextRoot=true
print '[',
for k in POST:
   print "'"+k+"',",
print
print '[',
for k in POST:   
   print "'"+POST[k]+"',",
