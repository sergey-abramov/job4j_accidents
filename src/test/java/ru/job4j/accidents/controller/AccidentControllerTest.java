package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentService accidents;

    @Test
    @WithMockUser
    void viewCreateAccident() throws Exception {
        this.mockMvc.perform(get("/addAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"));
    }

    @Test
    @WithMockUser
    void viewEditAccident() throws Exception {
        this.mockMvc.perform(get("/editAccident/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("editAccident"));
    }

    @Test
    @WithMockUser
    void saveAccident() throws Exception {
        this.mockMvc.perform(post("/saveAccident")
                        .param("name", "Наезд")
                        .param("text", "Наезд на пешехода")
                        .param("address", "проспект Мира 32")
                        .param("type.id", "2")
                        .param("rIds", "1, 2"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argumentCaptor = ArgumentCaptor.forClass(Accident.class);
        verify(accidents).add(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName()).isEqualTo("Наезд");
        assertThat(argumentCaptor.getValue().getText()).isEqualTo("Наезд на пешехода");
    }

    @Test
    @WithMockUser
    void updateAccident() throws Exception {
        this.mockMvc.perform(post("/updateAccident")
                        .param("name", "Наезд")
                        .param("text", "Наезд на пешехода")
                        .param("address", "проспект Мира 32")
                        .param("type.id", "2")
                        .param("rIds", "1, 2"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argumentCaptor = ArgumentCaptor.forClass(Accident.class);
        verify(accidents).update(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName()).isEqualTo("Наезд");
        assertThat(argumentCaptor.getValue().getText()).isEqualTo("Наезд на пешехода");
    }
}