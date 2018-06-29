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
            System.out.println("Endereco Memoria" + m.getAddress() + " - Endereco pesquisado " + address);
            if(m.getAddress() != null && address != null){
                if(m.getAddress().equals(address)){
                    if(MemoryManager.cache.size() > 0) {
                        int indexCache = 0;
                        for (Memory c : MemoryManager.cache) {
                            if(c.getAddress().equals(m.getAddress())){
                                c = m;
                                c.setValid(true);
                                MemoryManager.cache.set(indexCache, c);
                                System.out.println("Endereço " + address + " foi atualizado!");
//                                cacheFile.saveMemoryFile(MemoryManager.cache);
                                return;
                            }
                            if(indexCache == MemoryManager.cache.size()){ //Não está na Cache
                                System.out.println("Endereço " + m.getAddress() + " foi adicionado!");
                                m.setValid(true);
                                MemoryManager.cache.add(m);
//                                cacheFile.saveMemoryFile(MemoryManager.cache);
                                return;
                            }
                            indexCache++;
                        }
                    }else {
                        MemoryManager.cache.add(m);
//                        cacheFile.saveMemoryFile(MemoryManager.cache);
                        return;
                    }

                }
            }
            
        }
        
    }
    
    public String getData(String address) throws IOException{
        //MemoryManager.cache = cacheFile.readFile();
        //MemoryManager.memory = memoryFile.readFile();
        address = "00000000".substring(address.length()) + address;
        System.out.println(address);
        Memory mem = new Memory();
        for (Memory c : MemoryManager.cache) {
//            System.out.println("---  Printando comparado 1 ---");
//            System.out.println(c.getAddress());
//            System.out.println("---  Printando comparado 2 ---");
//            System.out.println(address);
//            System.out.println("----- printando validacao ---");
//            System.out.println(c.isValid());
            if( c.getAddress().equals(address) && c.isValid()){
                mem = c;
                System.out.println("Hit =)");
                System.out.println("Endereço: " + mem.getData() + " - Valor: " + mem.getData());
                return mem.getData();
            }
        }
    
        System.out.println("Miss =(");
        pushToCache(address);
        cacheFile.saveMemoryFile(MemoryManager.cache);
        
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
        
        int indexMem = 0;
        
        for (Memory m : MemoryManager.memory){
            if(m.getAddress().equals(address)){
                m.setData(data); //atualiza o dado da memória
                MemoryManager.memory.set(indexMem, m);
                break;
            }
            if(indexMem == MemoryManager.cache.size()){ //Não está na Cache
                tempMemory = new Memory();
                tempMemory.setAddress(address);
                tempMemory.setData(data);
                memory.add(tempMemory);
                break;
            }
            indexMem++;
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
