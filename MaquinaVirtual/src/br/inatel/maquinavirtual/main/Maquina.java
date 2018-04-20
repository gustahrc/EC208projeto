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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author joaop
 */
public class Maquina extends MaquinaVirtual{
    
    private final File ProgramMemory;
    private final File DataMemory;
    

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

    Maquina() throws FileNotFoundException, IOException{
        this.ProgramMemory = new File("ProgMemory.txt");
        this.DataMemory = new File("DataMemory.txt");
        
        // Inicializacao dos registros
	PC = 0;
	for (i = 0; i < 10; i++)
	{
		Reg[i] = 0;
	}
    }
    private int get_instruction_type(int pc){
        String instruction = (String)dadosp.get(pc);
        String subsInst= instruction.substring(0, 3);
        if(subsInst == "0001")
        {
            
        }
        else if(subsInst =="0011")
        {
            
        }
        else if()
        return 0;
    }
    
    private ArrayList arrayInstruction() throws FileNotFoundException, IOException{
        // cria o arquivo se ainda não existir
        InputStream isp = new FileInputStream(ProgramMemory);
        InputStreamReader isrp = new InputStreamReader(isp);
        BufferedReader brp = new BufferedReader(isrp);
        String linhap = brp.readLine();
        while (linhap != null) {
                  dadosp.add(linhap);
                  linhap = brp.readLine();  
        }        
        return dadosp;
    }
    // Prototipos
    public void decode()
    {
            InstrType = Instr >>> 12;

            if (InstrType == 1 || InstrType == 3)
            {
                    // Soma, Subtracao
                    RegSourceA = Instr >>> 8;
                    RegSourceA = RegSourceA & 0b0000000000001111;
                    RegSourceB = Instr >>> 4;
                    RegSourceB = RegSourceB & 0b0000000000001111;
                    RegDest = Instr & 0b0000000000001111;
            }
            else if (InstrType == 8)
            {
                    /* Load */
                    RegDest = Instr >>> 8;
                    RegDest = RegDest & 0b0000000000001111;
                    RegAddrMemory = Instr & 0b0000000011111111;
            }
            else if (InstrType == 9)
            {
                    /* Store */
                    RegSourceA = Instr >>> 8;
                    RegSourceA = RegSourceA & 0b0000000000001111;
                    RegAddrMemory = Instr & 0b0000000011111111;
            }
    }
    public void execute()
    {
            if (InstrType == 1)
            {
                    // Soma
                    Reg[RegDest] = Reg[RegSourceA] + Reg[RegSourceB];
            }
            else if (InstrType == 3)
            {
                    // Subtracao
                    Reg[RegDest] = Reg[RegSourceA] - Reg[RegSourceB];
            }
            else if (InstrType == 8)
            {
                    // Load
                    Reg[RegDest] = DataMemory[RegAddrMemory];
            }
            else if (InstrType == 9)
            {
                    // Store
                    DataMemory[RegAddrMemory] = Reg[RegSourceA];
            }
    }
    
}
