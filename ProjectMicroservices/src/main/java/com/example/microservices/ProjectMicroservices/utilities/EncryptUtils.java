package com.example.microservices.ProjectMicroservices.utilities;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Component;

//Component bcoz it can create instance and we can use Autowired to inject object
@Component
public class EncryptUtils {
    //attribute from jasypt library
    private BasicTextEncryptor textEncryptor;


    /* constructor */
    public EncryptUtils()
    {
        textEncryptor = new BasicTextEncryptor();
// Encryption keys
        textEncryptor.setPassword("mySecretEncriptionKeyBlaBla1234");
    }


    public String encrypt(String data)
    {
        return textEncryptor.encrypt(data);
    }

    public String decrypt(String encriptedData)
    {
        return textEncryptor.decrypt(encriptedData);
    }
    }
