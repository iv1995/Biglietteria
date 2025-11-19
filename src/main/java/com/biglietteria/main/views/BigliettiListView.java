package com.biglietteria.main.views;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.springframework.beans.factory.annotation.Autowired;

import com.biglietteria.main.services.Biglietti;
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

@Route(value = "/Biglietti", layout = MainLayout.class)
@PageTitle("Application")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Menu")
@PermitAll
public class BigliettiListView extends Main{
String tmp;
	
	@Autowired
	private BigliettiService bigliettiService;
	
    final Grid<Biglietti> replicaGrid;
    
    BigliettiListView(BigliettiService bigliettiService) {
        this.bigliettiService = bigliettiService;

        replicaGrid = new Grid<>();
        replicaGrid.setItems(query -> bigliettiService.list(toSpringPageRequest(query)).stream());
        replicaGrid.addColumn(Biglietti::getCodiceOperazione).setHeader("Codice Operazione");
        replicaGrid.addColumn(Biglietti::getCodiceReplica).setHeader("Codice Replica");
        replicaGrid.addColumn(Biglietti::getCodiceCliente).setHeader("Codice Cliente");
        replicaGrid.addColumn(Biglietti::getQuantita).setHeader("Quantita");
        replicaGrid.addColumn(Biglietti::getTipoPagamento).setHeader("Tipo Pagamento");
        replicaGrid.addColumn(Biglietti::getDataOra).setHeader("Data e Ora");
        replicaGrid.setSizeFull();
        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);
        add(replicaGrid);
    }
    
    private void deleteBiglietti(String codiceoperazione) {
    	bigliettiService.deleteById(codiceoperazione);
    	replicaGrid.getDataProvider().refreshAll();
    }
}
