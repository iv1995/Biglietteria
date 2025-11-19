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
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.security.PermitAll;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.springframework.beans.factory.annotation.Autowired;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

@Route(value = "/public")
@PageTitle("Application")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Menu")
@PermitAll
@AnonymousAllowed
class PublicListView extends Main {
	
	@Autowired
	private ReplicaService replicaService;
	@Autowired
	private BigliettiService bigliettiService;
	
    final Grid<Replica> replicaGrid;
    
    PublicListView(ReplicaService replciaservice, SecurityService securityService) {
        this.replicaService = replicaService;

        var dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(getLocale())
                .withZone(ZoneId.systemDefault());
        var dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(getLocale());

        replicaGrid = new Grid<>();
        replicaGrid.setItems(query -> replicaService.list(toSpringPageRequest(query)).stream());
        replicaGrid.addColumn(Replica::getCodiceReplica).setHeader("Codice Replica");
        replicaGrid.addColumn(Replica::getDataReplica).setHeader("Data Replica");
//        replicaGrid.addComponentColumn(replica -> {TextArea text = new TextArea("Text"); return text;}).setKey("seats");
        
        replicaGrid.setSizeFull();

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

//        add(new ViewToolbar("Task List", ViewToolbar.group(description, dueDate, createBtn)));
        add(replicaGrid);
    }
    
    private void deleteReplica(String id) {
    	replicaService.deleteById(id);
    	replicaGrid.getDataProvider().refreshAll();
    }

}

