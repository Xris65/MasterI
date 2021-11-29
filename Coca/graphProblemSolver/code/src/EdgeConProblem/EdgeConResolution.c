#include "EdgeConResolution.h"
#include "Graph.h"
#include <stdlib.h>
#include <stdio.h>
/*
 * Rentre dans le graphe par un noeud et le marque, puis marque son voisin, et ainsi de suite.
 *
 */

void explore(Graph graph, int node, bool *marked_nodes)
{
    // mark node node
    marked_nodes[node] = true;
    for (int i = 0; i < graph.numNodes; i++)
    {
        if (isEdge(graph, node, i) && !marked_nodes[i])
        {
            explore(graph, i, marked_nodes);
        }
    }
}
/**
 * Notes pour l'oral:
 * - notre algorithme ne peut pas être implémenté avec les fonctions mises
 *   à notre disposition. Il manque les fonctions de modification du graphe.
 */
// returns false

void connectTwoHomogeneousComponents(EdgeConGraph graph, int component1, int component2)
{
    Graph g = getGraph(graph);
    for (int node1 = 0; node1 < orderG(g) - 1; node1++)
    {
        for (int node2 = node1 + 1; node2 < orderG(g); node2++)
        {
            if(isEdgeHeterogeneous(graph,node1,node2)){
            addTranslator(graph, node1, node2);
            return;
            }
        }
    }
}
int BruteForceEdgeCon(EdgeConGraph graph)
{
    int nbTranslators = 0;
    for (int component1 = 0; component1 < getNumComponents(graph) - 1; component1++)
    {
        for (int component2 = component1 + 1; component2 < getNumComponents(graph); component2++)
        {
            connectTwoHomogeneousComponents(graph, component1, component2);
            nbTranslators++;
        }
    }
    computesHomogeneousComponents(graph);
    return nbTranslators;
    
}