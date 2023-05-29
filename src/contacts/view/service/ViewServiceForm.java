package contacts.view.service;





import contacts.data.Categorie;
import contacts.data.Personne;
import contacts.data.Service;
import contacts.view.ManagerGui;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jfox.javafx.util.UtilFX;
import jfox.javafx.util.converter.ConverterInteger;
import jfox.javafx.view.ControllerAbstract;


public class ViewServiceForm extends ControllerAbstract {

	
	//-------
	// Composants de la vue
	//-------
	
	@FXML
	private Label			labId;
	@FXML
	private TextField		txfNom;
	@FXML
	private TextField		txfCrée;
	@FXML
	private CheckBox		ckbSieège;
	@FXML
	private Button			btnValider;
	@FXML
	private ComboBox<Personne> cmbPersonne;


	//-------
	// Autres champs
	//-------
	
	@Inject
	private ManagerGui		managerGui;
	@Inject
	private ModelService	modelService;


	//-------
	// Initialisation du Controller
	//-------

	@FXML
	private void initialize() {
		
		var draft = modelService.getDraft();

		// Id
		bind( labId, draft.idProperty(), new ConverterInteger() );
		
		// Libellé
		bindBidirectional( txfNom, draft.nomProperty()  );
		validator.addRuleNotBlank(txfNom);
		validator.addRuleMaxLength(txfNom, 50 );
		validator.addRuleMinLength(txfNom,3);
		
		// Description

		bindBidirectional( txfCrée, draft.anneeCreationProperty(), new ConverterInteger() );
		
		bindBidirectional(ckbSieège, draft.flagSiegeProperty());
		
		cmbPersonne.setItems( modelService.getPersonnes());
		bindBidirectional( cmbPersonne, draft.personneProperty());
		
		UtilFX.setCellFactory( cmbPersonne, p -> p.getNom() + " " + p.getPrenom() ); 
		
		// Bouton VAlider
		btnValider.disableProperty().bind( validator.invalidProperty() );
	}
	
	
	@Override
	public void refresh() {
		txfNom.requestFocus();
	}
	
	
	//-------
	// Actions
	//-------
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( ViewServiceList.class );
	}
	
	@FXML
	private void doValider() {
		modelService.saveDraft();
		managerGui.showView( ViewServiceList.class );
	}
	@FXML
	private void doPersonneSupprimer() {
		cmbPersonne.setValue( null );
	}

}
