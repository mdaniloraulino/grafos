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
        
    def __init__(self,id,estilo,horario):
        self.id = id
        self.estilo = estilo
        self.horario = horario
        self.adj = list()
        self.dia = int()
        
    def __repr__(self):
        return f'{self.id}|dia {self.dia}'
    
    def __eq__(self,v):
       if isinstance(v,self.__class__):
            return self.id == v.id and self.estilo == v.estilo
    def __cmp__(self, other):
        return len(self.adj).__cmp__(len(other.adj))
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
cores = tuple([1,2,3,4,5,6,7,8,9])

file = open('bandas.txt','r') 

for line in file:
    banda = line.split(',')
    banda[2] = banda[2][:-1]
    vertices.append(Vertice(banda[0],banda[1],banda[2]))    

for i in vertices:
    for j in vertices:
        if i == j:
            continue
        elif i.estilo == j.estilo or i.horario == j.horario:
            aresta = Aresta([i,j])
            if aresta in arestas:
                continue
            if not i in j.adj:
                j.adj.append(i)
            if not j in i.adj:
                i.adj.append(j)
            arestas.append(aresta)
                                    
def adicionaCor(vertice):
    possiveis = list(cores) 
    for v in vertice.adj:
        if v.dia != 0:
            possiveis.remove(v.dia)
    possiveis.sort()
    vertice.dia = possiveis[0]
            
def weshPowell(vList):
    g = sorted(vList,key = lambda vertice: len(vertice.adj),reverse=True )
    for v in g:
        adicionaCor(v)
    
    fileRes = open('cronograma.txt','w')
    
    
    i = 0
    lines = ''
    while i < len(cores):
        diaVazio = True        
        lines = lines + f'Bandas dias {i}:\n'
        diaAtual = ''
        for v in g:
            if v.dia == i:
                diaAtual = diaAtual + f'   {v.id}\n'
                diaVazio = False
        if not diaVazio:
            lines = lines + diaAtual
        i+=1
    fileRes.write(lines)
    fileRes.close     

weshPowell(vertices)
            

            

    
    
        
    
    
    