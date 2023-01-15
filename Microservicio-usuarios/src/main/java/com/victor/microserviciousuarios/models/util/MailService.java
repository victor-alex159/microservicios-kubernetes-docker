package com.victor.microserviciousuarios.models.util;

import com.victor.microserviciousuarios.models.response.GenericResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;

import java.io.File;
import java.util.List;

public interface MailService {

    void enviarEmail(String emailTo, String subject, String text, String bodyHtml) throws Exception;

    void enviarEmail(String emailTo, String subject, String text, String bodyHtml, List<String> fileNames,
                     List<File> listFiles) throws Exception;

    void enviarEmail2(String emailTo, String subject, String text, String bodyHtml, List<String> fileNames,
                 List<File> listFiles) throws Exception;

    GenericResponse<Object> enviarEmail3(String emailTo, String subject, String text, String bodyHtml, List<String> fileNames,
                                 List<ByteArrayResource> listFiles) throws Exception;

}
