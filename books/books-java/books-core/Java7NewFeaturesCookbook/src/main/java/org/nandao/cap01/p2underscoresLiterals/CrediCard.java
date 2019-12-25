package org.nandao.cap01.p2underscoresLiterals;

// The only purpose of the underscore is to make the code more readable to the developer. The
// compiler ignores the underscores during code generation and during any subsequent variable
// manipulation. Consecutive underscores are treated as one and also ignored by the compiler.
// If the output format of a variable is important, it will have to be handled separately.
// page 16
public class CrediCard {

    public static void main(final String[] args) {

        final long debitCard = 1234_5678_9876_5432L;

        System.out.println("The card number is: " + debitCard);
        System.out.print("The formatted card number is:");

        printFormatted(debitCard);

        final float minAmount = 5_000F;

        final float currentAmount = 5_250F;

        final float withdrawalAmount = 500F;

        if (currentAmount - withdrawalAmount < minAmount) {
            System.out.println("Minimum amount limit exceeded " + minAmount);
        }

        // Invalid forms
        // final long productKey=_12345_67890_09876_54321L;
        // final long licenseNumber=123_456_789_ L;
        // final float pi=3._ 14_15F;

        // hexadecimal literals
        final int commandInHex = 0xE_23D5_8C_7;
        final int commandInBinary = 0b1110_0010001111010101_10001100_0111;
        //
        System.out.println("commandInHex: " + Integer.toBinaryString(commandInHex));
        System.out.println("commandInBinary: " + Integer.toBinaryString(commandInBinary));

        // binary literals
        final byte initializationSequence = (byte) 0b10_110_010;
        final byte inputValue = (byte) 0b101_11011;

        // operations with binay literals
        final byte result = (byte) (inputValue & (byte) 0b000_11111);
        //
        System.out.println("initializationSequence: " + Integer.toBinaryString(initializationSequence));
        System.out.println("result: " + Integer.toBinaryString(result));

    }

    private static void printFormatted(final long cardNumber) {
        final String formattedNumber = Long.toString(cardNumber);

        for (int i = 0; i < formattedNumber.length(); i++) {
            if (i % 4 == 0) {
                System.out.print(" ");
            }

            System.out.print(formattedNumber.charAt(i));
        }

        System.out.println();
    }
}
