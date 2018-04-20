/********************************************************************************

Máquina virtual IOT010

	Este codigo implementa uma máquina virtual (interpretador) capaz de buscar,
decodificar e executar um set de instrucão criado exclusivamente para demostrações 
durante as aulas de IOT010.   

***********************************************************************************

Detalhes do set de instrução

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
 	 	 	 
********************************************************************************/
    
// Memoria de programa            
private int[] ProgMemory = {0b1000000000000000, 0b1000000100000001, 0b0001000000010010, 0b1001001000000010};
// Memoria de dados
private int[] DataMemory = {1, 2, 0, 0, 0, 0, 0, 0};


// Registradores
private int PC;
private int Instr;
private int InstrType;
private int RegSourceA;
private int RegSourceB;
private int RegDest;
private int RegAddrMemory;
private int[] Reg = new int[10];

// Prototipos
private void decode()
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
private void execute()
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

private static int Main()
{
	byte i;

	// Inicializacao dos registros
	PC = 0;
	for (i = 0; i < 10; i++)
	{
		Reg[i] = 0;
	}

	while (PC < 4)
	{
		Instr = ProgMemory[PC]; // busca da instrução
		PC = PC + 1;
		decode(); // decodificação
		execute();
	}

	return 0;
}
