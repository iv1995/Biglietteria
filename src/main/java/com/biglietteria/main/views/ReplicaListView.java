package com.biglietteria.main.views;

import com.biglietteria.main.security.SecurityService;
import com.biglietteria.main.services.BigliettiService;
import com.biglietteria.main.services.Replica;
import com.biglietteria.main.services.ReplicaService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.security.PermitAll;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Application")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Menu")
@PermitAll
class ReplicaListView extends Main {
	
	String tmp;
	
	@Autowired
	private final SecurityService securityService;
	
	@Autowired
	private ReplicaService replicaService;
	@Autowired
	private BigliettiService bigliettiService;
	
    final Grid<Replica> replicaGrid;
    
    ReplicaListView(ReplicaService replciaservice, SecurityService securityService) {
        this.replicaService = replicaService;
        this.securityService = securityService;

        replicaGrid = new Grid<>();
        replicaGrid.setItems(query -> replicaService.list(toSpringPageRequest(query)).stream());
        replicaGrid.addColumn(Replica::getCodiceReplica).setHeader("Codice Replica");
        replicaGrid.addColumn(Replica::getDataReplica).setHeader("Data Replica");
        replicaGrid.addComponentColumn(replica -> {
        	
        	HorizontalLayout hl = new HorizontalLayout();
        	
        	TextArea text = new TextArea("Text");
        	text.setMaxRows(1);
        	Button b = new Button("Buy",  e -> {bigliettiService.buyTicket(LocalDate.now().toString() + securityService.getAuthenticatedUser().getUsername(), 
        																   securityService.getAuthenticatedUser().getUsername(), 
        																   replica.getCodiceReplica(), 
        																   replica.getDataReplica(), 
        																   "credit card", 
        																   Integer.valueOf(text.getValue()));}); 
        	
        	System.out.println(replica.getCodiceReplica());
        	
        	hl.add(text);
        	hl.add(b);
        	
        	return hl;
        });
        
        replicaGrid.setSizeFull();

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);
        
        add(replicaGrid);
    }
    
    private void deleteReplica(String id) {
    	replicaService.deleteById(id);
    	replicaGrid.getDataProvider().refreshAll();
    }

}
