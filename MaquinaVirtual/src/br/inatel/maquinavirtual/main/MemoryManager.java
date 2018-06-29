/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inatel.maquinavirtual.main;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author joaop
 */
public class MemoryManager {
    static FileManager cacheFile = new FileManager("cache.txt");
    static FileManager memoryFile = new FileManager("memory.txt");
    protected ArrayList<Memory> cache = new ArrayList<>();
    protected ArrayList<Memory> memory = new ArrayList<>();
    
    MemoryManager() throws IOException{
        this.cache = cacheFile.readFile();
        this.memory = memoryFile.readFile();
        initCache();
    }
    
    protected void initCache() throws IOException{
        Memory tmpCache;
        for(int i = 0; i < 200; i++){
            tmpCache = new Memory();
            tmpCache.setValid(false);
            tmpCache.setAddress("00000000".substring(Integer.toBinaryString(i).length()) + Integer.toBinaryString(i));
            tmpCache.setData("00000000");
            this.cache.add(tmpCache);
        }
        cacheFile.saveMemoryFile(cache);
    }
    
//    public void updateMemory(int address){
//        
//        for (Memory c : cache) {
//            for (Memory m : memory){
//                if(m.getAddress() == c.getAddress()){
//                    m = c; //atualiza a memória com o valor da cache
//                    c.valid = false; //invalida a linha da cache
//                }
//            }
//        }
//    }
    
    public void pushToCache(String address) throws IOException{
        this.cache = cacheFile.readFile();
        this.memory = memoryFile.readFile();
        
        for (Memory m : memory){
            if(m.getAddress() != null && address != null){
                if(m.getAddress().equals(address)){

                    for (Memory c : cache) {
                        if(c.getAddress() == null ? address == null : c.getAddress().equals(address)){
                            c = m;
                            c.setValid(true);
                            System.out.println("Endereço " + address + " foi atualizado!");
                            cacheFile.saveMemoryFile(cache);
                            break;
                        }
                    }

                }
            }
            
        }
        
    }
    
    public String getData(String address) throws IOException{
        this.cache = cacheFile.readFile();
        this.memory = memoryFile.readFile();
        
        Memory mem = new Memory();
        
        for (Memory c : cache) {
            if((c.getAddress() == null ? address == null : c.getAddress().equals(address)) && c.isValid()){
                mem = c;
                System.out.println("Hit =)");
                System.out.println("Valor: " + mem.getData());
                return mem.getData();
            }
        }
        
        System.out.println("Miss =(");
        
        pushToCache(address);
        
        for (Memory c : cache) {
            if((c.getAddress() == null ? address == null : c.getAddress().equals(address)) && c.isValid()){
                mem = c;
                System.out.println("Valor: " + mem.getData());
                return mem.getData();
            }
        }
        
        
        return mem.getData();
    }
    public void saveData(String address, String data) throws IOException{
        this.cache = cacheFile.readFile();
        this.memory = memoryFile.readFile();
        
        for (Memory m : memory){
            if(m.getAddress() == null ? address == null : m.getAddress().equals(address)){
                m.setAddress(address); //atualiza o dado da memória
            }
        }
        
        for (Memory c : cache) {
            if(c.getAddress() == null ? address == null : c.getAddress().equals(address)){
                c.setValid(false); //invalida a linha da cache
            }
        }
        
        cacheFile.saveMemoryFile(cache);
        memoryFile.saveMemoryFile(memory);
    }
    
    
}
