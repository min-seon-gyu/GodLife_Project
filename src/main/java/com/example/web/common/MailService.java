package com.example.web.common;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final JavaMailSender javaMailSender;
    private static final int MAX_RETRIES = 3;

    public void findId(String email, String id) {
        sendEmailFindIdAsync(email, id, MAX_RETRIES);
    }

    private void sendEmailFindIdAsync(String email, String id, int maxRetries) {
        CompletableFuture.runAsync(() -> sendEmailFindId(email, id), executorService)
                .handle((result, e) -> {
                    if(e == null){
                        log.info("메일 API 요청이 성공하였습니다.");
                        return result;
                    }
                    else{
                        return retry(() -> sendEmailFindId(email, id), maxRetries);
                    }
                });
    }

    public void findPassword(String email, String password) {
        sendEmailFindPasswordAsync(email, password, MAX_RETRIES);
    }

    private void sendEmailFindPasswordAsync(String email, String password, int maxRetries) {
        CompletableFuture.supplyAsync(() -> sendEmailFindPassword(email, password), executorService)
                .handle((result, e) -> {
                    if(e == null){
                        log.info("메일 API 요청이 성공하였습니다.");
                        return result;
                    }
                    else{
                        return retry(() -> sendEmailFindPassword(email, password), maxRetries);
                    }
                });
    }

    public void signUp(String email, String key) {
        sendEmailSignUpAsync(email, key, MAX_RETRIES);
    }

    private void sendEmailSignUpAsync(String email, String key, int maxRetries) {
        CompletableFuture.supplyAsync(() -> sendEmailSignUp(email, key), executorService)
                .handle((result, e) -> {
                    if(e == null){
                        log.info("메일 API 요청이 성공하였습니다.");
                        return result;
                    }
                    else{
                        return retry(() -> sendEmailSignUp(email, key), maxRetries);
                    }
                });
    }

    private boolean sendEmailFindId(String email, String id){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("갓생 사이트 아이디 찾기");
            message.setText("아이디는 " + id + "입니다.");
            javaMailSender.send(message);
            return true;
        }catch(MailException e){
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "메일 API 요청 오류가 발생하였습니다.");
        }
    }

    private boolean sendEmailFindPassword(String email, String password){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("갓생 사이트 비밀번호 찾기");
            message.setText("임시 비밀번호는 " + password + "입니다.");
            javaMailSender.send(message);
            return true;
        }catch(MailException e){
            log.info("메일 API 요청 오류가 발생하였습니다.");
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "메일 API 요청 오류가 발생하였습니다.");
        }
    }

    private boolean sendEmailSignUp(String email, String key){
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setSubject("갓생 사이트 회원가입 인증", "UTF-8");
            String htmlContent = "<h1>메일인증</h1>" +
                    "<br/>갓생 가입을 축하드립니다. 하단 이메일 인증 확인 버튼을 통해 가입을 완료해주세요!"+
                    "<br/><a href='http://localhost:8080/member/" + key +
                    "' target='_blenk'>이메일 인증 확인</a>";
            message.setText(htmlContent, "UTF-8", "html");
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            javaMailSender.send(message);
            return true;
        }catch (MessagingException e) {
            log.info("메일 API 요청 오류가 발생하였습니다.");
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "메일 API 요청 오류가 발생하였습니다.");
        } catch (MailException e) {
            log.info("메일 API 요청 오류가 발생하였습니다.");
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "메일 API 요청 오류가 발생하였습니다.");
        }
    }

    private boolean retry(Runnable task, int retries) {
        for (int i = 1; i <= retries; i++) {
            try {
                task.run();
                return true;
            } catch (Exception e) {
                log.info("{}번 째 재시도가 실패하였습니다.", i);
            }
        }
        return false;
    }
}
