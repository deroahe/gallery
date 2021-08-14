package com.deroahe.gallerybe.service.impl;

import com.deroahe.gallerybe.service.EncryptionService;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service("encryptionService")
public class EncryptionServiceImpl implements EncryptionService {

    @Value("${jasypt.encryptor.password}")
    private String password;
    public static final String ALGORITHM = "PBEWITHHMACSHA512ANDAES_256";
    public static final String KEY_OBTENTION_ITERATIONS = "1000";
    public static final String POOL_SIZE = "1";
    public static final String PROVIDER_NAME = "SunJCE";
    public static final String RANDOM_SALT_GENERATOR_CLASS_NAME = "org.jasypt.salt.RandomSaltGenerator";
    public static final String IV_GENERATOR_CLASS_NAME = "org.jasypt.iv.RandomIvGenerator";
    public static final String STRING_OUTPUT_TYPE = "base64";
    private StringEncryptor stringEncryptor;

    @PostConstruct
    public void init() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm(ALGORITHM);
        config.setKeyObtentionIterations(KEY_OBTENTION_ITERATIONS);
        config.setPoolSize(POOL_SIZE);
        config.setProviderName(PROVIDER_NAME);
        config.setSaltGeneratorClassName(RANDOM_SALT_GENERATOR_CLASS_NAME);
        config.setIvGeneratorClassName(IV_GENERATOR_CLASS_NAME);
        config.setStringOutputType(STRING_OUTPUT_TYPE);
        encryptor.setConfig(config);
        stringEncryptor = encryptor;
    }

    @Override
    public String encrypt(String value) {
        return stringEncryptor.encrypt(value);
    }

    @Override
    public String decrypt(String value) {
        return stringEncryptor.decrypt(value);
    }
}
