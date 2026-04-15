package dev._xdbe.booking.creelhouse.infrastructure.persistence;


import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter
public class CreditCardConverter implements AttributeConverter<String, String> {

    @Autowired
    private CryptographyHelper cryptographyHelper;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        // Step 7a: Encrypt the PAN before storing it in the database
        try {
            return cryptographyHelper.encryptData(attribute);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Step 7a: End of PAN encryption
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        // Step 7b: Decrypt the PAN when reading it from the database
        String pan = cryptographyHelper.decryptData(dbData);
        // Step 7b: End of PAN decryption
        String maskedPanString = panMasking(pan);
        return maskedPanString;
    }

    private String panMasking(String pan) {
        if (pan == null || pan.length() < 8) {
        return pan;
    }

    String first4 = pan.substring(0, 4);
    String last4 = pan.substring(pan.length() - 4);

    StringBuilder masked = new StringBuilder();
    for (int i = 0; i < pan.length() - 8; i++) {
        masked.append("*");
    }

    return first4 + masked + last4;
    }

    
}
