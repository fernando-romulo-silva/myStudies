package br.com.fernando.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.fernando.core.rest.Link;
import br.com.fernando.core.rest.RESTEntity;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Usuarios implements RESTEntity {

    @XmlElement(name = "usuario")
    private Collection<Usuario> usuarios;

    @XmlElement(name = "link")
    private Collection<Link> links;

    public Usuarios() {
        super();
    }

    public Usuarios(final Collection<Usuario> usuarios, final Link... links) {
        this.usuarios = usuarios;
        this.links = new ArrayList<>(Arrays.asList(links));
    }

    @Override
    public void adicionarLink(final Link link) {
        if (links == null) {
            links = new ArrayList<>();
        }
        links.add(link);
    }

    public Collection<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(final Collection<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
