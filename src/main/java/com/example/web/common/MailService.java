package com.example.web.common;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public void findId(String email, String id){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("갓생 사이트 아이디 찾기");
            message.setText("아이디는 " + id + "입니다.");
            javaMailSender.send(message);
        }catch(MailException e){
            e.printStackTrace();
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "메일 API 요청 오류가 발생하였습니다.");
        }
    }

    public void findPassword(String email, String password){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("갓생 사이트 비밀번호 찾기");
            message.setText("임시 비밀번호는 " + password + "입니다.");
            javaMailSender.send(message);
        }catch(MailException e){
            e.printStackTrace();
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "메일 API 요청 오류가 발생하였습니다.");
        }
    }

    public void signUp(String email, String key){
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
        }catch (MessagingException e) {
            e.printStackTrace();
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "메일 API 요청 오류가 발생하였습니다.");
        } catch (MailException e) {
            e.printStackTrace();
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "메일 API 요청 오류가 발생하였습니다.");
        }
    }
}
