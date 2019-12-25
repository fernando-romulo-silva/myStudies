package org.nandao.cap01.p4catching_multiple_exception_types_improve_type_checking;

public class InvalidParameter extends java.lang.Exception {

    private static final long serialVersionUID = 1L;

    public InvalidParameter() {
        super("Invalid Parameter");
    }
}
