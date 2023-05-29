package contacts.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Memo {
	private final ObjectProperty<Integer> id = new SimpleObjectProperty<>();
	  private final StringProperty titre = new SimpleStringProperty();
	  private final StringProperty description = new SimpleStringProperty();
	  private final ObjectProperty<Boolean> flagUrgent = new SimpleObjectProperty<>();
	  private final ObjectProperty<Categorie> categorie = new SimpleObjectProperty<>();
	  private final StringProperty statut= new SimpleStringProperty();;
	  private final ObjectProperty<Integer> effectif= new SimpleObjectProperty<>();
	  private final  ObjectProperty<BigDecimal> budget=new SimpleObjectProperty<>();
	  private final ObjectProperty<LocalDate> echeance=new SimpleObjectProperty<>();
	  private final ObjectProperty<LocalTime> heure=new SimpleObjectProperty<>();
	  private final ObservableList<Compte> abonnes = FXCollections.observableArrayList();
	  private final ObservableList<Agir> acteurs = FXCollections.observableArrayList();
	  
	  public final ObjectProperty<Integer> idProperty() {
	    return this.id;
	  }
	  
	  public final Integer getId() {
	    return this.idProperty().get();
	  }
	  
	  public final void setId(final Integer id) {
	    this.idProperty().set(id);
	  }
	  
	  public final StringProperty titreProperty() {
	    return this.titre;
	  }
	  
	  public final String getTitre() {
	    return this.titreProperty().get();
	  }
	  
	  public final void setTitre(final String titre) {
	    this.titreProperty().set(titre);
	  }
	  
	  public final StringProperty descriptionProperty() {
	    return this.description;
	  }
	  
	  public final String getDescription() {
	    return this.descriptionProperty().get();
	  }
	  
	  public final void setDescription(final String description) {
	    this.descriptionProperty().set(description);
	  }
	  
	  public final ObjectProperty<Boolean> flagUrgentProperty() {
	    return this.flagUrgent;
	  }
	  
	  public final Boolean getFlagUrgent() {
	    return this.flagUrgentProperty().get();
	  }
	  
	  public final void setFlagUrgent(final Boolean flagUrgent) {
	    this.flagUrgentProperty().set(flagUrgent);
	  }
	  public final ObjectProperty<Categorie> categorieProperty() {
		    return this.categorie;
		  }
		  
		  public final Categorie getCategorie() {
		    return this.categorieProperty().get();
		  }
		  
		  public final void setCategorie(final Categorie categorie) {
		    this.categorieProperty().set(categorie);
		  } 
		 public final StringProperty statutProperty() {
			    return this.statut;
			  }
			  
		 public final String getStatut() {
			    return this.statutProperty().get();
			  }
			  
		 public final void setStatut(final String statut) {
			    this.statutProperty().set(statut);
			  }
		 public final ObjectProperty<Integer> effectifProperty() {
			    return this.effectif;
			  }
			  
		public final Integer getEffectif() {
			    return this.effectifProperty().get();
			  }
			  
		 public final void setEffectif(final Integer effectif) {
			    this.effectifProperty().set(effectif);
			  }
	    public final ObjectProperty<BigDecimal> budgetProperty() {
			    return this.budget;
			  }
			  
	    public final BigDecimal getBudget() {
			    return this.budgetProperty().get();
			  }
			  
		 public final void setBudget(final BigDecimal budget) {
			    this.budgetProperty().set(budget);
			  } 
			  
		 public final ObjectProperty<LocalDate> echeanceProperty() {
			    return this. echeance;
			  }
			  
	     public final LocalDate getEcheance() {
			    return this. echeanceProperty().get();
			  }
			  
		 public final void setEcheance(final LocalDate  echeance) {
			    this. echeanceProperty().set(echeance);
			  } 
			  
		 public final ObjectProperty<LocalTime> heureProperty() {
			    return this.heure;
			  }
			  
	     public final LocalTime getHeure() {
			    return this. heureProperty().get();
			  }
			  
		 public final void setHeure(final LocalTime  heure) {
			    this. heureProperty().set(heure);
			  } 
			  
		 public ObservableList<Compte> getAbonnes() {
				return abonnes;
			}
		 
		 public ObservableList<Agir> getActeurs() {
			 return acteurs;
			 }
		 //
	     
				

	

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
		Memo other = (Memo) obj;
		return Objects.equals(id.get(), other.id.get());
	}

	
}
