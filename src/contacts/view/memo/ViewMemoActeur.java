package contacts.view.memo;

import contacts.data.Personne;
import contacts.view.ManagerGui;
import jakarta.inject.Inject;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.ControllerAbstract;


public class ViewMemoActeur extends ControllerAbstract {

	
	//-------
	// Composants de la vue
	//-------
	
	@FXML
	private Label			labTitre;
	@FXML
	private ComboBox<Personne>	cmbPersonne;
	@FXML
	private TextField		txfFonction;
	@FXML
	private DatePicker		dtpDebut;
	@FXML
	private Button			btnValider;


	//-------
	// Autres champs
	//-------
	
	@Inject
	private ManagerGui		managerGui;
	@Inject
	private ModelMemoActeur	modelActeur;


	//-------
	// Initialisations
	//-------

	@FXML
	private void initialize() {
		
		var draft = modelActeur.getDraft();

		// Titre
		bind( labTitre, Bindings.createStringBinding( () -> draft.getMemo() == null ? null: draft.getMemo().getTitre(), draft.memoProperty() ) );
		
		// Personne
		cmbPersonne.setItems( modelActeur.getActeursPossibles() );
		bindBidirectional( cmbPersonne, draft.personneProperty() );
		UtilFX.setCellFactory( cmbPersonne, p -> p.getNom() + " " + p.getPrenom() );
		validator.addRuleNotNull( cmbPersonne );
		
		// Fonction
		bindBidirectional( txfFonction, draft.fonctionProperty()  );
		
		// Date début
		bindBidirectional( dtpDebut, draft.debutProperty() );
		
		
		// Bouton Valider
		btnValider.disableProperty().bind( validator.invalidProperty() );
	}
	
	
	@Override
	public void refresh() {
		cmbPersonne.requestFocus();
	}
	
	
	//-------
	// Actions
	//-------
	
	@FXML
	private void doAnnuler() {
		managerGui.closeDialog();
	}
	
	@FXML
	private void doValider() {
		modelActeur.saveDraft();
		managerGui.closeDialog();
	}

}
