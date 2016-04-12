'''
Created on 23/10/2014

@author: usuario
'''

class binTree(object):
    '''
    classdocs
    '''


    def __init__(self, value,left=None,right=None):
        '''
        Constructor
        '''
        self.value=value
        self.left=left
        self.right=right
        
        
    def inOrder(self):
        if (self.left!=None):
            self.left.inOrder()
        print str(self.value)+','
        if (self.right!=None):
            self.right.inOrder()
            
    def preOrder(self):
        print str(self.value)+','
        if (self.left!=None):
            self.left.preOrder()
        if (self.right!=None):
            self.right.preOrder()
            
    def postOrder(self):
        if (self.left!=None):
            self.left.postOrder()
        if (self.right!=None):
            self.right.postOrder()
        print str(self.value)+','
        
    def insert(self,value):
        def _insert(arbol,nodo):
            if (arbol==None):
                raise TypeError
            elif (value<arbol.value):
                if (arbol.left==None):
                    arbol.left=nodo
                else:
                    _insert(arbol.left,nodo)
            else:
                if (arbol.right==None):
                    arbol.right=nodo
                else:
                    _insert(arbol.right,nodo)
        _insert(self,binTree(value))
    
    def __str__(self):
        res=""
        if (self.left!=None):
            res+=str(self.left)
        res+=', '+str(self.value)
        if (self.right!=None):
            res+=str(self.right)
        return res

b=binTree(5)
b.insert(2)
b.insert(8)
b.insert(1)
b.insert(4)
b.insert(6)
b.insert(3)
b.insert(7)
print '----------------------------------------------------'
b.inOrder()       
print '----------------------------------------------------'
b.postOrder()
print '----------------------------------------------------'
b.preOrder()
print str(b)
        
        