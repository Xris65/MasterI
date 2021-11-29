# -*- coding: utf-8 -*-
from enum import EnumMeta
import time
import chess
from random import randint, choice

## Note : Les tests des exercises sont mis apres les fonctions en commentaires.


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
t = time.perf_counter()

def elapsedTime():
    return time.perf_counter() - t
#deroulement exhaustif qui renvoie le nombre de noeuds explorés
def deroulementExhaustif(b: chess.Board, maxDepth=3, nbNoeuds=0, timeLimit=None):
    if(timeLimit != None and elapsedTime() > timeLimit):
        raise TimeoutError
    if (b.is_game_over() or maxDepth == 0):
        return nbNoeuds
    for m in b.generate_legal_moves():
        b.push(m)
        nbNoeuds = deroulementExhaustif(b,maxDepth-1, nbNoeuds + 1, timeLimit)
        b.pop()
    return nbNoeuds


# La fonction qui affiche les stats du deroulement exhaustif, c'est a dire 
# le nombre de noeuds explorés et la profondeur maximale
def statsExploration(b: chess.Board, timeLimit = 8):
    maxDepth = 1
    nbNoeuds = 0
    while(True):
        print("current depth: ", maxDepth)
        try:
            nbNoeuds = deroulementExhaustif(b, maxDepth, nbNoeuds, timeLimit)
        except TimeoutError:
            b.pop()
            print("max depth: ", maxDepth)
            print("nombre de noeuds en total : ",nbNoeuds)
            break
        print("nombre de noeuds en total pour depth = " + str(maxDepth) + " : ", nbNoeuds)
        maxDepth += 1
board = chess.Board()

#statsExploration(board)

#Ex 2.2
# Heuristic de Shannon 
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
# Fonction qui calcule le meilleur coup pour un joueur
def MinMax(b,depth=3):
    if(depth == 0 or b.is_game_over()):
        return ShannonHeuristic(b)
    pire = 1000
    for m in b.generate_legal_moves():
        val = MaxMin(b,depth-1)
        if(val < pire):
            pire = val
    return pire
# Fonction qui calcule le meilleur coup pour l'autre joueur(l'advesaire du premier)
def MaxMin(b,depth=3):
    if(depth == 0 or b.is_game_over()):
        return ShannonHeuristic(b)
    meilleur = -1000
    for m in b.generate_legal_moves():
        val = MinMax(b,depth-1)
        if(val > meilleur):
            meilleur = val
    return meilleur
    
# Fonction qui renvoie le meilleur coup pour le joueur
def CoupMaxMin(b,depth=3):
    coup = None
    meilleur = -1000
    for m in b.generate_legal_moves():
        b.push(m)
        val = MaxMin(b,depth-1)
        if(val > meilleur):
            meilleur = val
            coup = m
        b.pop()
    return coup

# Fonction qui renvoie le meilleur coup pour l'advesaire du premier joueur
def CoupMinMax(b,depth=3):
    coup = None
    pire = 1000
    for m in b.generate_legal_moves():
        b.push(m)
        val = MinMax(b,depth-1)
        if(val < pire):
            pire = val
            coup = m
        b.pop()
    return coup


#print(CoupMaxMin(board))
#Ex 2.4
# Fonction qui fait le deroulement d'une partie entre un IA utilisant CoupMaxMin avec depth 3
# et un IA qui fait des coups au hasard
def RandomVsAI(b):
    while(b.is_game_over() == False):
        b.push(CoupMaxMin(b))
        if(b.is_game_over()):
            break
        b.push(randomMove(b))
        print(b)
        print("----------------------")
    print(b.result())
#RandomVsAI(board)

# Fonction qui fait le deroulement d'une partie entre un IA utilisant CoupMaxMin avec depth 3
# et un IA qui utilise CoupMinMax avec depth 1
def Bot1vsBot3(b: chess.Board):
    while(b.is_game_over() == False):
        b.push(CoupMaxMin(b))
        if(b.is_game_over()):
            break
        b.push(CoupMinMax(b,1))
        print(b)
        print("----------------------")
    print(b.result())
#Bot1vsBot3(board)

#Ex 3.1
# Fonction qui renvoie le meilleur coup pour le joueur (version amelioree avec alpha et beta)
def MaxMinAlphaBeta(b,depth=3, alpha=-1000, beta=1000):
    if(depth == 0 or b.is_game_over()):
        return ShannonHeuristic(b)
    meilleur = -1000
    for m in b.generate_legal_moves():
        b.push(m)
        val = MinMaxAlphaBeta(b,depth-1,alpha,beta)
        if(val > meilleur):
            meilleur = val
        if(val > alpha):
            alpha = val
        if(beta <= alpha):
            b.pop()
            break
        b.pop()
    return alpha

# Fonction qui renvoie le meilleur coup pour l'advesaire du premier joueur (version amelioree avec alpha et beta)
def MinMaxAlphaBeta(b,depth=3, alpha=-1000, beta=1000):
    if(depth == 0 or b.is_game_over()):
        return ShannonHeuristic(b)
    pire = 1000
    for m in b.generate_legal_moves():
        b.push(m)
        val = MaxMinAlphaBeta(b,depth-1,alpha,beta)
        if(val < pire):
            pire = val
        if(val < beta):
            beta = val
        if(beta <= alpha):
            b.pop()
            break
        b.pop()
    return beta

# Fonction qui renvoie le meilleur coup pour le joueur ou l'advesaire (version amelioree avec alpha et beta)
# Differente technique cette fois ci, j'utilise la variable chercheMax pour savoir si c'est le joueur ou l'advesaire
# qui doit jouer. Si c'est le joueur, chercheMax = True, sinon chercheMax = False
def ProchainCoup(b, depth=3, chercheMax=True):
    coup = None
    meilleur = -1000
    pire = 1000
    for m in b.generate_legal_moves():
        b.push(m)
        if(chercheMax):
            val = MaxMinAlphaBeta(b,depth-1)
            if(val > meilleur):
                meilleur = val
                coup = m
        else:
            val = MinMaxAlphaBeta(b,depth-1)
            if(val < pire):
                pire = val
                coup = m
        b.pop()
    return coup

# Fonction qui simule le deroulement d'une partie entre deux IA utilisant la version amelioree
# du premier IA en utilisant alpha beta avec la meme profondeur de recherche
def Bot3vsBot3Optimise(b: chess.Board):
    while(b.is_game_over() == False):   
        print(b)
        print("----------------------")
        coup = ProchainCoup(b,3,True)
        print(coup)
        b.push(coup)
        if(b.is_game_over()):
            break
        b.push(ProchainCoup(b,3,False))
    print(b.result())

#Bot3vsBot3Optimise(board)

#Ex 3.2
# Fonction qui calcule le meilleur coup pour le joueur avec un limite de temps en utilisant
# la version avec alpha beta
def MaxMinLimite(b,depth=3,timeLimit=10, alpha=-1000, beta=1000, actualTime=time.time()):
    timer = time.time()
    if(depth == 0 or b.is_game_over()):
        return ShannonHeuristic(b)
    meilleur = -1000
    for m in b.generate_legal_moves():
        if(time.time() - timer > timeLimit):
            return meilleur
        b.push(m)
        val = MinMaxLimite(b,depth-1,alpha,beta,timeLimit)
        if(val > meilleur):
            meilleur = val
        if(val > alpha):
            alpha = val
        if(beta <= alpha):
            b.pop()
            break
        b.pop()
    return meilleur
#  Fonction qui calcule le meilleur coup pour l'advesaire du premier joueur avec un limite de temps en utilisant
# la version avec alpha beta
def MinMaxLimite(b, depth=3 , timeLimit=10, actualTime=time.time() ,alpha=-1000, beta=1000):
    if(depth == 0 or b.is_game_over()):
        return ShannonHeuristic(b)
    pire = 1000
    for m in b.generate_legal_moves():
        if(time.time() - actualTime > timeLimit):
            return pire
        b.push(m)
        val = MaxMinLimite(b,depth-1,alpha,beta,timeLimit)
        if(val < pire):
            pire = val
        if(val < beta):
            beta = val
        if(beta <= alpha):
            b.pop()
            break
        b.pop()
    return pire
# Fonction qui renvoie le meilleur coup pour le joueur ou l'advesaire dans un temps limite
# (version amelioree avec alpha beta)
# Differente technique cette fois ci, j'utilise la variable chercheMax pour savoir si c'est le joueur ou l'advesaire
# qui doit jouer. Si c'est le joueur, chercheMax = True, si c'est son advesaire chercheMax = False
def IterativeDeepeningMaxMin(b, depth=3, timeLimit=10,chercheMax=True):
    actualTime = time.time()
    meilleurCoup = None
    meilleur = -1000
    pire = 1000
    for m in b.generate_legal_moves():
        if(timeLimit != None and time.time() - actualTime > timeLimit):
            return meilleurCoup
        b.push(m)
        if(chercheMax):
            val = MaxMinLimite(b,depth-1,timeLimit,time.time())
            if(val > meilleur):
                meilleur = val
                meilleurCoup = m
        else:
            val = MinMaxLimite(b,depth-1,timeLimit,time.time())
            if(val < pire):
                pire = val
                meilleurCoup = m
        b.pop()
    return meilleurCoup
    
    
    
#Ex 3.3
# Fonction qui lance une partie entre un IA et l'utilisateur
def JouerPartie(b: chess.Board):
    while(b.is_game_over() == False):
        if(b.is_game_over() == False):
            b.push(IterativeDeepeningMaxMin(b,3,10,True))
        print(b)
        print("----------------------")
        move = chess.Move.from_uci(input("Entrez le coup a jouer : "))
        while(b.is_legal(move) == False):
            move = chess.Move.from_uci(input("Coup invalide, entrez un autre coup : "))
        b.push(move)
        if(b.is_game_over()):
            break

    print(b.result())

#JouerPartie(board)