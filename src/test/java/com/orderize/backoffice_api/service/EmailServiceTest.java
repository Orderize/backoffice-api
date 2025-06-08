package com.orderize.backoffice_api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    private EmailService emailService;

    private final String TEST_TO_EMAIL = "recipient@example.com";
    private final String TEST_NEW_PASSWORD = "secure_password_123";
    private final String TEST_FROM_EMAIL = "no-reply@your-app.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emailService = new EmailService(mailSender, TEST_FROM_EMAIL);
    }

    @Test
    @DisplayName("Deve enviar e-mail com sucesso para a nova senha")
    void shouldSendEmailSuccessfully() {
        emailService.sendGeneratedPasswordEmail(TEST_TO_EMAIL, TEST_NEW_PASSWORD);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertNotNull(sentMessage);
        assertEquals(TEST_FROM_EMAIL, sentMessage.getFrom());
        assertArrayEquals(new String[]{TEST_TO_EMAIL}, sentMessage.getTo());
        assertEquals("Sua Nova Senha - Seu Aplicativo", sentMessage.getSubject());
        assertTrue(sentMessage.getText().contains(TEST_NEW_PASSWORD));
        assertTrue(sentMessage.getText().contains("Olá,"));
        assertTrue(sentMessage.getText().contains("Sua nova senha é: " + TEST_NEW_PASSWORD));
    }

    @Test
    @DisplayName("Deve lidar com exceção ao enviar e-mail e não propagar")
    void shouldHandleExceptionAndNotPropagate() {

        doThrow(new MailSendException("Falha na conexão SMTP"))
                .when(mailSender).send(any(SimpleMailMessage.class));

        assertDoesNotThrow(() -> {
            emailService.sendGeneratedPasswordEmail(TEST_TO_EMAIL, TEST_NEW_PASSWORD);
        });

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("Deve lançar IllegalStateException se mailSender for null (se a verificação for mantida)")
    void shouldThrowIllegalStateExceptionIfMailSenderIsNull() {

        emailService = new EmailService(null, TEST_FROM_EMAIL);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            emailService.sendGeneratedPasswordEmail(TEST_TO_EMAIL, TEST_NEW_PASSWORD);
        });

        assertEquals("JavaMailSender bean is null. Cannot send email.", exception.getMessage());

        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }
}