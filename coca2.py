explorer(graphe G, sommet s)
      marquer le sommet s
      pour voisin dans liste_voisins(s)
            si t n'est pas marqué alors
                marquer arete(s,t)
                explorer(G, t)

fonction algo2(Graphe G):
    pour composante_homogene dans composantes_homogenes:
            pivot = composante_homogene[0]
            pour sommet dans composante_homogene:
                si sommet != pivot:
                    pour arete dans E:
                        si (arete est heterogene) et sommet dans arete:
                            arete.remplacer(sommet,pivot)
                        sinon si (arete est homogene):
                            arete.supprimer()
                sommet.supprimer
    pour sommet s dans G:
            si s n'est pas marqué:
                   explorer(G, s)
    pour arete dans E:
        si arete n'est pas marqué:
            arete.supprimer
    retourner taille(E)

