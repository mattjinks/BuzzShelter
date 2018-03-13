package com.example.hp.buzzshelter;


import java.io.InputStream;
import java.util.List;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.IOException;



/**
 * Created by YizraGhebre on 3/13/18.
 */

public class ShelterList {

    private List<Shelter> shelters;
    private InputStream inputStream;


    public ShelterList (InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<Shelter> getShelterList() {

        final List<String[]> resultList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                resultList.add(row);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        } finally

        {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }

        shelters = new ArrayList<>();


        for (int i = 1; i < resultList.size(); i++) {

           // String first = resultList.get(i)[6];
           // first = first.substring(1);
            //String second = resultList.get(i)[7];
            //String third = resultList.get(i)[8];
            //third = third.substring(0, resultList.get(i)[8].length() - 1);
            //String address = first + second + third;

            shelters.add(new Shelter(resultList.get(i)[1], resultList.get(i)[2], resultList.get(i)[3], resultList.get(i)[4], resultList.get(i)[5], resultList.get(i)[6], resultList.get(i)[7], resultList.get(i)[8]));

        }
        return shelters;
    }

}



