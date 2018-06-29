/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inatel.maquinavirtual.main;

/**
 *
 * @author joaop
 */
public class Memory {
    protected boolean valid; //only for cache
    protected String address;
    protected String data;
    
//    Cache(int tag, int data){
//        this.valid = 1;
//        this.tag = tag;
//        this.data = data;
//    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    
}
