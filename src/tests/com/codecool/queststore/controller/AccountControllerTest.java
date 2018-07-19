package com.codecool.queststore.controller;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.io.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {
    String inexTwigContent;

    @Mock
    HttpExchange httpExchange;

    @Mock
    Headers headers;

    private void setUp() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/elzbietakrzych/Documents/codecool/DBSQL/2018_07_16_TW/quest-store/src/tests/com/codecool/queststore/controller/testIndexTwig.txt"));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            this.inexTwigContent = stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    @Test
    public void shouldSendIndexTwig() throws Exception{
        setUp();
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        when(httpExchange.getRequestHeaders()).thenReturn(headers);
        when(headers.getFirst("Cookie")).thenReturn("sessionId =\"1111\"; Idea-34189a9a=bf4bc27e-06aa-4d08-b747-49f205c05a34");
        OutputStream os = new ByteArrayOutputStream();
        when(httpExchange.getResponseBody()).thenReturn(os);
        new AccountController().handle(httpExchange);

        assertEquals(os.toString(), inexTwigContent);
    }


}