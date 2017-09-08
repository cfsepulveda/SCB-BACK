package com.artsoft.scb.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.artsoft.scb.model.bll.OffererService;
import com.artsoft.scb.model.entity.Offerer;
import com.artsoft.scb.model.entity.OffererState;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = OffererController.class, secure = true)
public class OffererControllerTest extends BaseControllerTest {

	@Autowired
    private MockMvc mvc;
 
    @MockBean
    private OffererService offererService;
 
    @Autowired 
    private ObjectMapper mapper;
    
    @Test
    public void CreateOferent_whenCreateOferent_thenReturnOk()
      throws Exception {
         
    	Offerer alex = new Offerer();
    	alex.setEmail("alex@hotmail.com");    
    	alex.setNit("1014207");
    	alex.setName("Icetex");	    	
        alex.setPassword("Hola");
    
        Mockito.when(
        		offererService.createOferent(alex)).thenReturn(true);
        String json = mapper.writeValueAsString(alex);
        mvc.perform(post("/offerer")
        		.accept(MediaType.APPLICATION_JSON)
        		.content(json)
        		.contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }
    
    @Test
    public void GetAllOferents_whenGetAllOferents_thenReturnOk()
      throws Exception {
         
    	List<Offerer> oferents = new ArrayList<Offerer>();
    	Offerer oferentOne = new Offerer();
		oferentOne.setName("Offerer one");
		oferentOne.setNit("123456");
		oferents.add(oferentOne);
		
		Mockito.when(offererService.getAllOferents()).thenReturn(oferents);
		
        mvc.perform(get("/offerer")
        		.accept(MediaType.APPLICATION_JSON)
        		.contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(roles="OFFERER")
    public void GetAllPendingOfferers_whenGetAllPendingOfferers_thenReturnOk()
      throws Exception {
         
    	OffererState state = new OffererState();
    	state.setId(1);
    	state.setDescription("Pendiente");
    	
    	List<Offerer> oferents = new ArrayList<Offerer>();
    	Offerer oferentOne = new Offerer();
		oferentOne.setName("Offerer one");
		oferentOne.setNit("123456");
		oferentOne.setOffererState(state);
		oferents.add(oferentOne);
		
		Mockito.when(offererService.getPendingOferents()).thenReturn(oferents);
		
        mvc.perform(get("/offerer/pending")
        		.accept(MediaType.APPLICATION_JSON)
        		.contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(roles="OFFERER")
    public void GetAllApprovedOfferers_whenGetAllApprovedOfferers_thenReturnOk()
      throws Exception {
         
    	OffererState state = new OffererState();
    	state.setId(2);
    	state.setDescription("Aprovado");
    	
    	List<Offerer> oferents = new ArrayList<Offerer>();
    	Offerer oferentOne = new Offerer();
		oferentOne.setName("Offerer one");
		oferentOne.setNit("123456");
		oferentOne.setOffererState(state);
		oferents.add(oferentOne);
		
		Mockito.when(offererService.getApprovedOferents()).thenReturn(oferents);
		
        mvc.perform(get("/offerer/approved")
        		.accept(MediaType.APPLICATION_JSON)
        		.contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(roles="OFFERER")
    public void GetAllRejectedOfferers_whenGetAllRejectedOfferers_thenReturnOk()
      throws Exception {
         
    	OffererState state = new OffererState();
    	state.setId(3);
    	state.setDescription("Rechazado");
    	
    	List<Offerer> oferents = new ArrayList<Offerer>();
    	Offerer oferentOne = new Offerer();
		oferentOne.setName("Offerer one");
		oferentOne.setNit("123456");
		oferentOne.setOffererState(state);
		oferents.add(oferentOne);
		
		Mockito.when(offererService.getApprovedOferents()).thenReturn(oferents);
		
        mvc.perform(get("/offerer/rejected")
        		.accept(MediaType.APPLICATION_JSON)
        		.contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }
}

