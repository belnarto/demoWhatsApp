package com.example.demowhatsapp.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageSender {

    private static final String SENDER_PHONE_NUMBER_ID = "110627518561121";
    private static final String SEND_MESSAGE_URL =
        "https://graph.facebook.com/v15.0/%s/messages".formatted(SENDER_PHONE_NUMBER_ID);
    private static final String NO_TEMPLATE_MESSAGE = """
            {
            	"messaging_product": "whatsapp",
            	"to": "%s",
            	"text": {
            		"body": "%s"
            	}
            }
        """;
    private static final String HELLO_WORLD_MESSAGE_TEMPLATE = """
            {
                "messaging_product": "whatsapp",
                "to": "%s",
                "type": "template",
                "template": {
                    "name": "hello_world",
                    "language": {
                        "code": "en_US"
                    }
                }
            }
        """;
    private static final String INTERACTIVE_MESSAGE_TEMPLATE = """
            {
              "messaging_product": "whatsapp",
              "recipient_type": "individual",
              "to": "%s",
              "type": "interactive",
              "interactive": {
                "type": "list",
                "header": {
                  "type": "text",
                  "text": "HEADER_TEXT"
                },
                "body": {
                  "text": "BODY_TEXT"
                },
                "footer": {
                  "text": "FOOTER_TEXT"
                },
                "action": {
                  "button": "BUTTON_TEXT",
                  "sections": [
                    {
                      "title": "SECTION_1_TITLE",
                      "rows": [
                        {
                          "id": "SECTION_1_ROW_1_ID",
                          "title": "SECTION_1_ROW_1_TITLE",
                          "description": "SECTION_1_ROW_1_DESCRIPTION"
                        },
                        {
                          "id": "SECTION_1_ROW_2_ID",
                          "title": "SECTION_1_ROW_2_TITLE",
                          "description": "SECTION_1_ROW_2_DESCRIPTION"
                        }
                      ]
                    },
                    {
                      "title": "SECTION_2_TITLE",
                      "rows": [
                        {
                          "id": "SECTION_2_ROW_1_ID",
                          "title": "SECTION_2_ROW_1_TITLE",
                          "description": "SECTION_2_ROW_1_DESCRIPTION"
                        },
                        {
                          "id": "SECTION_2_ROW_2_ID",
                          "title": "SECTION_2_ROW_2_TITLE",
                          "description": "SECTION_2_ROW_2_DESCRIPTION"
                        }
                      ]
                    }
                  ]
                }
              }
            }
        """;
    private static final String TOKEN = "EAAJxQ8trhBkBALiSxElCroGNZBuYlAJXTaMS6llhG8g98ApbVfC0fFqZBzWF6PE5jZADUnbGCnyhhsZC2k67AWJQSHuLLBrjVvIyNm81Q0U6sd3vDx5dmezMOQkQFeb7rrfLMijzgC7n4u7ZCqFZBB3DlDQW4CIjQUrL7k4QPbzYdYahybIKxyCQsWkkqp2kJk8ZALaApcdogZDZD";

    private final RestTemplate restTemplate;

    @PostConstruct
    public void test() {
        sendMessageWithoutTemplate("37580291442000", "hi there");
    }

    @SneakyThrows
    public void sendMessageWithoutTemplate(String recipientNumber, String content) {
        String messageJson = NO_TEMPLATE_MESSAGE.formatted(recipientNumber, content);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(TOKEN);

        HttpEntity<String> request = new HttpEntity<>(messageJson, headers);

        String response = restTemplate.postForObject(SEND_MESSAGE_URL, request, String.class);

        log.info("Send custom message without template to {} response is {}", recipientNumber, response);
    }

    @SneakyThrows
    public void sendHelloWorldMessage(String recipientNumber) {
        String messageJson = HELLO_WORLD_MESSAGE_TEMPLATE.formatted(recipientNumber);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(TOKEN);

        HttpEntity<String> request = new HttpEntity<>(messageJson, headers);

        String response = restTemplate.postForObject(SEND_MESSAGE_URL, request, String.class);

        log.info("Send hello world message to {} response is {}", recipientNumber, response);
    }

    @SneakyThrows
    public void sendInteractiveMessage(String recipientNumber) {
        String messageJson = INTERACTIVE_MESSAGE_TEMPLATE.formatted(recipientNumber);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(TOKEN);

        HttpEntity<String> request = new HttpEntity<>(messageJson, headers);

        String response = restTemplate.postForObject(SEND_MESSAGE_URL, request, String.class);

        log.info("Send interactive message to {} response is {}", recipientNumber, response);
    }
}
