package br.com.fernando.core;

import java.util.ArrayList;
import java.util.Collection;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public enum AlgoritmoValidacaoCPF {

    MODULO11 {
        @Override
        public boolean eValido(final String valor) {
            final CPFValidator cpfValidator = new CPFValidator(false);
            try {
                cpfValidator.assertValid(valor);
                return true;
            } catch (final InvalidStateException invalidStateException) {
                return false;
            }
        }
    },

    RECEITA {

        private final Collection<String> valoresValidos = new ArrayList<String>();

        {
            valoresValidos.add("53389399321");
            valoresValidos.add("64573128530");
            valoresValidos.add("53783947677");
            valoresValidos.add("79780901671");
            valoresValidos.add("75538117774");
        }

        @Override
        public boolean eValido(final String valor) {
            return valoresValidos.contains(valor);
        }
    },

    TODOS {
        @Override
        public boolean eValido(final String valor) {
            return MODULO11.eValido(valor) && RECEITA.eValido(valor);
        }
    };

    public abstract boolean eValido(String valor);

}
