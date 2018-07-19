package com.codecool.queststore.controller;

import com.codecool.queststore.model.SingletonAcountContainer;
import com.codecool.queststore.model.inventory.Category;
import com.codecool.queststore.model.inventory.Item;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
class StudentControllerTest {

    @Mock
    private HttpExchange httpExchange;

    @Mock
    private Headers headers;

    private ByteArrayOutputStream byteArrayOutputStream;
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        initializeFields();
        SingletonAcountContainer.getInstance().addAccount("15de3c1d-21fd-4e31-9605-74a028cf1826", 1);
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        when(httpExchange.getRequestHeaders()).thenReturn(headers);
        when(headers.getFirst("Cookie")).thenReturn("sessionId=15de3c1d-21fd-4e31-9605-74a028cf1826");
        when(httpExchange.getResponseBody()).thenReturn(byteArrayOutputStream);
    }


    private void initializeFields() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        studentController = new StudentController();
    }

    @Test
    void shouldGetStudentMenu() throws IOException, URISyntaxException {
        setEnvironment("/student");

        String actualResult = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        String expectedResult = getExpectedResult("templates/menu-student.twig");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldGetProfileView() throws IOException, URISyntaxException {
        setEnvironment("/student-view");

        String actualResult = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        String expectedResult = getExpectedResult("templates/student-view-profile.twig");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldGetTransactionHistory() throws IOException, URISyntaxException {
        setEnvironment("transactions-history");

        String actualResult = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        String expectedResult = getExpectedResult("templates/transactions-student-view.twig");

        assertEquals(expectedResult, actualResult);
    }


    private void setEnvironment(String uri) throws IOException, URISyntaxException {
        when(httpExchange.getRequestURI()).thenReturn(new URI(uri));
        studentController.handle(httpExchange);
    }


    private String getExpectedResult(String templatePath) {
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel jtwigModel = JtwigModel.newModel();
        setTestHeader(jtwigModel);
        if (templatePath.contains("student-view"))
            setTestStudentView(jtwigModel);
        if (templatePath.contains("transactions-student"))
            setTestTransaction(jtwigModel);
        if (templatePath.contains("student-store"))
            setTestStore(jtwigModel);
        if (templatePath.contains("buy-artifact"))
            setTestArtifact(jtwigModel);
        return jtwigTemplate.render(jtwigModel);
    }

    private void setTestHeader(JtwigModel jtwigModel) {
        String fullName = "Eliza Golec";
        int coolcoins = 0;
        jtwigModel.with("fullname", fullName);
        jtwigModel.with("money", String.valueOf(coolcoins));
    }


    private void setTestStudentView(JtwigModel jtwigModel) {
        jtwigModel.with("firstname", "Eliza");
        jtwigModel.with("lastname", "Golec");
        jtwigModel.with("email", "email@gmail.com");
        jtwigModel.with("login", "eliza");
        jtwigModel.with("classname", "webRoom");
    }


    private void setTestTransaction(JtwigModel jtwigModel) {
        jtwigModel.with("transactions", new ArrayList<>());
        jtwigModel.with("totalamount", 0);
    }


    private void setTestStore(JtwigModel jtwigModel) {
        ArrayList<Item> artifacts = new ArrayList<>();
        artifacts.add(new Item(1, "name", "description", 25, new Category("single")));

        jtwigModel.with("artifacts", artifacts);
    }


    private void setTestArtifact(JtwigModel jtwigModel) {
        jtwigModel.with("name", "name");
        jtwigModel.with("description", "description");
        jtwigModel.with("price", 25);
    }
}