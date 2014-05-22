/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graph;

import exceptions.GeneticAlgorithmException;
import exceptions.NoPossibilityToCreateGraphException;

/**
 *
 * @author krzysztof
 */
public class NewClass {
    
    public static void main(String[] arg) throws NoPossibilityToCreateGraphException, GeneticAlgorithmException {
        GraphRepresentation gr = new GraphRepresentation(8, 8, 3);
        System.out.println(gr);
    }
    
}
