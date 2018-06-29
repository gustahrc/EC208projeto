/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inatel.maquinavirtual.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;

/**
 *
 * @author joaop
 */
public class FileManager {

    private final File file;
    private ArrayList fileArray = new ArrayList();

    public FileManager(String filename) {
        this.file = new File(filename);
    }

    public void setFileArray(ArrayList fileArray) {
        this.fileArray = fileArray;
    }

    protected ArrayList readProgramFile() throws FileNotFoundException, IOException {
        file.createNewFile(); // cria o arquivo se ainda não existir
        InputStream is = new FileInputStream(file);

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String linha;
        while ((linha = br.readLine()) != null) {
            fileArray.add(linha);
        }

        return fileArray;
    }

    protected ArrayList<Memory> readFile() throws FileNotFoundException, IOException {
        Memory data = new Memory();

        file.createNewFile(); // cria o arquivo se ainda não existir
        InputStream is = new FileInputStream(file);

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String linha;
        while ((linha = br.readLine()) != null) {
            
            if(linha.length() == 15 || linha.length() == 16){
                data.setValid(linha.substring(1).equals("1"));
                data.setAddress(linha.substring(1, 9)); //Binário para inteiro
                data.setData(linha.substring(9, 16)); //Binário para inteiro
                
                

                fileArray.add(data);
            }
        }

        return fileArray;
    }

    protected void saveMemoryFile(ArrayList<Memory> memoryArray) throws IOException {
        String line;

        OutputStream os = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
        try (BufferedWriter bw = new BufferedWriter(osw)) {
            for (Memory m : memoryArray) {

                line = "";

                //Valid
                if (m.isValid()) {
                    line += "1";
                } else {
                    line += "0";
                }
                line += m.getAddress();
                line += m.getData();

                bw.write(line + "\n");

            }
            bw.close(); //Fecha o arquivo
        }
    }

}
