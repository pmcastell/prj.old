
LliureX trusty

    LoginPreferencesHelp/GuideAbout Trac 

    WikiTimelineRoadmapBrowse SourceView TicketsSearch 

    ← Previous RevisionLatest RevisionNext Revision →BlameRevision Log 

source:
n4d-server-plugins
/
trunk
/
fuentes
/
server-install-files
/
usr
/
share
/
n4d
/
python-plugins
/
Golem.py
@
4896

View diff against:
View revision:
Last change on this file since 4896 was 4896, checked in by hectorgh, 13 months ago

removing chowns from startup and from ldap watch
File size: 25.5 KB
Line	 
1	# -*- coding: utf-8 -*-
2	import imp
3	import ldap
4	import sys
5	import subprocess
6	import grp
7	import shutil
8	import threading
9	import magic
10	import pyinotify
11	import time
12	from pyinotify import WatchManager, Notifier, ThreadedNotifier, EventsCodes, ProcessEvent
13	
14	
15	class Golem:
16	
17	
18	        PLUGIN_PATH="/usr/share/n4d/python-plugins/"
19	        LDAP_LOG="/var/lib/ldap/"
20	       
21	       
22	        def startup(self,options):
23	               
24	               
25	                try:
26	                        self.mime=magic.open(magic.MAGIC_MIME)
27	                        self.mime.load()
28	                        self.obj=imp.load_source("LdapManager",Golem.PLUGIN_PATH + "LdapManager.py")
29	                        obj3=imp.load_source("NetFilesManager",Golem.PLUGIN_PATH + "NetFilesManager.py")
30	                        obj4=imp.load_source("PasswordManager",Golem.PLUGIN_PATH + "PasswordManager.py")
31	                        obj5=imp.load_source("GesItaManager",Golem.PLUGIN_PATH + "GesItaManager.py")
32	                        obj6=imp.load_source("FileOperations",Golem.PLUGIN_PATH + "FileOperations.py")
33	                        obj7=imp.load_source("PeterPan",Golem.PLUGIN_PATH + "PeterPan.py")
34	                        self.ldap=self.obj.LdapManager(llxvars)
35	                        self.netfiles=obj3.NetFilesManager(llxvars)
36	                        self.pw=obj4.PasswordManager()
37	                        self.itaca=obj5.GesItaManager(llxvars,self,'llxgesc.xml')
38	                        self.file_operations=obj6.FileOperations()
39	                        self.peter_pan=obj7.PeterPan()
40	                        self.try_count=0
41	                        self.sharefunctions = {}
42	                       
43	                        self.start_inotify()
44	                       
45	                        if objects["VariablesManager"].get_variable("MASTER_SERVER_IP")!=None:
46	                                p=subprocess.Popen(["gluster volume info"],shell=True,stdout=subprocess.PIPE,stderr=subprocess.PIPE).communicate()[1]
47	                                if 'No volumes present' in p:
48	                                        #Light version. Does not chown existing net files
49	                                        self.regenerate_net_files(1)
50	                       
51	                except Exception as e:
52	                        print e
53	               
54	        #def __init__
55	       
56	
57	        def start_inotify(self):
58	
59	                t=threading.Thread(target=self._inotify)
60	                t.daemon=True
61	                t.start()
62	
63	        #def start_inotify
64	               
65	        def _inotify(self):
66	               
67	                wm=WatchManager()
68	                mask=pyinotify.ALL_EVENTS
69	                       
70	                class Process_handler(ProcessEvent):
71	                               
72	                        def __init__(self,main):
73	                               
74	                                self.main=main
75	                                self.count=0
76	                                self.in_modify=False
77	                               
78	                        def process_IN_MODIFY(self,event):
79	                                if not self.in_modify:
80	                                        self.in_modify=True
81	                                        time.sleep(2)
82	                                        # light version. Does not chown existing net files
83	                                        self.main.regenerate_net_files(1)
84	                                        time.sleep(2)
85	                                        self.in_modify=False
86	
87	
88	       
89	                notifier=Notifier(wm,Process_handler(self))
90	                wdd=wm.add_watch(Golem.LDAP_LOG,mask,rec=True)
91	                       
92	                while True:
93	                        try:
94	                                       
95	                                notifier.process_events()
96	                                if notifier.check_events():
97	                                        notifier.read_events()
98	                               
99	                        except Exception as e:
100	                                print e
101	                                notifier.stop()
102	                                       
103	                return False
104	       
105	        #def _inotify
106	       
107	       
108	       
109	        def _restore_groups_folders(self):
110	               
111	                t=threading.Thread(target=self.restore_groups_folders)
112	                t.daemon=True
113	                t.start()
114	               
115	        #def
116	
117	        def add_user(self,plantille,properties,generic_mode=False):
118	               
119	               
120	                generated_user=None
121	                properties["uid"]=properties["uid"].encode("utf8")
122	                properties["cn"]=properties["cn"].encode("utf8")
123	                properties["sn"]=properties["sn"].encode("utf8")
124	               
125	               
126	                if properties.has_key("userPassword"):
127	                        properties["userPassword"]=properties["userPassword"].encode("utf8")
128	               
129	                if type(generic_mode)==type(True):
130	                        #es un booleano
131	                        generated_user=self.ldap.add_user(generic_mode,plantille,properties)
132	                else:
133	                        generated_user=self.ldap.add_user(False,plantille,properties)
134	               
135	                if type(generated_user) is dict:
136	                       
137	                        homepath = self.netfiles.exist_home_or_create(generated_user)
138	                        if plantille=="Teachers" or plantille=="Others":
139	                                self.pw.add_password(generated_user["uid"],generated_user["cn"],generated_user["sn"],generated_user["userPassword"])
140	                        properties["group_type"]=plantille
141	                        self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/golem','add_user',properties)
142	                        self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/openmeetings','add_user',[properties])
143	                        return "true: " + generated_user["uid"]
144	                else:
145	                        return generated_user
146	               
147	       
148	        #def add_user
149	       
150	
151	        def add_generic_users(self,plantille,group_type,number,generic_name,pwd_generation_type,pwd=None):
152	               
153	                generated_list=self.ldap.add_generic_users(plantille,group_type,number,generic_name,pwd_generation_type,pwd)
154	                for item in generated_list:
155	                        #
156	                        # Item {uid:name,userPassword:password}
157	                        #
158	                       
159	                        homepath = self.netfiles.exist_home_or_create(item)
160	                       
161	                        #print "password saving..."
162	                        if plantille=="Teachers" or plantille=="Others":
163	                                self.pw.add_password(item["uid"],item["cn"],item["sn"],item["userPassword"])
164	                        self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/golem',('add_generic_users'),{'group':group_type,'user':item})
165	
166	                        properties = {}
167	                        properties['group_type'] = plantille
168	                        properties['uid'] = item['uid']
169	                        properties['cn'] = item['uid']
170	                        properties['sn'] = item['uid']
171	                        self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/openmeetings','add_user',[properties])
172	                return generated_list
173	               
174	        #def add_generic_users
175	       
176	        def add_admin(self,user_name):
177	                # existing system user
178	       
179	                try:
180	                        uid=pwd.getpwnam(user_name).pw_uid
181	                       
182	                        properties={}
183	                        properties["uid"]=user_name
184	                        properties["cn"]=user_name
185	                        properties["sn"]=user_name
186	                        properties["userPassword"]=uid
187	                        properties["uidNumber"]=os.environ["SUDO_UID"]
188	                       
189	                        self.ldap.add_user(False,"Admin",properties)
190	                        return True
191	                       
192	                except Exception as e:
193	                       
194	                        return [False,e.message]
195	                       
196	                       
197	       
198	        #def add_admin
199	       
200	        def login(self,user_info):
201	               
202	                uid,password=user_info
203	               
204	                dic={}
205	                p = subprocess.Popen(["groups",uid],stdout = subprocess.PIPE,stderr = subprocess.PIPE)
206	                output = p.communicate()[0]
207	                output=output.replace("\n","")
208	               
209	                dic["groups"]=output
210	               
211	                students="ou=Students,ou=People," + llxvars("LDAP_BASE_DN")
212	                teachers="ou=Teachers,ou=People," + llxvars("LDAP_BASE_DN")
213	                admins="ou=Admins,ou=People," + llxvars("LDAP_BASE_DN")
214	               
215	                group_type="None"
216	               
217	                if output.find("students")!=-1:
218	                        dic["path"]="uid=" + uid + "," + students
219	                        group_type="students"
220	                       
221	                if output.find("teachers")!=-1:
222	                        dic["path"]="uid=" + uid + "," + teachers
223	                        group_type="teachers"
224	
225	                if output.find("admins")!=-1 and output.find("teachers")!=-1:
226	                        dic["path"]="uid=" + uid + "," + teachers
227	                        group_type="promoted-teacher"
228	               
229	                if output.find("adm")!=-1:
230	                        dic["path"]="uid=" + uid + "," + admins
231	                        group_type="admin"
232	                        #return "true " + group_type
233	
234	                if "NTicketsManager" in objects:
235	                        if objects["NTicketsManager"].validate_user(uid,password):
236	                                return "true " + group_type
237	                       
238	                if validate_user(uid,password)[0]:
239	                        return "true "+ group_type
240	                else:
241	                        return "false"
242	
243	               
244	        #def login
245	       
246	       
247	       
248	        def change_own_password(self,user_info,new_password):
249	               
250	                uid,password=user_info
251	                dic={}
252	                p = subprocess.Popen(["groups",uid],stdout = subprocess.PIPE,stderr = subprocess.PIPE)
253	                output = p.communicate()[0]
254	                output=output.replace("\n","")
255	               
256	                dic["groups"]=output
257	               
258	                students="ou=Students,ou=People," + llxvars("LDAP_BASE_DN")
259	                teachers="ou=Teachers,ou=People," + llxvars("LDAP_BASE_DN")
260	                admin="ou=Admins,ou=People," + llxvars("LDAP_BASE_DN")
261	                others="ou=Other,ou=People," + llxvars("LDAP_BASE_DN")
262	               
263	                if output.find("students")!=-1:
264	                        path="uid=" + uid + "," + students
265	                elif output.find("teachers")!=-1:
266	                        path="uid=" + uid + "," + teachers
267	                elif output.find("others")!=-1:
268	                        path="uid=" + uid + "," + others
269	                elif output.find("admin")!=-1:
270	                        path="uid=" + uid + "," + admin
271	                else:
272	                        return "false"
273	               
274	                dic["path"]=path
275	               
276	                #dic["llxvars"]=llxvars
277	               
278	                try:
279	                       
280	                        tmp_ldap=ldap.initialize(llxvars("CLIENT_LDAP_URI"))
281	                        dic["a"]="initialize"
282	                        tmp_ldap.set_option(ldap.VERSION,ldap.VERSION3)
283	                        dic["b"]="set_option"
284	                        tmp_ldap.bind_s(path,password)
285	                        dic["c"]="bind"
286	                        self.ldap.change_password(path,new_password)
287	                        dic["d"]="ldap password"
288	                       
289	                        if "Teachers" in path:
290	                                self.pw.set_externally_modified(uid)
291	                       
292	                        return "true"
293	                       
294	                except Exception as inst:
295	
296	                       
297	
298	                        dic["exception"]=inst
299	               
300	                        return "false"
301	               
302	        #def change_own_password
303	       
304	       
305	       
306	       
307	        def delete_student(self,uid,delete_data=True):
308	               
309	                user_info={}
310	                user_info["uid"]=uid
311	                user_info["profile"]="students"
312	       
313	                #self.unfreeze_user(uid)
314	       
315	       
316	                if delete_data==True:
317	                        homepath = self.netfiles.delete_home(user_info)
318	
319	                os.system("rm -rf /home/%s"%uid)
320	
321	                ret=self.ldap.delete_student(uid)
322	               
323	                self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/golem','delete_student')
324	                properties = {}
325	                properties['uid'] = uid
326	                properties['group_type'] = 'Students'
327	                self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/openmeetings','delete_user',[properties])
328	                return ret
329	               
330	        #def delete_student
331	               
332	               
333	               
334	        def delete_teacher(self,uid,delete_data=True):
335	
336	                user_info={}
337	                user_info["uid"]=uid
338	                user_info["profile"]="teachers"
339	
340	                #self.unfreeze_user(uid)               
341	
342	                if delete_data==True:
343	                        homepath = self.netfiles.delete_home(user_info)
344	
345	                os.system("rm -rf /home/%s"%uid)
346	               
347	                self.pw.remove_password(uid)
348	                ret=self.ldap.delete_teacher(uid)
349	               
350	                self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/golem','delete_teacher')
351	                properties = {}
352	                properties['uid'] = uid
353	                properties['group_type'] = 'Teachers'
354	                self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/openmeetings','delete_user',[properties])
355	
356	                return ret
357	        #def delete_teacher
358	
359	        def delete_other(self,uid,delete_data=True):
360	
361	                user_info={}
362	                user_info["uid"]=uid
363	                user_info["profile"]="others"
364	               
365	                self.unfreeze_user(uid)
366	               
367	               
368	                if delete_data==True:
369	                        homepath = self.netfiles.delete_home(user_info)
370	
371	               
372	                self.pw.remove_password(uid)
373	
374	                ret=self.ldap.delete_other(uid)
375	                self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/golem','delete_other')
376	                properties = {}
377	                properties['uid'] = uid
378	                properties['group_type'] = 'Others'
379	                self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/openmeetings','delete_user',[properties])
380	
381	                return ret
382	               
383	        #def delete_other
384	
385	        def delete_students(self,delete_data=True):
386	               
387	                list=self.ldap.search_user("*")
388	               
389	                ret_list=[]
390	               
391	                for item in list:
392	                        if item.properties["path"].find("ou=Students")!=-1:
393	                                ret=self.delete_student(item.properties["uid"],delete_data) 
394	                                ret_list.append(item.properties["uid"] +":"+ret)
395	                               
396	                return ret_list
397	                               
398	                       
399	               
400	        #def delete_students
401	       
402	        def delete_teachers(self,delete_data=True):
403	               
404	                list=self.ldap.search_user("*")
405	               
406	                ret_list=[]
407	               
408	                for item in list:
409	                        if item.properties["path"].find("ou=Teachers")!=-1:
410	                                ret=self.delete_teacher(item.properties["uid"],delete_data)
411	                                ret_list.append(item.properties["uid"] +":"+ret)
412	
413	                return ret_list
414	                       
415	               
416	        #def delete_students
417	       
418	
419	        def delete_all(self,delete_data=True):
420	               
421	                list=self.ldap.search_user("*")
422	               
423	                ok=True
424	               
425	                ret_list=[]
426	               
427	                for item in list:
428	                        if item.properties["path"].find("ou=Teachers")!=-1:
429	                                ret=self.delete_teacher(item.properties["uid"],delete_data)
430	                                ret_list.append(item.properties["uid"] +":"+ret)
431	                               
432	                        if item.properties["path"].find("ou=Students")!=-1:
433	                                ret=self.delete_student(item.properties["uid"],delete_data)
434	                                ret_list.append(item.properties["uid"] +":"+ret)
435	                               
436	                        if item.properties["path"].find("ou=Other")!=-1:
437	                                ret=self.delete_other(item.properties["uid"],delete_data)
438	                                ret_list.append(item.properties["uid"] +":"+ret)
439	                               
440	                return ret_list
441	               
442	        #def delete_students
443	
444	        def get_students_function_list(self):
445	                return students_func_list
446	
447	        def get_teachers_function_list(self):
448	                return teachers_func_list
449	               
450	        def get_admin_function_list(self):
451	                return admin_func_list
452	               
453	        def get_others_function_list(self):
454	                return others_func_list
455	
456	        def get_student_list(self):
457	               
458	                list=self.ldap.search_students("*")
459	               
460	                return_list=[]
461	               
462	                for item in list:
463	                        return_list.append(item.properties)
464	                                                       
465	                return return_list
466	               
467	        def get_teacher_list(self):
468	               
469	                list=self.ldap.search_teachers("*")
470	               
471	                return_list=[]
472	               
473	                for item in list:
474	                        return_list.append(item.properties)
475	                                                       
476	                return return_list
477	                       
478	        def get_user_list(self,filter):
479	
480	                list=self.ldap.search_user(filter)
481	
482	               
483	                #return self.ldap.light_search(filter)
484	               
485	                return_list=[]
486	                for item in list:
487	                        return_list.append(item.properties)
488	                       
489	                return return_list
490	               
491	
492	        #def get_user_list
493	       
494	        def light_get_user_list(self):
495	               
496	                list=self.ldap.light_search()
497	
498	                       
499	                return list
500	               
501	        #def light_get_user_list
502	       
503	        def get_available_groups(self):
504	               
505	                return self.ldap.get_available_groups()
506	               
507	        #def get_available_groups
508	       
509	
510	        def add_to_group(self,uid,group):
511	               
512	                result=self.ldap.add_to_group_type(group,uid)
513	
514	                user_info={}
515	                user_info["uid"]=uid
516	               
517	                path=self.ldap.get_dn(uid)
518	               
519	                if path.find("ou=Students")!=-1:
520	                        user_info["profile"]="students"
521	                if path.find("ou=Teachers")!=-1:
522	                        user_info["profile"]="teachers" 
523	                if path.find("ou=Other")!=-1:
524	                        user_info["profile"]="others"   
525	                       
526	               
527	
528	                self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/golem',('add_to_group'),{'group':{'cn':group},'user':user_info})
529	                #return must be "true" (string)
530	               
531	                return result
532	               
533	        #def add_to_group
534	       
535	        def remove_from_group(self,uid,group):
536	               
537	                result=self.ldap.del_user_from_group(uid,group)
538	                user_info={}
539	                user_info["uid"]=uid
540	               
541	               
542	               
543	                #return must be "true" (string)
544	
545	                self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/golem',('remove_from_group'),{'group':{'cn':group},'user':user_info})
546	                return result
547	               
548	        #def remove_from_group
549	
550	
551	        def change_student_personal_data(self,uid,name,surname):
552	                name=unicode(name).encode("utf8")
553	                surname=unicode(surname).encode("utf8")
554	                result=self.ldap.change_student_name(uid,name)
555	                #print result
556	                result2=self.ldap.change_student_surname(uid,surname)
557	                #print result2
558	                if result==result2 and result=="true":
559	                       
560	                        # TODO
561	                        # Execute hook to moodle
562	
563	
564	                        properties = {}
565	                        properties['group_type'] = 'Students'
566	                        properties['uid'] = uid
567	                        properties['cn'] = name
568	                        properties['sn'] = surname
569	                        self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/openmeetings','update_user',[properties])
570	
571	                        return result
572	                else:
573	                        return result + "," + result2
574	               
575	        #def change_personal_data
576	       
577	        def change_password(self,path,password,uid="",cn="",sn="",auto=False):
578	               
579	                password=unicode(password).encode("utf8")
580	                result=self.ldap.change_password(path,password)
581	               
582	                #trying to obtain user uid
583	                list=path.split(",")
584	                uid=list[0].split("=")[1]
585	               
586	               
587	                #return=="true"
588	               
589	                if uid!="" and cn!="" and sn!="":
590	                        self.pw.add_password(uid,cn,sn,password)
591	                       
592	                if not auto:
593	                        if "Teachers" in path:
594	                                self.pw.set_externally_modified(uid)
595	               
596	                return result
597	               
598	        #def change_student_password
599	
600	        def change_student_password(self,uid,password):
601	               
602	                result=self.ldap.change_user_password(uid,password)
603	               
604	                #return=="true"
605	               
606	               
607	                return result
608	               
609	        #def change_student_password
610	        def freeze_user(self,uid_list):
611	                self.ldap.freeze_user(uid_list)
612	                return 0
613	        #def freeze_user
614	
615	        def freeze_group(self,cn):
616	                self.ldap.freeze_group(cn)
617	                return 0
618	        #def freeze_group
619	
620	        def unfreeze_user(self,uid_list):
621	                self.ldap.unfreeze_user(uid_list)
622	                return 0
623	        #def unfreeze_user
624	
625	        def unfreeze_group(self,cn):
626	                self.ldap.unfreeze_group(cn)
627	                return 0
628	        #def unfreeze_group
629	       
630	        def add_teacher_to_admins(self,uid):
631	               
632	                result=self.ldap.add_teacher_to_admins(uid)
633	               
634	                return result
635	               
636	        #def add_teacher_to_admins
637	       
638	        def del_teacher_from_admins(self,uid):
639	               
640	                result=self.ldap.del_teacher_from_admins(uid)
641	                return result
642	               
643	        #def de_teacher_from_admins
644	       
645	       
646	        def change_group_description(self,gid,description):
647	               
648	                description=unicode(description).encode("utf8")
649	                result=self.ldap.change_group_description(gid,description)
650	               
651	                return result
652	               
653	        #def change_group_description
654	       
655	        def delete_group(self,group_name):
656	
657	                #self.unfreeze_group(gid)
658	                result=self.ldap.delete_group(group_name)
659	                try:
660	                        self.netfiles.remove_group_folder(group_name)
661	                except Exception as e:
662	                        print(e)
663	               
664	                self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/golem',('delete_group'),{'group':{'cn':group_name}})
665	                return result
666	               
667	        #def delete_group
668	       
669	       
670	        def add_group(self,properties):
671	                properties["description"]=unicode(properties["description"]).encode("utf8")
672	                result=self.ldap.add_group(properties)
673	               
674	                try:
675	                        self.create_group_folder(properties["cn"])
676	                except Exception as e:
677	                        print(e)
678	               
679	                try:
680	                        self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/golem',('add_group'),{'group':properties})
681	                except Exception as e:
682	                        print(e)
683	                return result
684	               
685	        #def add_group
686	       
687	        def get_students_passwords(self):
688	               
689	                list = self.ldap.get_students_passwords()
690	
691	                return self.quicksort(list)
692	               
693	        #def get_students_passwords
694	       
695	        def get_teachers_passwords(self):
696	               
697	                ret=self.light_get_user_list()
698	               
699	                tmp_teachers={}
700	
701	                for item in ret:
702	                       
703	                        if item[-1]=="teachers":
704	                                teacher={}
705	                                teacher["uid"]=item[1]
706	                                teacher["cn"]=item[3]
707	                                teacher["sn"]=item[4]
708	                                teacher["passwd"]=" #! UNKNOWN PASSWORD !# "   
709	                                tmp_teachers[item[1]]=teacher
710	                               
711	                               
712	                ret2=self.quicksort(self.pw.get_passwords())
713	
714	                final_ret=[]
715	
716	                for item in ret2:
717	                        if item["uid"] in tmp_teachers:
718	                                tmp_teachers[item["uid"]]["passwd"]=item["passwd"]
719	                       
720	
721	                for item in tmp_teachers:
722	                        final_ret.append(tmp_teachers[item])
723	                       
724	               
725	                return final_ret
726	               
727	        #def get_teachers_passwords
728	       
729	        def get_all_passwords(self,force_teachers=False):
730	                list=self.ldap.get_students_passwords()
731	                if not force_teachers:
732	                        list2=self.pw.get_passwords()
733	                else:
734	                        list2=self.ldap.get_teachers_passwords()
735	                for item in list2:
736	                        list.append(item)
737	                return self.quicksort(list)
738	               
739	        #def get_all_passwords
740	       
741	        def quicksort (self,lista): 
742	                self.sort_quicksort(lista,0,len(lista)-1) 
743	                return lista
744	       
745	        #def quicksort
746	       
747	        def sort_quicksort (self,lista,izdo,dcho) : 
748	                if izdo<dcho : 
749	                        pivote=lista[(izdo+dcho)/2] 
750	                        i,d=izdo,dcho
751	                        while i<=d : 
752	                                while lista[i]['sn'].lower()<pivote['sn'].lower() : i+=1 
753	                                while lista[d]['sn'].lower()>pivote['sn'].lower() : d-=1 
754	                                if i<=d : 
755	                                        lista[i],lista[d]=lista[d],lista[i] 
756	                                        i+=1 
757	                                        d-=1 
758	                        if izdo<d : self.sort_quicksort(lista,izdo,d) 
759	                        if i<dcho : self.sort_quicksort(lista,i,dcho) 
760	                return lista
761	        #def sort_quicksort
762	       
763	       
764	        def generic_student_to_itaca(self,uid,nia):
765	               
766	                return self.ldap.generic_student_to_itaca(uid,nia)
767	               
768	        #def generic_student_to_itaca
769	       
770	        def generic_teacher_to_itaca(self,uid,nif):
771	               
772	                return self.ldap.generic_teacher_to_itaca(uid,nif)
773	               
774	        #def generic_teachers_to_itaca
775	       
776	        def send_xml_to_server(self,data64,file_name,passwd=""):
777	                server_path="/tmp/" + file_name
778	                try:
779	                        ret=self.file_operations.send_file_to_server(data64,server_path)
780	                        if self.mime.file(server_path).split(";")[0]=="application/xml":
781	                                pass
782	                        elif self.mime.file(server_path).split(";")[0]=="application/octet-stream":
783	                                if passwd=="":
784	                                        return "false:xml_encrypted"
785	                                else:
786	                                        p=subprocess.Popen(["openssl","enc","-des","-d","-k",passwd,"-in",server_path,"-out",server_path+".xml"],stderr=subprocess.PIPE)
787	                                        output=p.communicate()[1]
788	                                        if output!=None:
789	                                                if "bad decrypt" in output:
790	                                                        return "false:xml_bad_password"
791	
792	                                        server_path=server_path+".xml"
793	                                        if self.mime.file(server_path).split(";")[0]=="application/xml":
794	                                                pass
795	                                        else:
796	                                                return "false:invalid_xml"
797	                                               
798	                       
799	                except Exception as e:
800	                        print e
801	                        return "false:send_error"
802	                if ret==1:
803	                        try:
804	                                ret=self.gescen_set_path(server_path)
805	                                if ret==True:
806	                                        return "true"
807	                                elif ret==False:
808	                                        return "false:xml_error"
809	                                elif ret=="false:xml_encrypted":
810	                                        return ret
811	                                else:
812	                                        return "false:unknown_error"
813	                        except:
814	                                return "false:xml_error"
815	                else:
816	                        return "false:send_error"
817	               
818	               
819	        #def send_xml_to_server
820	       
821	       
822	        def gescen_info(self):
823	                return self.itaca.get_info()
824	        #def gescen_info
825	       
826	        def gescen_set_path(self,path):
827	                return self.itaca.set_path(path)
828	        #def gescen_info
829	       
830	        def gescen_load(self):
831	                return self.itaca.load_file()
832	        #def gescen_info
833	
834	        def gescen_groups(self):
835	                return self.itaca.get_groups()
836	        #def gescen_group
837	
838	        def gescen_partial(self,group_list):
839	                #print "partial"
840	                #print group_list
841	                self.sharefunctions['generate_uid'] = generate_uid
842	                users_added = self.itaca.partial_import(group_list)
843	                self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/golem',('gescen_partial'),{})
844	                self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/openmeetings','add_user',users_added)
845	                return 'true'
846	
847	        #def gescen_partial
848	
849	        def gescen_full(self):
850	
851	                try:
852	                        self.sharefunctions['generate_uid'] = generate_uid
853	                except Exception as e:
854	                        print e
855	                        raise e
856	
857	                ret,users_added=self.itaca.full_import()
858	                self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/golem',('gescen_full'),{})
859	                self.peter_pan.execute_python_dir('/usr/share/n4d/hooks/openmeetings','add_user',users_added)
860	                return ret
861	        #def gescen_full
862	       
863	        def empty_students(self,generic=None):
864	                list=self.ldap.search_user("*")
865	               
866	                ret_list=[]
867	               
868	                for item in list:
869	                        if item.properties["path"].find("ou=Students")!=-1:
870	                                ret=self.empty_home(item.properties)
871	                                ret_list.append(item.properties["uid"] +":"+ret)
872	                return ret_list
873	        #def empty_students
874	       
875	        def empty_teachers(self,generic=None):
876	                list=self.ldap.search_user("*")
877	               
878	                ret_list=[]
879	               
880	                for item in list:
881	                        if item.properties["path"].find("ou=Teachers")!=-1:
882	                                ret=self.empty_home(item.properties)
883	                                ret_list.append(item.properties["uid"] +":"+ret)
884	                return ret_list
885	        #def empty_teachers
886	       
887	        def empty_others(self,generic=None):
888	                list=self.ldap.search_user("*")
889	               
890	                ret_list=[]
891	               
892	                for item in list:
893	                        if item.properties["path"].find("ou=Other")!=-1:
894	                                ret=self.empty_home(item.properties)
895	                                ret_list.append(item.properties["uid"] +":"+ret)
896	                return ret_list
897	        #def empty_others
898	       
899	        def empty_all(self):
900	               
901	                ret_list=[]
902	                ret_list.extend(self.empty_students(True))
903	                ret_list.extend(self.empty_teachers(True))
904	                ret_list.extend(self.empty_others(True))
905	                return ret_list
906	               
907	        #def empty_all
908	               
909	               
910	       
911	        def empty_home(self,user_info):
912	                try:
913	                        self.netfiles.delete_home(user_info)
914	                        self.netfiles.create_home(user_info)
915	                        return "true"
916	                except:
917	                        return "false"
918	        #def empty_home
919	
920	        def get_frozen_users(self):
921	                return self.ldap.get_frozen_users()
922	        #def get_frozen_users
923	
924	        def get_frozen_groups(self):
925	                return self.ldap.get_frozen_groups()
926	        #def get_frozen_groups
927	       
928	        def is_frozen_user(self,user):
929	                return self.ldap.is_frozen_user(user)
930	               
931	        def exist_home_or_create(self,user):
932	                return self.netfiles.exist_home_or_create(user)
933	               
934	
935	        def create_group_folder(self,group_name):
936	               
937	                return self.netfiles.create_group_folder(group_name)
938	               
939	               
940	        #def create_group_folder
941	       
942	        def restore_groups_folders(self):
943	                ret=[]
944	                try:
945	                       
946	                        for item in self.get_available_groups():
947	                                try:
948	                                        id=self.create_group_folder(item["cn"][0])
949	                                        ret.append((item["cn"][0],id))
950	                                except Exception as ex:
951	                                        ret.append((item["cn"][0],str(ex)))
952	                       
953	                except Exception as e:
954	                        ret.append(str(e))
955	                       
956	                return ret
957	               
958	        #def restore_group_folders
959	       
960	        def full_search(self):
961	               
962	                return self.ldap.full_search("*")
963	               
964	        #def full_search
965	       
966	       
967	        def export_llum_info(self):
968	               
969	                try:
970	               
971	                        user_list=self.get_user_list("*")
972	                        pwd_list=self.get_all_passwords(True)
973	                        groups=self.get_available_groups()
974	                       
975	                        exported_groups={}
976	                        exported_users={}
977	               
978	                        for item in groups:
979	                               
980	                                exported_groups[item["cn"][0]]={}
981	                                if item.has_key("memberUid"):
982	                                        exported_groups[item["cn"][0]]["members"]=item["memberUid"]
983	                                else:
984	                                        exported_groups[item["cn"][0]]["members"]=[]
985	                                exported_groups[item["cn"][0]]["description"]=item["description"][0]
986	                               
987	                        for item in user_list:
988	                               
989	                                if item["profile"]=="teachers":
990	                                        profile="Teachers"
991	                                elif item["profile"]=="students":
992	                                        profile="Students"
993	                                else:
994	                                        continue
995	                               
996	                                exported_users[item["uid"]]={}
997	                                exported_users[item["uid"]]["profile"]=profile
998	                                exported_users[item["uid"]]["cn"]=item["cn"]
999	                                exported_users[item["uid"]]["sn"]=item["sn"]
1000	                                exported_users[item["uid"]]["groups"]=item["groups"]
1001	                                exported_users[item["uid"]]["is_admin"]=item["is_admin"]
1002	                       
1003	
1004	                        for item in pwd_list:
1005	                                if item["uid"] in exported_users:
1006	                                        exported_users[item["uid"]]["userPassword"]=item["passwd"]
1007	                                        exported_users[item["uid"]]["sambaNTPassword"]=item["sambaNTPassword"]
1008	                                        exported_users[item["uid"]]["sambaLMPassword"]=item["sambaLMPassword"]
1009	                               
1010	                        exported={}
1011	                        exported["groups"]=exported_groups
1012	                        exported["users"]=exported_users
1013	
1014	                        return [True,exported]
1015	               
1016	                except Exception as e:
1017	
1018	                        return [False,str(e)]
1019	               
1020	                                               
1021	               
1022	        #def export_llum_info
1023	       
1024	        def import_llum_info(self,exported_info):
1025	               
1026	                try:
1027	                        for group in exported_info["groups"]:
1028	
1029	                                properties={}
1030	                                properties["description"]=exported_info["groups"][group]["description"]
1031	                                properties["cn"]=group
1032	                                self.add_group(properties)
1033	                               
1034	                        for user in exported_info["users"]:
1035	                               
1036	                                properties={}
1037	                                properties["uid"]=user
1038	                                properties["cn"]=exported_info["users"][user]["cn"]
1039	                                properties["sn"]=exported_info["users"][user]["sn"]
1040	                                properties["userPassword"]=exported_info["users"][user]["userPassword"]
1041	                                properties["sambaLMPassword"]=exported_info["users"][user]["sambaLMPassword"]
1042	                                properties["sambaNTPassword"]=exported_info["users"][user]["sambaNTPassword"]
1043	                                profile=exported_info["users"][user]["profile"]
1044	                               
1045	                                self.add_user(profile,properties)
1046	                               
1047	                               
1048	                        for group in exported_info["groups"]:
1049	                       
1050	                                for user in exported_info["groups"][group]["members"]:
1051	                                        self.add_to_group(user,group)
1052	                                       
1053	                        for user in exported_info["users"]:
1054	                               
1055	                                if exported_info["users"][user]["is_admin"]:
1056	                                        self.add_teacher_to_admins(user)
1057	                       
1058	                        return[True,]
1059	                except Exception as e:
1060	                       
1061	                        return [False,str(e)]
1062	               
1063	        #def import_llum_info
1064	       
1065	        def regenerate_net_files(self,mode=0):
1066	               
1067	                try:
1068	                        ret=self.light_get_user_list()
1069	                        users=[]
1070	                        for item in ret:
1071	                                user={}
1072	                                user["profile"]=item[5]
1073	                                user["uid"]=item[1]
1074	                                user["uidNumber"]=item[2]
1075	                                users.append(user)
1076	                       
1077	                        for user in users:
1078	                                try:
1079	                                        self.netfiles.exist_home_or_create(user,mode)
1080	                                except:
1081	                                        pass
1082	                               
1083	                        try:
1084	                               
1085	                                self.restore_groups_folders(self)
1086	                        except:
1087	                                pass
1088	                       
1089	                except:
1090	                        pass
1091	               
1092	        #def regenerate_net_files
1093	       
1094	        def is_roadmin_available(self):
1095	               
1096	                try:
1097	                        return self.ldap.custom_search("cn=roadmin,"+llxvars("LDAP_BASE_DN"))["status"]
1098	                except:
1099	                        return False
1100	       
1101	        #def is_roadmin_avaiable
1102	       
1103	
1104	#class Golem
1105	
1106	
1107	if __name__=="__main__":
1108	       
1109	        golem=Golem()
Note: See TracBrowser for help on using the repository browser.
Download in other formats:

    Plain Text Original Format 

Trac Powered

Powered by Trac 1.0.1
By Edgewall Software.

Visiteu el web del LliureX
http://lliurex.net/

