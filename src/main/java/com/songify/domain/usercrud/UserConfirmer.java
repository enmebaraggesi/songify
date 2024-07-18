package com.songify.domain.usercrud;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConfirmer {
    
    private final MailSender mailSender;
    private final UserRepository userRepository;
    
    void sendConfirmationEmail(final User createdUser) {
        String email = createdUser.getEmail();
        String subject = "Complete your registration";
        String text = "To confirm your account, please click here: " +
                "http://localhost:8080/users/confirm?token=" + createdUser.getConfirmationToken();
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
    
    @Transactional
    public boolean confirmUser(final String confirmationToken) {
        User user = userRepository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new RuntimeException("Invalid confirmation token"));
        return user.confirm();
    }
}
