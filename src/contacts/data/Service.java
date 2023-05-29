package contacts.data;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Service {
	
		private final ObjectProperty<Integer> id = new SimpleObjectProperty<>();
		  private final StringProperty nom = new SimpleStringProperty();
		  private final ObjectProperty<Integer> anneeCreation = new SimpleObjectProperty<>();
		  private final ObjectProperty<Boolean> flagSiege = new SimpleObjectProperty<>();
		  private final ObjectProperty<Personne> personne = new SimpleObjectProperty<>();
		  public final ObjectProperty<Integer> idProperty() {
		    return this.id;
		  }
		  
		  public final Integer getId() {
		    return this.idProperty().get();
		  }
		  
		  public final void setId(final Integer id) {
		    this.idProperty().set(id);
		  }
		  
		  public final StringProperty nomProperty() {
		    return this.nom;
		  }
		  
		  public final String getNom() {
		    return this.nomProperty().get();
		  }
		  
		  public final void setNom(final String nom) {
		    this.nomProperty().set(nom);
		  }
		  public final ObjectProperty<Integer> anneeCreationProperty() {
			    return this.anneeCreation;
			  }
			  
			  public final Integer getAnneeCreation() {
			    return this.anneeCreationProperty().get();
			  }
			  
			  public final void setAnneeCreation(final Integer anneeCreation) {
			    this.anneeCreationProperty().set(anneeCreation);
			  }

		  
		  public final ObjectProperty<Boolean> flagSiegeProperty() {
		    return this.flagSiege;
		  }
		  
		  public final Boolean getFlagSiege() {
		    return this.flagSiegeProperty().get();
		  }
		  
		  public final void setFlagSiege(final Boolean flagSiege) {
		    this.flagSiegeProperty().set(flagSiege);
		  }
		  public final ObjectProperty<Personne> personneProperty() {
			    return this.personne;
			  }
			  
			  public final Personne getPersonne() {
			    return this.personneProperty().get();
			  }
			  
			  public final void setPersonne(final Personne personne) {
			    this.personneProperty().set(personne);
			  }
			 

		@Override
		public int hashCode() {
			return Objects.hash(id.get());
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Service other = (Service) obj;
			return Objects.equals(id.get(), other.id.get());
		}
}
