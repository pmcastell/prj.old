'''
Created on 27/10/2014

@author: usuario
'''
 
def sort1(lst):
    swapFlag = True
    iteration = 0
    while swapFlag:
        swapFlag = False
        for i in range(len(lst)-1):
            if lst[i] > lst[i+1]:
                temp = lst[i+1]
                lst[i+1] = lst[i]
                lst[i] = temp
                swapFlag = True

        L = lst[:]  # the next 3 questions assume this line just executed
        iteration += 1
    print "iteraciones: "+str(iteration)
    return lst

def sort2(lst):
    for iteration in range(len(lst)):
        minIndex = iteration
        minValue = lst[iteration]
        for j in range(iteration+1, len(lst)):
            if lst[j] < minValue:
                minIndex = j
                minValue = lst[j]
        temp = lst[iteration]
        lst[iteration] = minValue
        lst[minIndex] = temp

        L = lst[:]  # the next 3 questions assume this line just executed

    return lst

def sort3(lst):
    out = []
    for iteration in range(0,len(lst)):
        new = lst[iteration]
        inserted = False
        for j in range(len(out)):
            if new < out[j]:
                out.insert(j, new)
                inserted = True
                break
        if not inserted:
            out.append(new)

        L = out[:]  # the next 3 questions assume this line just executed

    return out
def sort4(lst):
    global numLlamadas
    def unite(l1, l2):
        if len(l1) == 0:
            return l2
        elif len(l2) == 0:
            return l1
        elif l1[0] < l2[0]:
            return [l1[0]] + unite(l1[1:], l2)
        else:
            return [l2[0]] + unite(l1, l2[1:])
    numLlamadas+=1
    if len(lst) == 0 or len(lst) == 1:
        return lst
    else:
        front = sort4(lst[:len(lst)/2])
        back = sort4(lst[len(lst)/2:])

        L = lst[:]  # the next 3 questions assume this line just executed

        return unite(front, back)

def isMyNumber(n):
    number=-7
    if (n<number):
        return -1
    elif (n==number):
        return 0
    else:
        return 1    
    
def jumpAndBackpedal(isMyNumber):
    '''
    isMyNumber: Procedure that hides a secret number. 
     It takes as a parameter one number and returns:
     *  -1 if the number is less than the secret number
     *  0 if the number is equal to the secret number
     *  1 if the number is greater than the secret number
 
    returns: integer, the secret number
    ''' 
    guess = 1
    
    foundNumber = False
    while not foundNumber:
        sign = isMyNumber(guess)
        if sign == 0:
            return guess
        if sign == -1:
            guess *= 2
        else:
            guess -= 1
    return guess

#print "hola"
#print "Numero encontrado: "
#print "numero encontrado: "+str(jumpAndBackpedal(isMyNumber))
    



class courseInfo(object):

    def __init__(self, courseName):
        self.courseName = courseName
        self.psetsDone = []
        self.grade = "No Grade"
        
    def setPset(self, pset, score):
        self.psetsDone.append((pset, score))
        
    def getPset(self, pset):
        for (p, score) in self.psetsDone:
            if p == pset:
                return score

    def setGrade(self, grade):
        if self.grade == "No Grade":
            self.grade = grade

    def getGrade(self):
        return self.grade



class edx(object):
    def __init__(self, courses):
        self.myCourses = []
        for course in courses:
            self.myCourses.append(courseInfo(course))

    def setGrade(self, grade, course="6.01x"):
        """
        grade: integer greater than or equal to 0 and less than or equal to 100
        course: string 

        This method sets the grade in the courseInfo object named by `course`.   

        If `course` was not part of the initialization, then no grade is set, and no
        error is thrown.

        The method does not return a value.
        """
        #   fill in code to set the grade
        for c in self.myCourses:
            if (c.courseName==course):
                c.setGrade(grade)
                break
                

    def getGrade(self, course="6.02x"):
        """
        course: string 

        This method gets the grade in the the courseInfo object named by `course`.

        returns: the integer grade for `course`.  
        If `course` was not part of the initialization, returns -1.
        """
        #   fill in code to get the grade
        for c in self.myCourses:
            if (c.courseName==course):
                return c.getGrade()
        return -1

    def setPset(self, pset, score, course="6.00x"):
        """
        pset: a string or a number
        score: an integer between 0 and 100
        course: string

        The `score` of the specified `pset` is set for the
        given `course` using the courseInfo object.

        If `course` is not part of the initialization, then no pset score is set,
        and no error is thrown.
        """
        #   fill in code to set the pset
        for c in self.myCourses:
            if (c.courseName==course):
                c.setPset(pset,score)
                break

    def getPset(self, pset, course="6.00x"):
        """
        pset: a string or a number
        course: string        

        returns: The score of the specified `pset` of the given
        `course` using the courseInfo object.
        If `course` was not part of the initialization, returns -1.
        """
        #   fill in code to get the pset
        for c in self.myCourses:
            if (c.courseName==course):
                return c.getPset(pset)
        return -1

#edX = edx( ["6.00x","6.01x","6.02x"] )
#edX.setPset(1,100)
#edX.setPset(2,100,"6.00x")
#edX.setPset(2,90,"6.00x")

#edX.setGrade(100)

#for c in ["6.00x","6.01x","6.02x"]:
#    edX.setGrade(90,c)

#for c in ["6.00x","6.01x","6.02x"]:
#    print "Curso "+c+" Grade: "+str(edX.getGrade(c))

#print dir(edX)    
    
    
class Member(object):
    def __init__(self, founder):
        """ 
        founder: string
        Initializes a member. 
        Name is the string of name of this node,
        parent is None, and no children
        """        
        self.name = founder
        self.parent = None         
        self.children = []    

    def __str__(self):
        return self.name    

    def add_parent(self, mother):
        """
        mother: Member
        Sets the parent of this node to the `mother` Member node
        """
        self.parent = mother   

    def get_parent(self):
        """
        Returns the parent Member node of this Member
        """
        return self.parent 

    def is_parent(self, mother):
        """
        mother: Member
        Returns: Boolean, whether or not `mother` is the 
        parent of this Member
        """
        return self.parent == mother  

    def add_child(self, child):
        """
        child: Member
        Adds another child Member node to this Member
        """
        self.children.append(child)   

    def is_child(self, child):
        """
        child: Member
        Returns: Boolean, whether or not `child` is a
        child of this Member
        """
        return child in self.children 


class Family(object):
    def __init__(self, founder):
        """ 
        Initialize with string of name of oldest ancestor

        Keyword arguments:
        founder -- string of name of oldest ancestor
        """

        self.names_to_nodes = {}
        self.root = Member(founder)    
        self.names_to_nodes[founder] = self.root   

    def set_children(self, mother, list_of_children):
        """
        Set all children of the mother. 

        Keyword arguments: 
        mother -- mother's name as a string
        list_of_children -- children names as strings
        """
        # convert name to Member node (should check for validity)
        mom_node = self.names_to_nodes[mother]   
        # add each child
        for c in list_of_children:           
            # create Member node for a child   
            c_member = Member(c)               
            # remember its name to node mapping
            self.names_to_nodes[c] = c_member    
            # set child's parent
            c_member.add_parent(mom_node)        
            # set the parent's child
            mom_node.add_child(c_member)         
    
    def is_parent(self, mother, kid):
        """
        Returns True or False whether mother is parent of kid. 

        Keyword arguments: 
        mother -- string of mother's name
        kid -- string of kid's name
        """
        mom_node = self.names_to_nodes[mother]
        child_node = self.names_to_nodes[kid]
        return child_node.is_parent(mom_node)   

    def is_child(self, kid, mother):
        """
        Returns True or False whether kid is child of mother. 

        Keyword arguments: 
        kid -- string of kid's name
        mother -- string of mother's name
        """        
        mom_node = self.names_to_nodes[mother]   
        child_node = self.names_to_nodes[kid]
        return mom_node.is_child(child_node)
    
    def path(self,a):
        path=[]
        while(str(a)!=str(self.root)):
            path.insert(0,a)
            a=str(self.names_to_nodes[a].get_parent())
        path.insert(0, str(self.root))
        return path

    def cousin(self, a, b):
        """
        Returns a tuple of (the cousin type, degree removed) 

        Keyword arguments: 
        a -- string that is the name of node a
        b -- string that is the name of node b

        cousin type:
          -1 if a and b are the same node.
          -1 if either one is a direct descendant of the other
          >=0 otherwise, it calculates the distance from 
          each node to the common ancestor.  Then cousin type is 
          set to the smaller of the two distances, as described 
          in the exercises above

        degrees removed:
          >= 0
          The absolute value of the difference between the 
          distance from each node to their common ancestor.
        """
        
        if (a==b):
            return (-1,0)
        elif (self.is_child(a,b) or self.is_child(b, a)):
            return (-1,1)
        patha=self.path(a)
        pathb=self.path(b)
        ancestorIndex=0
        limit=min(len(patha),len(pathb))
        while (ancestorIndex < limit and patha[ancestorIndex]==pathb[ancestorIndex]):
            ancestorIndex+=1
        ancestorIndex-=1
        indexa=indexb=ancestorIndex
        while(patha[indexa]!=a):
            indexa+=1
        while(pathb[indexb]!=b):
            indexb+=1
        return (min(indexa-ancestorIndex-1,indexb-ancestorIndex-1),abs(indexa-indexb))
        

def familyProff():
    f = Family("a")
    f.set_children("a", ["b", "c"])
    f.set_children("b", ["d", "e"])
    f.set_children("c", ["f", "g"])
    
    f.set_children("d", ["h", "i"])
    f.set_children("e", ["j", "k"])
    f.set_children("f", ["l", "m"])
    f.set_children("g", ["n", "o", "p", "q"])
    
    words = ["zeroth", "first", "second", "third", "fourth", "fifth", "non"]
    
    ## These are your test cases. 
    
    ## The first test case should print out:
    ## 'b' is a zeroth cousin 0 removed from 'c'
    t, r = f.cousin("b", "c")
    print "'b' is a", words[t],"cousin", r, "removed from 'c'"
    
    ## For the remaining test cases, use the graph to figure out what should 
    ## be printed, and make sure that your code prints out the appropriate values.
    
    t, r = f.cousin("d", "f")
    print "'d' is a", words[t],"cousin", r, "removed from 'f'"
    
    t, r = f.cousin("i", "n")
    print "'i' is a", words[t],"cousin", r, "removed from 'n'"
    
    t, r = f.cousin("q", "e")
    print "'q' is a", words[t], "cousin", r, "removed from 'e'"
    
    t, r = f.cousin("h", "c")
    print "'h' is a", words[t], "cousin", r, "removed from 'c'"
    
    t, r = f.cousin("h", "a")
    print "'h' is a", words[t], "cousin", r, "removed from 'a'"
    
    t, r = f.cousin("h", "h")
    print "'h' is a", words[t], "cousin", r, "removed from 'h'"
    
    t, r = f.cousin("a", "a")
    print "'a' is a", words[t], "cousin", r, "removed from 'a'"

class Frob(object):
    def __init__(self, name):
        self.name = name
        self.before = None
        self.after = None
    def setBefore(self, before):
        # example: a.setBefore(b) sets b before a
        self.before = before
    def setAfter(self, after):
        # example: a.setAfter(b) sets b after a
        self.after = after
    def getBefore(self):
        return self.before
    def getAfter(self):
        return self.after
    def myName(self):
        return self.name
    
def insert(atMe, newFrob):
    """
    atMe: a Frob that is part of a doubly linked list
    newFrob:  a Frob with no links 
    This procedure appropriately inserts newFrob into the linked list that atMe is a part of.    
    """
    while (atMe.myName()<newFrob.myName() and atMe.getAfter()!=None):
        atMe=atMe.getAfter()
    while (atMe.myName()>newFrob.myName() and atMe.getBefore()!=None):
        atMe=atMe.getBefore()
    if (atMe.myName()<newFrob.myName()):
        temp=atMe.getAfter()
        if (temp!=None):
            temp.setBefore(newFrob)
        atMe.setAfter(newFrob)
        newFrob.setBefore(atMe)
        newFrob.setAfter(temp)
    else:
        temp=atMe.getBefore()
        if (temp!=None):
            temp.setAfter(newFrob)
        atMe.setBefore(newFrob)
        newFrob.setAfter(atMe)
        newFrob.setBefore(temp)
        

def findFront(start):
    """
    start: a Frob that is part of a doubly linked list
    returns: the Frob at the beginning of the linked list 
    """
    if (start.getBefore()==None):
        return start
    else:
        return findFront(start.getBefore())