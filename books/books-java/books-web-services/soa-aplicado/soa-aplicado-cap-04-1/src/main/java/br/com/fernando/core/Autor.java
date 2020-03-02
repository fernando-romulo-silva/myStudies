package br.com.fernando.core;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.methods.GetMethod;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "autor")
public class Autor {

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

    @XmlElementWrapper(name = "refs")
    @XmlElement(name = "ref")
    // Realiza uma busca no Google pelas referências àquele autor.
    public List<URL> getRefs() throws HttpException, IOException {

        final String autor = URLEncoder.encode(nome, "UTF-8");
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

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    // Mas e se, por acaso, não quiséssemos trafegar, nunca, o atributo dataNascimento da classe Autor?
    // Usamos a anotacao '@XmlTransient'
    @XmlTransient
    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(final Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
