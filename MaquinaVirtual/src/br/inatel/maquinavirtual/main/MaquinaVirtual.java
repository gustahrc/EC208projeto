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
public class MaquinaVirtual {

    /**
     * @param args the command line arguments
     */
    
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


    public static void main(String[] args) {
        byte i;
        Maquina maquinaVirtual = new Maquina();

	while (maquinaVirtual.PC < 4)
	{
		maquinaVirtual.Instr = maquinaVirtual.ProgMemory[maquinaVirtual.PC]; // busca da instrução
		maquinaVirtual.PC = maquinaVirtual.PC + 1;
		maquinaVirtual.decode(); // decodificação
		maquinaVirtual.execute();
	}

    }
    
}
