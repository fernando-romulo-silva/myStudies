package br.com.fernando.ch15_Blending_OOP_and_FP.part01_Introduction_to_Scala;

import java.util.Optional;

class Car {
    
    private Insurance insurance;

    // A car might or might not be insured, so you declare this get return a Optional
    public Optional<Insurance> getInsurance() {
        return Optional.ofNullable(insurance);
    }

    public void setInsurance(final Insurance insurance) {
        this.insurance = insurance;
    }
}
