/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inatel.maquinavirtual.main;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 *
 * @author joaop
 */
public class MaquinaVirtual{
    

/**
	Tamanho das instruções: 16 bits
	
	Código das intruções:
	
		ADD: 	0001
		SUB: 	0011
		LOAD: 	1000
		STORE:	1001

	Instruções Tipo 1: 
	
		- Utilizado para operaçções aritméticas (soma, subtração, ...)
	     
             MSB                                      LSB
		   
		(Tipo instr.) (End. Reg 1) (End. Reg 2) (End Reg Dest.)
          
           4 bits        4 bits        4 bits       4 bits
           
		   
         - Exemplo: 0b0001000000010010 >>> |0001|0000|0001|0010
         
         	 	Realiza a soma (0001 >> tipo da instrução) do registro 0 (0000 
 	 	 	 >> end. Reg 1) com o registro 1 (0001 >> end. Reg 2) e salva o resultado
 	 	 	 em registro 2 (0010 >> end. Reg Dest.)
 	 	 	 
 	 	 	 
    Instruções Tipo 2:
    
     	 - Uitlizado para operações de LOAD e STORE
     	 
     	       MSB                        LSB
     	 
     	 (Tipo instr.) (End Reg) (End Memória de dados)

		    4 bits       4 bits        8 bits
		    
   	   - Exemplo: 0b1000000000010010 >>> |1000|0000|00001010
         
         	 	Realiza o LOAD (1000 >> tipo da instrução) do endereço de 
			memória 10 (00001010 >> end. Memória) para o registro 0 
			(0000 >> end. Reg )
*/
    // Memoria de programa           
    // Memoria de dados
    //public int[] DataMemory = {1, 2, 0, 0, 0, 0, 0, 0};
    
    /**
    
        Arquitetura da Cache:
        Look Aside: a cache fica no mesmo 
        barramento do sistema (no qual a
        memória principal está localizada).
        Durante a leitura, a cache monitora o
        barramento e caso seja um “hit”,
        responde para a CPU. Caso seja um
        “miss”, a memória principal responde
        para a CPU e a cache copia o dado para
        que, na próxima leitura, ocorra um “hit”.      
        
        - Bits menos significativos do endereço determina a linha, mais significativo a tag.
            - Se a linha corresponder e a tag não, então ocorrerá um miss e o dado deve ser buscado 
            na memória.
            - Senão ocorrerá um hit.
        
        Exemplo linha da cache:
        
        (v)     (endereço)    (dados)
        1 bit   8 bits     8 bits
        
        1   00000001 01000100
        
    
    */


    // Registradores
    public int PC;
    public int i;
    public int[] Reg = new int[16];
    public int Instr;
    public int InstrType;
    public int RegSourceA;
    public int RegSourceB;
    public int RegDest;
    public int RegAddrMemory;
    ArrayList<String> dadosp = new ArrayList<>();
    public int[] dadosd = new int[256];
    

    public void get_instruction_type(int PC){
        String instrucao = dadosp.get(PC);
        Instr = new BigInteger(instrucao,2).intValue();
        InstrType = Instr  >>> 12;
    }
    
    public int getReg(int instruction){
        RegSourceA = instruction >>> 8;
        return RegSourceA & 0b0000000000001111;
    }
    
    public void arrayInstruction() throws IOException{
        FileManager instructionFile = new FileManager("programData.txt");
        // cria o arquivo se ainda não existir
        dadosp = instructionFile.readProgramFile();
        
    }
    
    // Prototipos
    public void decode()
    {
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
                    System.out.println(RegAddrMemory);
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
    public void execute() throws IOException
    {
            MemoryManager memoryManager = new MemoryManager();
            String RegAddrMemoryString = Integer.toBinaryString(RegAddrMemory);
            String RegDestString = Integer.toBinaryString(Reg[RegDest]);
            
        switch (InstrType) {
            case 1:
                // Soma
                Reg[RegDest] = Reg[RegSourceA] + Reg[RegSourceB];
                System.out.println(Reg[RegDest]);
                break;
            case 3:
                // Subtracao
                Reg[RegDest] = Reg[RegSourceA] - Reg[RegSourceB];
                break;
            case 8:
                // Load
                System.out.println(RegAddrMemoryString);
                if(memoryManager.getData(RegAddrMemoryString) == null || RegAddrMemoryString == null)
                    break;
                
                System.out.println(RegAddrMemoryString);
                Reg[RegDest] = Integer.valueOf(memoryManager.getData(RegAddrMemoryString).trim());
                //Reg[RegDest] = dadosd[RegAddrMemory];
                break;
            case 9:
                // Store
                //dadosd[RegAddrMemory] = Reg[RegDest];
                memoryManager.saveData(RegAddrMemoryString, RegDestString);
                break;
            default:
                break;
        }
            
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
            //obj.dadosd[10]=4;
            obj.get_instruction_type(obj.PC);
            obj.decode();
            obj.execute();
         }
        
        // TODO code application logic here
    }
         
         
}
    

