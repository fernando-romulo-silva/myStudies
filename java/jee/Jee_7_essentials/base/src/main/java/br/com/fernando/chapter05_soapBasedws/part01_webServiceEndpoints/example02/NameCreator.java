package br.com.fernando.chapter05_soapBasedws.part01_webServiceEndpoints.example02;

import java.util.ArrayList;
import java.util.Arrays;

import javax.jws.WebService;

@WebService
public class NameCreator implements NameCreatorEndpoint {

    @Override
    public String createName() {
        return "Some name";
    }

    @Override
    public NamesListElement createNames() {
        final ArrayList<String> result = new ArrayList<>();

        result.addAll(Arrays.asList("Name 01", "Name 02"));

        return new NamesListElement(result);
    }
}
