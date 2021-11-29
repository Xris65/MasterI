#include "EdgeConReduction.h"
#include "Z3Tools.h"
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>

#define NOT(X) Z3_mk_not(ctx, X)
#define AND(n) Z3_mk_and(ctx, n, (Z3_ast[n]) {
#define EAND })
#define OR(n) Z3_mk_or(ctx, n, (Z3_ast[n]) {
#define EOR })

#define X_(n1, n2, i) getVariableIsIthTranslator(ctx, n1, n2, i)
#define P_(j1, j2) getVariableParent(ctx, j1, j2)
#define L_(j, h) getVariableLevelInSpanningTree(ctx, h, j)

Z3_ast getVariableIsIthTranslator(Z3_context ctx, int node1, int node2, int number)
{
    char name[40];
    if (node1 < node2)
        snprintf(name, 40, "[(%d,%d),%d]", node1, node2, number);
    else
        snprintf(name, 40, "[(%d,%d),%d]", node2, node1, number);
    return mk_bool_var(ctx, name);
}

Z3_ast getVariableParent(Z3_context ctx, int child, int parent)
{
    char name[40];
    snprintf(name, 40, "p_[%d,%d]", child, parent);
    return mk_bool_var(ctx, name);
}

Z3_ast getVariableLevelInSpanningTree(Z3_context ctx, int level, int component)
{
    char name[40];
    snprintf(name, 40, "l_[%d,%d]", component, level);
    return mk_bool_var(ctx, name);
}


Z3_ast calculatePhi_1(Z3_context ctx, int i, int j, int u1, int u2, int v1, int v2){
    return
    AND(2)
        OR(2)
            NOT(X_(u1, v1, i)),
            X_(u2, v2, i)
        EOR,
        OR(2)
            NOT(X_(u1, v1, i)),
            NOT(X_(u1, v1, j))
        EOR
    EAND;
}


Z3_ast calculatePhi_2(Z3_context ctx, int i, int j, int m){
    return
    AND(3)
        OR(2)
            NOT(P_(i,j)),
            NOT(P_(i,m))
        EOR,
        OR(2)
            P_(i,j),
            P_(i,m)
        EOR,
        NOT(L_(i,0))
    EAND;
        
}

Z3_ast calculatePhi_3(Z3_context ctx, int i, int z, int y){
    return 
    OR(2)
        L_(i,z),
        OR(2)
            NOT( L_(i,z) ),
            NOT( L_(i,y) )
        EOR
    EAND;
}

Z3_ast calculatePhi_4(Z3_context ctx, int i, int k){
    return
    L_(i,k+1);
}

Z3_ast calculatePhi_5(Z3_context ctx, int i, int j, int m, int u, int v){
    return
    AND(2)
        OR(2)
            P_(i,j),
            P_(j,i)
        EOR,
        X_(u,v,m)
    EAND;   
}

Z3_ast calculatePhi_6(Z3_context ctx, int i, int j, int h){
    return
    AND(2)
        L_(i,h),
        L_(j,h-1)
    EAND;
}

Z3_ast calculatePhi_7(Z3_context ctx, int i, int j, int m, int u, int v, int h){
    return
    AND(2)
        OR(2)
            NOT(P_(i,j)),
            calculatePhi_5(ctx, i, j, m, u, v)
        EOR,
        OR(2)
            NOT(P_(i,j)),
            calculatePhi_6(ctx, i, j, h)
        EOR,
    EAND;           
}

/*
 * |-----------------------------------------------------|
 * |  C'est carrément l'enfer ce truc, désolé Mr.Duchon  |
 * |-----------------------------------------------------|
*/
Z3_ast calculatePhi_Gk(Z3_context ctx, int i, int j, int k, int u1, int u2, int v1, int v2, int m, int u , int v, int h1, int h2){
    // return
}

typedef struct {
    Z3_ast* list;
    int size;
} astList;

void addToList(astList* list, Z3_ast ast){
    // realloc and copy old content to new
    if( list == NULL)
    Z3_ast* newList = (Z3_ast*) malloc(sizeof(Z3_ast) * (list->size + 1));
    for(int i = 0; i < list->size; i++)
        newList[i] = list->list[i];
    }
    list->list = realloc(list->list, sizeof(Z3_ast) * (list->size + 1));
    list->list[list->size] = ast;
    list->size++;
}

Z3_ast EdgeConReduction(Z3_context ctx, EdgeConGraph edgeGraph, int cost)
{
    int number_components = getNumComponents(edgeGraph);
    Graph graph = getGraph(edgeGraph);
    int number_nodes = orderG(graph);
    int max_height = 0;
    int k = cost;
    bool solved = false;

    int index=0;
    astList formula_list;
    
    
    // i,j,m in Homogenous Component
    
    for (int i = 1; i < number_components; i++){
        for (int j = 1; j < number_components; j++){
            for(int m = 1 ; m < number_components; m++){
                if( i != j && i != m && j != m){

                    // u1-v1, u2-v2 in heterogenous edges
                    for(int u1 = 0 ; u1 < number_nodes; u1++){
                        for(int v1 = 0 ; v1 < number_nodes; v1++){
                            for(int u2 = 0 ; u2 < number_nodes; u2++){
                                for(int v2 = 0 ; v2 < number_nodes; v2++){
                                    if (u1!=u2 && v1!=v2){
                                        
                                        // h1,h2 in tree levels
                                        for(int h1 = 0 ; h1 < max_height; h1++){
                                            for(int h2 = 0 ; h2 < max_height; h2++){
                                                if (h1 != h2){
                                                // Create AST formula
                                                // if i,j,m are different.....h1,h2 are different.....u1-v1 and u2-v2 edges are different
                                                AND(5)
                                                    calculatePhi_1(ctx, i, j, u1, v1, u2, v2),
                                                    calculatePhi_2(ctx, i, j, m),
                                                    calculatePhi_3(ctx, i, h1, h2),
                                                    calculatePhi_4(ctx, i, k),
                                                    calculatePhi_7(ctx, i, j, m, u1, v1, h1)
                                                EAND;

                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }      
        }
    }
    
    return Z3_mk_false(ctx);
}

void getTranslatorSetFromModel(Z3_context ctx, Z3_model model, EdgeConGraph graph)
{
    return;
}