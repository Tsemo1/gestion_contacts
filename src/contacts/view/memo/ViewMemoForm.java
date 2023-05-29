package contacts.view.memo;





import java.math.BigDecimal;
import java.time.LocalDate;

import contacts.data.Agir;
import contacts.data.Categorie;
import contacts.data.Compte;
import contacts.data.Personne;
import contacts.view.ManagerGui;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import jfox.javafx.util.UtilFX;
import jfox.javafx.util.converter.ConverterBigDecimal;
import jfox.javafx.util.converter.ConverterInteger;
import jfox.javafx.util.converter.ConverterLocalDate;
import jfox.javafx.util.converter.ConverterLocalTime;
import jfox.javafx.view.ControllerAbstract;
import jfox.javafx.view.Mode;



public class ViewMemoForm extends ControllerAbstract {

	
	//-------
	// Composants de la vue
	//-------
	
	@FXML
	private Label			labId;
	@FXML
	private TextField		txfTitre;
	@FXML
	private ToggleGroup		tggStatut;
	@FXML
	private TextArea		txaDescription;
	@FXML
	private TextField txfEffectif;
	@FXML
	private TextField txfBudget;
	@FXML
	private DatePicker dtpEcheance;
	@FXML
	private TextField txfHeure;
	
	@FXML
	private CheckBox		ckbUrgent;
	@FXML
	private Button			btnValider;
	@FXML
	private ComboBox<Categorie> cmbCategorie;
	@FXML
	private ListView<Compte> lsvAbonnes;
	@FXML
	private TableView<Agir> tbvActeurs;
	@FXML
	private TableColumn<Agir, Personne> colPersonne;
	@FXML
	private TableColumn<Agir, String> colFonction;
	//-------
	// Autres champs
	//-------
	
	@Inject
	private ManagerGui		managerGui;
	@Inject
	private ModelMemo	modelMemo;
	@Inject
	private ModelMemoActeur modelActeur;

	//-------
	// Initialisation du Controller
	//-------

	@FXML
	private void initialize() {
		
		var draft = modelMemo.getDraft();

		// Id
		bind( labId, draft.idProperty(), new ConverterInteger() );
		
		// Libellé
		bindBidirectional( txfTitre, draft.titreProperty()  );
		validator.addRuleNotBlank(txfTitre);
		validator.addRuleMaxLength(txfTitre, 50 );
		validator.addRuleMinLength(txfTitre,4);
		
		// Description
		bindBidirectional( txaDescription, draft.descriptionProperty() );
		bindBidirectional( txfEffectif, draft.effectifProperty(), new ConverterInteger() );
		validator.addRuleMinValue(txfEffectif,0);
		validator.addRuleMaxValue(txfEffectif, 1000);
		bindBidirectional( txfBudget, draft.budgetProperty(), new ConverterBigDecimal("#,##0.00") );
		validator.addRuleMinValue( txfBudget, BigDecimal.valueOf(0) );
		validator.addRuleMaxValue(txfBudget, BigDecimal.valueOf(1000000));
		bindBidirectional( dtpEcheance, draft.echeanceProperty(), new ConverterLocalDate("dd/MM/yy") );
		validator.addRuleMinValue( dtpEcheance,LocalDate.of(2000, 1, 1)  );
		validator.addRuleMaxValue(dtpEcheance, LocalDate.of(2099, 12, 31));
		bindBidirectional( txfHeure, draft.heureProperty(), new ConverterLocalTime("HH:mm") );
		bindBidirectional( tggStatut, draft.statutProperty(), "A", "E", "F" );
		bindBidirectional(ckbUrgent, draft.flagUrgentProperty());
		
		
		cmbCategorie.setItems( modelMemo.getCategories() );
		bindBidirectional( cmbCategorie, draft.categorieProperty() );
		UtilFX.setCellFactory( cmbCategorie, "libelle" );
		
		lsvAbonnes.setItems( draft.getAbonnes() );
		lsvAbonnes.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
		UtilFX.setCellFactory( lsvAbonnes, "pseudo" );
		
		tbvActeurs.setItems( draft.getActeurs() );
		bindBidirectional( tbvActeurs, modelActeur.currentProperty() );
		UtilFX.setValueFactory( colPersonne, "personne" );
		UtilFX.setValueFactory( colFonction, "fonction" );
		UtilFX.setCellFactory( colPersonne, p -> p.getNom() + " " + p.getPrenom() );
		
		
		// Bouton VAlider
		btnValider.disableProperty().bind( validator.invalidProperty() );
	}
	
	
	@Override
	public void refresh() {
		txfTitre.requestFocus();
	}
	
	
	//-------
	// Actions
	//-------
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( ViewMemoList.class );
	}
	
	@FXML
	private void doValider() {
		modelMemo.saveDraft();
		managerGui.showView( ViewMemoList.class );
	}
	@FXML
	private void doCategorieSupprimer() {
	cmbCategorie.setValue( null );
	}
   
	@FXML
	private void doAbonneAjouter() {
		managerGui.showDialog( ViewMemoAbonner.class );
	}
	
	@FXML
	private void doAbonneSupprimer() {
	var items = lsvAbonnes.getSelectionModel().getSelectedItems();
	modelMemo.getDraft().getAbonnes().removeAll( items );
	}
	
	@FXML
	private void doActeurAjouter() {
		modelActeur.initDraft( Mode.NEW );
		managerGui.showDialog( ViewMemoActeur.class );
		tbvActeurs.requestFocus();
	}
	@FXML
	private void doActeurModifier() {
		modelActeur.initDraft( Mode.EDIT );
		managerGui.showDialog( ViewMemoActeur.class );
		tbvActeurs.requestFocus();
	}
	@FXML
	private void doActeurSupprimer() {
		var items = tbvActeurs.getSelectionModel().getSelectedItems();
		modelMemo.getDraft().getActeurs().removeAll( items );
	}
	
}
