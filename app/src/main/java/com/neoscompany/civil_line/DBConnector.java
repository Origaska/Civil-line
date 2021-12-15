package com.neoscompany.civil_line;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DBConnector {
    private InputStream inputStream;
    private static final String SEPARATOR=";";
    public final int MAX_VALUES = 10;
    public DBConnector(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    public ArrayList<String [] > readFile(String url, Context cntx) throws IOException {
        ArrayList<String []> values = new ArrayList<String []>();
        values.add(new String[]{"Empty"});
        BufferedReader book = null;
        try  {
            book = new BufferedReader(new FileReader(url));
            String line = book.readLine();
            values.clear();
            while (line != null){
                values.add(line.split(SEPARATOR));

                line = book.readLine();
            }
//            book.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(cntx,"Archivo no encontrado",Toast.LENGTH_SHORT);
        } catch (IOException e) {

        } finally {
            if (book!= null){
                book.close();
            }
        }
        return values;
    }


    public boolean addValue(String[] value,String url, Context cntx) throws IOException {

        ArrayList<String[]> readValues = readFile(url, cntx);

        if (readValues.get(0)[0] != "Empty" ) {
            int len = 0;
            for (String[] v : readValues) {
                len++;
                if (len == MAX_VALUES) {

                    readValues.remove(0);
                }
            }
        }else{
            readValues.clear();
        }

        readValues.add(value);
        if (writeContent(readValues,url)) {
            return true;
        }else{
            return false;
        }
    }

    private boolean writeContent(ArrayList<String []> datos,String  url){

        BufferedWriter book = null;
        try {
            book = new BufferedWriter(new FileWriter(url));


        } catch (IOException e) {
            return false;
        }
        return true;
    }


    public boolean deleteValue(){
        return true;
    }

}
