package contacts.view.memo;

import java.time.LocalDate;

import contacts.commun.IMapper;
import contacts.dao.DaoPersonne;
import contacts.data.Agir;
import contacts.data.Personne;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.javafx.view.Mode;

public class ModelMemoActeur {
	
	
	//-------
	// Données observables 
	//-------
	
	private final Agir		draft	= new Agir();
	
	private final ObjectProperty<Agir>	current	= new SimpleObjectProperty<>();
	
	private final ObservableList<Personne> acteursPossibles = FXCollections.observableArrayList(); 

	
	//-------
	// Autres champs
	//-------
	
	private Mode		mode;

	@Inject
	private IMapper		mapper;
    @Inject
	private ModelMemo	modelMemo;
    @Inject
    private DaoPersonne	daoPersonne;
	
	
	//-------
	// Getters & Setters
	//-------
	
	public Agir getDraft() {
		return draft;
	}

	public ObjectProperty<Agir> currentProperty() {
		return current;
	}

	public Agir getCurrent() {
		return current.get();
	}

	public void setCurrent(Agir item) {
		current.set(item);
	}
	
	public ObservableList<Personne> getActeursPossibles() {
		return acteursPossibles;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	
	//-------
	// Actions
	//-------
	
	public void initDraft(Mode mode) {
		this.mode = mode;
		refreshActeursPossibles();
		if( mode == Mode.NEW ) {
			mapper.update( draft, new Agir() );
			draft.setMemo( modelMemo.getDraft() );
			draft.setDebut( LocalDate.now() );
		} else {
			mapper.update( draft, getCurrent() );
		}
	}
	
	public void refreshActeursPossibles() {
		acteursPossibles.setAll( daoPersonne.listerTout() );
		for ( Agir item : modelMemo.getDraft().getActeurs() ) {
			if ( mode == Mode.NEW || getCurrent() == null || ! item.getPersonne().equals(getCurrent().getPersonne()) ) {
				acteursPossibles.remove( item.getPersonne() );
			}
		}
 	}
	
	
	public void saveDraft() {
		
		// Effectue la mise à jour
		
		if ( mode == Mode.NEW ) {
			// Ajout au mémo
			var newItem = mapper.update( new Agir(), draft );
			modelMemo.getDraft().getActeurs().add( newItem );
			// Actualise le courant
			setCurrent( newItem );
		} else {
			// Actualise le courant
			mapper.update( getCurrent(), draft );
		}
	}
	
}
