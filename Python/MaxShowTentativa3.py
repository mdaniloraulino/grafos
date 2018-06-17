# -*- coding: utf-8 -*-
"""
Created on Sat Jun 16 19:05:44 2018

@author: danil
"""

# Uma banda não pode se apresentar no mesmo horario que outra banda de mesmo estilo
# nem se apresentar no mesmo palco
# nem 

import numpy as np


class Vertice(object):
    palco = 0
    
    def __init__(self,id,estilo):
        self.id = id
        self.estilo = estilo
        self.adj = list()
        
    def __repr__(self):
        return f'{self.id}  {len(self.adj)}'
    
    def __eq__(self,v):
       if isinstance(v,self.__class__):
            return self.id == v.id and self.estilo == v.estilo
#    def __cmp__(self, other):
#        return self.adj.count(1).__cmp__(other.adj.count(1))
    def __lt__(self,v):
        return len(self.adj) < len(v.adj)
    
    def __ne__(self,v):
       if isinstance(v,self.__class__):
            return self.id != v.id and self.estilo != v.estilo
        
    

class Aresta():
    def __init__(self,id):
        self.id = id
    
    def __repr__(self):
        return f'({self.id[0]},{self.id[1]})'
    def __eq__(self,a2):
        return self.id[0] == a2.id[0] and self.id[1] == a2.id[1] or self.id[1] == a2.id[0] and self.id[0] == a2.id[1]
     
    
vertices = []
arestas = []

file = open('bandas.txt','r') 

for line in file:
    banda = line.split(',')
    banda[1] = banda[1][:-1]
    vertices.append(Vertice(banda[0],banda[1]))
    
for i in vertices:
    for j in vertices:
        if i.adj == j.adj:
            print('toma no cu')


for i in vertices:
    for j in vertices:
        if i == j:
            continue
        elif i.estilo == j.estilo:
            aresta = Aresta([i,j])
            if aresta in arestas:
                continue
            if not i in j.adj:
                j.adj.append(i)
            if not j in i.adj:
                i.adj.append(j)
            arestas.append(aresta)
                                    
            
def weshPowell(vList):
    g = sorted(vList)
    
        

weshPowell(vertices)
            

            

    
    
        
    
    
    