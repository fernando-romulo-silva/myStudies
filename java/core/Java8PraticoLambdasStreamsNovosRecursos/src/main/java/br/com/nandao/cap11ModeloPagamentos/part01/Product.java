package br.com.nandao.cap11ModeloPagamentos.part01;

import java.math.BigDecimal;
import java.nio.file.Path;

public class Product {
    private final String name;
    private final Path file;
    private final BigDecimal price;

    public Product(String name, Path file, BigDecimal price) {
        this.name = name;
        this.file = file;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public Path getFile() {
        return this.file;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
