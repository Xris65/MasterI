# 0 represente la position du sommet de gauche d'une arete
# 1 represente la position du sommet de droite d'une arete



fonction position_sommet(arete A, sommet S):
    si S == A[0]:
        retourner 1
    retourner 0


fonction algo1_rec(liste C,sommet u,sommet v,int compteur):
    pour arete dans C:
        si u dans arete:
            compteur++
            C = C - arete
            position_sommet_oppose=position_sommet(arete, u)
            si (v == arete[position_sommet_oppose]):
                retourner compteur
            sinon si (C est vide):
                retourner 0
            sinon retourner algo1_rec(C, arete[position_sommet_oppose], v, compteur) 
    retourner 0


fonction algo1(liste C,sommet u,sommet v):
    retourner algo1_rec(C, u, v, 0)


algo1([1-2,2-3,3-4,4-5,5-6], 6, 1)







