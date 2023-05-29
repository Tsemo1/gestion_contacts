package contacts.view.service;

import contacts.data.Service;
import contacts.view.ManagerGui;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.ControllerAbstract;
import jfox.javafx.view.Mode;


public class ViewServiceList extends ControllerAbstract {
	
	
	//-------
	// Composants de la vue
	//-------

	@FXML
	private ListView<Service>	lsvServices;
	@FXML
	private Button				btnModifier;
	@FXML
	private Button				btnSupprimer;


	//-------
	// Autres champs
	//-------
	
	@Inject
	private ManagerGui			managerGui;
	@Inject
	private ModelService		modelService;
	
	
	//-------
	// Initialisation du Controller
	//-------

	@FXML
	private void initialize() {

		// ListView
		lsvServices.setItems( modelService.getList() );
		UtilFX.setCellFactory( lsvServices, "nom" );
		bindBidirectional( lsvServices, modelService.currentProperty(), modelService.flagRefreshingListProperty() );
		
		// Configuraiton des boutons
		lsvServices.getSelectionModel().selectedItemProperty().addListener(obs -> {
			configurerBoutons();
		});
		configurerBoutons();
		
	}
	
	@Override
	public void refresh() {
		modelService.refreshList();
		lsvServices.requestFocus();
	}

	
	//-------
	// Actions
	//-------
	
	@FXML
	private void doAjouter() {
		modelService.initDraft( Mode.NEW );
		managerGui.showView( ViewServiceForm.class );
	}

	@FXML
	private void doModifier() {
		modelService.initDraft( Mode.EDIT );
		managerGui.showView( ViewServiceForm.class );
	}

	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm( "Confirmez-vous la suppresion ?" ) ) {
			modelService.deleteCurrent();
			refresh();
		}
	}
	
	
	//-------
	// Gestion des évènements
	//-------

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( lsvServices.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					doModifier();
				}
			}
		}
	}

	
	//-------
	// Méthodes auxiliaires
	//-------
	
	private void configurerBoutons() {
		var flagDisable = lsvServices.getSelectionModel().getSelectedItem() == null;
		btnModifier.setDisable(flagDisable);
		btnSupprimer.setDisable(flagDisable);
	}

}
