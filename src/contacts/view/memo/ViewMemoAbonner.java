package contacts.view.memo;

import contacts.data.Compte;

import contacts.view.ManagerGui;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.ControllerAbstract;


public class ViewMemoAbonner extends ControllerAbstract {
	
	
	//-------
	// Composants de la vue
	//-------

	@FXML
	private ListView<Compte>	lsvComptes;
	@FXML
	private Button				btnAbonner;



	//-------
	// Autres champs
	//-------
	
	@Inject
	private ManagerGui			managerGui;
	@Inject
	private ModelMemo		modelMemo;
	
	
	//-------
	// Initialisation du Controller
	//-------

	@FXML
	private void initialize() {

		// ListView
		lsvComptes.setItems( modelMemo.getComptesAbonnables() );
		lsvComptes.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
		UtilFX.setCellFactory( lsvComptes, "pseudo" );
		
		
		// Configuraiton des boutons
		lsvComptes.getSelectionModel().selectedItemProperty().addListener(obs -> {
			configurerBoutons();
		});
		configurerBoutons();
		
	}
	
	@Override
	public void refresh() {
		modelMemo.refreshComptesAbonnables();;
		lsvComptes.requestFocus();
	}

	
	//-------
	// Actions
	//-------
	
	@FXML
	private void doFermer() {
	 managerGui.closeDialog();
	
	}
	@FXML
	private void doAbonner() {
		
		var items = lsvComptes.getSelectionModel().getSelectedItems();
		modelMemo.getDraft().getAbonnes().addAll( items );
	    managerGui.closeDialog();
	
	}

	
	
	
	//-------
	// Gestion des évènements
	//-------

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( lsvComptes.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					doAbonner();
				}
			}
		}
	}

	
	//-------
	// Méthodes auxiliaires
	//-------
	
	private void configurerBoutons() {
		var flagDisable = lsvComptes.getSelectionModel().getSelectedItem() == null;
		btnAbonner.setDisable(flagDisable);

	}

}
