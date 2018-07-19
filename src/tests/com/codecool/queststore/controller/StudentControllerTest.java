package com.codecool.queststore.controller;

import com.codecool.queststore.model.SingletonAcountContainer;
import com.codecool.queststore.model.user.UserDetails;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
class StudentControllerTest {

    @Mock
    private HttpExchange httpExchange;

    @Mock
    private Headers headers;

    @Mock
    private JtwigTemplate jtwigTemplate;

    private ByteArrayOutputStream byteArrayOutputStream;
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        byteArrayOutputStream = new ByteArrayOutputStream();
        studentController = new StudentController();
        SingletonAcountContainer.getInstance().addAccount("15de3c1d-21fd-4e31-9605-74a028cf1826", 1);
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        when(httpExchange.getRequestHeaders()).thenReturn(headers);
        when(headers.getFirst("Cookie")).thenReturn("sessionId=15de3c1d-21fd-4e31-9605-74a028cf1826");
        when(httpExchange.getResponseBody()).thenReturn(byteArrayOutputStream);
    }

    @Test
    void shouldGetStudentMenu() throws IOException, URISyntaxException {
        when(httpExchange.getRequestURI()).thenReturn(new URI("/student"));
        studentController.handle(httpExchange);
        String actualResult = new String(byteArrayOutputStream.toByteArray(),StandardCharsets.UTF_8 );
        String expectedResult = getExpectedResult("templates/menu-student.twig");//new String(getExpectedResult("templates/menu-student.twig"), StandardCharsets.UTF_8);

        assertEquals(expectedResult, actualResult);
    }


    private String getExpectedResult(String templatePath) {
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel jtwigModel = JtwigModel.newModel();
        setTestHeader(jtwigModel);
        return jtwigTemplate.render(jtwigModel).toString();
    }

    private void setTestHeader(JtwigModel jtwigModel) {
        String fullName = "Eliza Golec";
        int coolcoins = 0;
        jtwigModel.with("fullname", fullName);
        jtwigModel.with("money", String.valueOf(coolcoins));
    }
}