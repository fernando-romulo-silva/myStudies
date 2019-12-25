package org.nandao.cap01.p3try_catach_resource;

public class FirstAutoCloseableResource implements AutoCloseable {
    @Override
    public void close() throws Exception {
        // Close the resource as appropriate
        System.out.println("FirstAutoCloseableResource close method executed");
        throw new UnsupportedOperationException("A problem has occurred inFirstAutoCloseableResource");
    }

    public void manipulateResource() {
        // Perform some resource specific operation
        System.out.println("FirstAutoCloseableResource manipulateResource method executed");
    }
}
