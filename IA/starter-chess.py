# -*- coding: utf-8 -*-
from enum import EnumMeta
import time
import chess
from random import randint, choice

def randomMove(b):
    '''Renvoie un mouvement au hasard sur la liste des mouvements possibles. Pour avoir un choix au hasard, il faut
    construire explicitement tous les mouvements. Or, generate_legal_moves() nous donne un itérateur.'''
    return choice([m for m in b.generate_legal_moves()])

def deroulementRandom(b):
    '''Déroulement d'une partie d'échecs au hasard des coups possibles. Cela va donner presque exclusivement
    des parties très longues et sans gagnant. Cela illustre cependant comment on peut jouer avec la librairie
    très simplement.'''
    print("----------")
    print(b)
    if b.is_game_over():
        print("Resultat : ", b.result())
        return
    b.push(randomMove(b))
    deroulementRandom(b)
    b.pop()
#Ex 2.1
def deroulementExhaustif(b: chess.Board, maxDepth=3, nbNoeuds=0, timeLimit=None):
    #Faites une recherche exhaustive de toutes les parties d’échec, mais en limitant la profondeur de larecherche par un paramètre de la recherche.
    #print("----------")
    #print(b)
    if(timeLimit != None and elapsedTime() > timeLimit):
        raise TimeoutError
    if (b.is_game_over() or maxDepth == 0):
        #if(b.is_checkmate()):
            #print("profondeur : ", maxDepth)
        return nbNoeuds
    for m in b.generate_legal_moves():
        b.push(m)
        deroulementExhaustif(b,maxDepth-1, nbNoeuds + 1, timeLimit)
        b.pop()
    return nbNoeuds

def statsExploration(b: chess.Board):
    maxDepth = 1
    nbNoeuds = 0
    while(True):
        print("current depth: ", maxDepth)
        try:
            nbNoeuds = deroulementExhaustif(b, maxDepth, nbNoeuds, 3)
        except TimeoutError:
            b.pop()
            print("max depth: ", maxDepth)
            print("nombre de noeuds en total : ",nbNoeuds)
            break
        maxDepth += 1
board = chess.Board()
#deroulementRandom(board)
t = time.perf_counter()

def elapsedTime():
    return time.perf_counter() - t

statsExploration(board)

#Ex 2.2
def ShannonHeuristic(b: chess.Board):
    valPieces = {'.' : 0, 'P': 1, 'N': 3, 'B': 3, 'R': 5, 'Q': 9, 'K': 200}
    score = 0
    for k,p in board.piece_map().items():
        s = p.symbol()
        v = valPieces[s.upper()]
        if( s == s.upper()):
            score += v
        else:
            score -= v
        line = k/8
        if( s == 'P'):
            score += (1+line)*0.05
        elif s=='p':
            score -= (8-line)*0.05
    return score

#Ex 2.3
def MinMax(b,depth=3):
    pire = 1000
    for m in b.generate_legal_moves():
        val = MaxMin(b,depth-1)
        if(val < pire):
            pire = val
    return val

def MaxMin(b,depth=3):
    meilleur = -1000
    for m in b.generate_legal_moves():
        val = MaxMin(b,depth-1)
        if(val > meilleur):
            meilleur = val
    return val

def CoupMaxMin(b,depth=3):
    coup = None
    meilleur = -1000
    for m in b.generate_legal_moves():
        b.push(m)
        val = MinMax(b,depth-1)
        if(val > meilleur):
            meilleur = val
            coup = m
        b.pop()
    return coup


def AlphaBeta(b,depth=3,alpha=-1000,beta=1000):
    if(depth == 0 or b.is_game_over()):
        return ShannonHeuristic(b)
    for m in b.generate_legal_moves():
        b.push(m)
        val = AlphaBeta(b,depth-1,alpha,beta)
        if(val > alpha):
            alpha = val
        if(alpha >= beta):
            break
        b.pop()
    return alpha