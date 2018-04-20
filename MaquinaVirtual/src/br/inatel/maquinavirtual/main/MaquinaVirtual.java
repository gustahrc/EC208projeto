/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inatel.maquinavirtual.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author joaop
 */
public class MaquinaVirtual{
    
    

    // Memoria de programa            
     public int[] ProgMemory = {0b1000000000000000, 0b1000000100000001, 0b0001000000010010, 0b1001001000000010};
    // Memoria de dados
    //public int[] DataMemory = {1, 2, 0, 0, 0, 0, 0, 0};


    // Registradores
    public int PC;
    public int i;
    public int[] Reg = new int[10];
    public int Instr;
    public int InstrType;
    public int RegSourceA;
    public int RegSourceB;
    public int RegDest;
    public int RegAddrMemory;
    ArrayList<String> dadosp = new ArrayList<String>();
    ArrayList<String> dadosd = new ArrayList<String>();

    public void get_instruction_type(int PC){
        String instrucao = dadosp.get(PC);
        Instr = new BigInteger(instrucao,2).intValue();
        InstrType = Instr  >>> 12;
    }
    
    public void arrayInstruction() throws IOException{
        // cria o arquivo se ainda não existir
        InputStream isp = new FileInputStream("arquitetura.txt");
        InputStreamReader isrp = new InputStreamReader(isp);
        BufferedReader brp = new BufferedReader(isrp);
        String linhap;
        while ((linhap = brp.readLine()) != null)  {
                  dadosp.add(linhap);  
        }        
    }
    // Prototipos
    public void decode()
    {
            System.out.println(InstrType);
            if (InstrType == 1 || InstrType == 3)
            {
                    // Soma, Subtracao
                    RegSourceA = Instr >>> 8;
                    RegSourceA = RegSourceA & 0b0000000000001111;
                    RegSourceB = Instr >>> 4;
                    RegSourceB = RegSourceB & 0b0000000000001111;
                    RegDest = Instr & 0b0000000000001111;
                    System.out.println(RegDest);
                    PC++;
            }
            else if (InstrType == 8)
            {
                    /* Load */
                    RegDest = Instr >>> 8;
                    RegDest = RegDest & 0b0000000000001111;
                    RegAddrMemory = Instr & 0b0000000011111111;
                    PC++;
            }
            else if (InstrType == 9)
            {
                    /* Store */
                    RegSourceA = Instr >>> 8;
                    RegSourceA = RegSourceA & 0b0000000000001111;
                    RegAddrMemory = Instr & 0b0000000011111111;
                    PC++;
            }
    }
    public void execute()
    {
            if (InstrType == 1)
            {
                    // Soma
                    Reg[RegDest] = Reg[RegSourceA] + Reg[RegSourceB];
                    System.out.println(Reg[RegDest]);
            }
            else if (InstrType == 3)
            {
                    // Subtracao
                    Reg[RegDest] = Reg[RegSourceA] - Reg[RegSourceB];
            }
            /*
            else if (InstrType == 8)
            {
                    // Load
                    Reg[RegDest] = dadosp[RegAddrMemory];
            }
            else if (InstrType == 9)
            {
                    // Store
                    DataMemory[RegAddrMemory] = Reg[RegSourceA];
            }
            */
    }
     public static void main(String[] args) throws IOException {
        MaquinaVirtual obj = new MaquinaVirtual();
        obj.arrayInstruction();
        obj.PC=0;
        int pcMax=obj.dadosp.size();
         for (int i = 0; i < pcMax; i++) {
             System.out.println(obj.dadosp.get(i));
         }
        while(obj.PC<pcMax){
             obj.get_instruction_type(obj.PC);
             obj.decode();
             obj.execute();
         }
        
        // TODO code application logic here
    }
         
         
     }
    

