# -*- coding: utf-8 -*-

import time
import Tictactoe 
from random import randint,choice

def RandomMove(b):
    '''Renvoie un mouvement au hasard sur la liste des mouvements possibles'''
    return choice(b.legal_moves())

def deroulementRandom(b):
    '''Effectue un déroulement aléatoire du jeu de morpion.'''
    print("----------")
    print(b)
    if b.is_game_over():
        res = getresult(b)
        if res == 1:
            print("Victoire de X")
        elif res == -1:
            print("Victoire de O")
        else:
            print("Egalité")
        return
    b.push(RandomMove(b))
    deroulementRandom(b)
    b.pop()


def getresult(b):
    '''Fonction qui évalue la victoire (ou non) en tant que X. Renvoie 1 pour victoire, 0 pour 
       égalité et -1 pour défaite. '''
    if b.result() == b._X:
        return 1
    elif b.result() == b._O:
        return -1
    else:
        return 0

winsX = 0
winsO = 0

def deroulement_brute(b):
    '''Effectue un déroulement aléatoire du jeu de morpion.'''
    global winsX
    global winsO
    if b.is_game_over():
        res = getresult(b)
        if res == 1:
            winsX += 1
        elif res == -1:
            winsO += 1
        return
    for move in b.legal_moves():
        b.push(move)
        deroulement_brute(b)
        b.pop()


def deroulement_total(b):
    if b.is_game_over():
        return 0,1
    n , f = 0 , 0
    for move in b.legal_moves():
        n += 1
        b.push(move)
        nn,nf = deroulement_total(b)
        b.pop()
        n += nn
        f += nf
    return n, f

def MaxMin(b):
    if b.is_game_over():
        return getresult(b)
    max = -2
    for move in b.legal_moves():
        b.push(move)
        res = MinMax(b)
        b.pop()
        if(res > max):
            max = res
    return max

def MinMax(b):
    if b.is_game_over():
        return getresult(b)
    min = 2
    for move in b.legal_moves():
        b.push(move)
        res = MaxMin(b)
        b.pop()
        if(res < min):
            min = res
    return min

board = Tictactoe.Board()
print(board)

### Deroulement d'une partie aléatoire
#deroulement_brute(board)
print(MaxMin(board))
#print("X a gagné " + str(winsX) + " fois")
#print("O a gagné " + str(winsO) + " fois")

print("Apres le match, chaque coup est défait (grâce aux pop()): on retrouve le plateau de départ :")
print(board)

