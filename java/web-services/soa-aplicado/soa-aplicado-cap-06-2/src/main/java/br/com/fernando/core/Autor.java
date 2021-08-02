package br.com.fernando.core;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.methods.GetMethod;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Date dataNascimento;

    public Autor() {
        super();
    }

    public Autor(final String nome, final Date dataNascimento) {
        super();
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public List<URL> refs() throws HttpException, IOException {

        final String autor = nome.replace(" ", "+");
        final String searchString = new StringBuilder("/ajax/services/search/web?v=1.0&q=%22").append(autor).append("%22").toString();

        final GetMethod getMethod = new GetMethod(searchString);
        final HttpState httpState = new HttpState();
        final HttpConnection httpConnection = new HttpConnection("ajax.googleapis.com", 80);
        httpConnection.open();
        getMethod.setFollowRedirects(true);
        final int result = getMethod.execute(httpState, httpConnection);

        if (result == 200) {
            final List<URL> responseList = new ArrayList<>();
            final JSONObject jsonObject = JSONObject.fromObject(getMethod.getResponseBodyAsString());
            final JSONArray results = jsonObject.getJSONObject("responseData").getJSONArray("results");
            for (int i = 0; i < results.size(); i++) {
                final String urlCrua = results.getJSONObject(i).getString("unescapedUrl");
                final URL url = new URL(urlCrua);
                responseList.add(url);
            }
            return responseList;
        }

        return Collections.emptyList();

    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(final Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
