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
    protected static ArrayList<Memory> cache = new ArrayList<>();
    protected static ArrayList<Memory> memory = new ArrayList<>();
    
    MemoryManager() throws IOException{
        MemoryManager.cache = cacheFile.readFile();
        MemoryManager.memory = memoryFile.readFile();
//        initCache();
    }
    
//    protected void initCache() throws IOException{
//        Memory tmpCache;
//        for(int i = 0; i < 200; i++){
//            tmpCache = new Memory();
//            tmpCache.setValid(false);
//            tmpCache.setAddress("00000000".substring(Integer.toBinaryString(i).length()) + Integer.toBinaryString(i));
//            tmpCache.setData("00000000");
//            this.cache.add(tmpCache);
//        }
//        cacheFile.saveMemoryFile(cache);
//    }
    
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
        //MemoryManager.cache = cacheFile.readFile();
        //MemoryManager.memory = memoryFile.readFile();
        
        address = "00000000".substring(address.length()) + address;
        
        //System.out.println("Push " + address);
        
        for (Memory m : MemoryManager.memory){
            //System.out.println("Endereco Memoria" + m.getAddress() + " - Endereco pesquisado " + address);
            if(m.getAddress() != null && address != null){
                if(m.getAddress().equals(address)){

                    for (Memory c : MemoryManager.cache) {
                        if(c.getAddress().equals(m.getAddress())){
                            c = m;
                            c.setValid(true);
                            System.out.println("Endereço " + address + " foi atualizado!");
                            //cacheFile.saveMemoryFile(MemoryManager.cache);
                            return;
                        }else {
                            System.out.println("Endereço " + m.getAddress() + " foi atualizado!");
                            MemoryManager.cache.add(m);
                            //cacheFile.saveMemoryFile(MemoryManager.cache);
                            return;
                        }
                    }

                }
            }
            
        }
        
    }
    
    public String getData(String address) throws IOException{
        //MemoryManager.cache = cacheFile.readFile();
        //MemoryManager.memory = memoryFile.readFile();
        address = "00000000".substring(address.length()) + address;
        
        Memory mem = new Memory();
        
        for (Memory c : MemoryManager.cache) {
            if( c.getAddress().equals(address) && c.isValid()){
                mem = c;
                System.out.println("Hit =)");
                System.out.println("Valor: " + mem.getData());
                return mem.getData();
            }
        }
        
        System.out.println("Miss =(");
        
        pushToCache(address);
        
        for (Memory c : MemoryManager.cache) {
            if(c.getAddress().equals(address) && c.isValid()){
                mem = c;
                System.out.println("Valor: " + mem.getData());
                return mem.getData();
            }
        }
        
        
        return mem.getData();
    }
    public void saveData(String address, String data) throws IOException{
        //MemoryManager.cache = cacheFile.readFile();
        //MemoryManager.memory = memoryFile.readFile();
        Memory tempMemory;
        
        address = "00000000".substring(address.length()) + address;
        data = "00000000".substring(data.length()) + data;
        
        for (Memory m : MemoryManager.memory){
            if(m.getAddress().equals(address)){
                m.setData(data); //atualiza o dado da memória
                break;
            }else {
                tempMemory = new Memory();
                tempMemory.setAddress(address);
                tempMemory.setData(data);
                memory.add(tempMemory);
                break;
            }
        }
        
        for (Memory c : MemoryManager.cache) {
            if(c.getAddress().equals(address)){
                c.setValid(false); //invalida a linha da cache
                break;
            }
        }
        
        cacheFile.saveMemoryFile(MemoryManager.cache);
        memoryFile.saveMemoryFile(MemoryManager.memory);
    }
    
    
}
