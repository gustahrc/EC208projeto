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
    ArrayList dadosp = new ArrayList();
    ArrayList dadosd = new ArrayList();

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
    
    private void CarregaArquivos() throws FileNotFoundException, IOException{
        
        ProgramMemory.createNewFile(); // cria o arquivo se ainda não existir
        InputStream isp = new FileInputStream(ProgramMemory);
        InputStreamReader isrp = new InputStreamReader(isp);
        BufferedReader brp = new BufferedReader(isrp);
        
        DataMemory.createNewFile(); // cria o arquivo se ainda não existir
        InputStream isd = new FileInputStream(ProgramMemory);
        InputStreamReader isrd = new InputStreamReader(isd);
        BufferedReader brd = new BufferedReader(isrd);
        
        String linhap = null;
        String linhad = null;

        linhap = brp.readLine();
        
        if (linhap != null) { //Se o arquivo não estiver vazio, entra no if

                while (linhap != null) {
                    
                    dadosp.add(linhap);
                    linhap = brp.readLine();
                    
                }
                
        }
        
        if (linhad != null) { //Se o arquivo não estiver vazio, entra no if

                while (linhad != null) {
                    
                    dadosd.add(linhad);
                    linhad = brd.readLine();
                    
                }
                
        }
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
                    Reg[RegDest] = dadosp[RegAddrMemory];
            }
            else if (InstrType == 9)
            {
                    // Store
                    DataMemory[RegAddrMemory] = Reg[RegSourceA];
            }
    }
    
}
