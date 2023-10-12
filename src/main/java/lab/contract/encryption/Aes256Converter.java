package lab.contract.encryption;

import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class Aes256Converter implements AttributeConverter<String, String> {
    private final Aes256Utils aes256Utils;

    public Aes256Converter() {
        this.aes256Utils = new Aes256Utils();
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {  // 암호화
        if (!StringUtils.hasText(attribute)) {
            return attribute;
        }
        try {
            return aes256Utils.encrypt(attribute);
        } catch (Exception e) {
            throw new RuntimeException("failed to encrypt data", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {  // 복호화
        try {
            return aes256Utils.decrypt(dbData);
        } catch (Exception e) {
            return dbData;
        }
    }
}
