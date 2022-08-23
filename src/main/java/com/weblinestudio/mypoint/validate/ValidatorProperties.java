package com.weblinestudio.mypoint.validate;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Component
@Data
public class ValidatorProperties {

    private String validatorMessageErrorPasswordConfirm;
    private String validatorMessageErrorPasswordLength;
    private String validatorMessageErrorPasswordPattern;
    private String validatorMessageErrorUserIsExist;
    private String validatorMessageErrorUserLength;
    private String validatorMessageErrorUserPattern;

    private String validatorMessageErrorPhone;

    private String validatorPatternPassword;
    private String validatorPatternUsername;
    private String validatorPatternPhone;

    private int validatorPatternPasswordMax;
    private int validatorPatternPasswordMin;
    private int validatorPatternLoginMin;
    private int validatorPatternLoginMax;

    public ValidatorProperties() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("values.yaml");
        Map<String, Object> map = yaml.load(inputStream);

        init(map);
    }

    private void init(Map<String, Object> map) {
        this.validatorMessageErrorPasswordConfirm = (String) map.get("validator.message.errorPasswordConfirm");
        this.validatorMessageErrorPasswordLength = (String) map.get("validator.message.errorPasswordLength");
        this.validatorMessageErrorPasswordPattern = (String) map.get("validator.message.errorPasswordPattern");
        this.validatorMessageErrorUserIsExist = (String) map.get("validator.message.errorUserIsExist");
        this.validatorMessageErrorUserLength = (String) map.get("validator.message.errorUserLength");
        this.validatorMessageErrorUserPattern = (String) map.get("validator.message.errorUserPattern");
        this.validatorPatternPassword = (String) map.get("validator.pattern.password");
        this.validatorPatternUsername = (String) map.get("validator.pattern.username");
        this.validatorPatternPasswordMax = (int) map.get("validator.pattern.passwordMax");
        this.validatorPatternPasswordMin = (int) map.get("validator.pattern.passwordMin");
        this.validatorPatternLoginMin = (int) map.get("validator.pattern.loginMin");
        this.validatorPatternLoginMax = (int) map.get("validator.pattern.loginMax");
        this.validatorPatternPhone = (String) map.get("validator.pattern.phone");
        this.validatorMessageErrorPhone = (String) map.get("validator.message.errorPhone");
    }
}
