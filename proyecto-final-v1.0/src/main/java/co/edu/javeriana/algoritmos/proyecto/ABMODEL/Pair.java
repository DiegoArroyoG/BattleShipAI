/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.algoritmos.proyecto.ABMODEL;

/**
 *
 * @author USUARIO
 * @param <F>
 * @param <S>
 */
public class Pair<F, S> extends java.util.AbstractMap.SimpleImmutableEntry<F, S> {

    public  Pair( F f, S s ) {
        super( f, s );
    }

    public F getFirst() {
        return getKey();
    }

    public S getSecond() {
        return getValue();
    }

    @Override
    public String toString() {
        return "["+getKey()+","+getValue()+"]";
    }

}