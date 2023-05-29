package contacts.data;

import java.time.LocalDate;
import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Agir {
	private final ObjectProperty<Memo> memo = new SimpleObjectProperty<>();
	private final ObjectProperty<Personne> personne = new SimpleObjectProperty<>();
	private final StringProperty fonction = new SimpleStringProperty();
	private final ObjectProperty<LocalDate> debut = new SimpleObjectProperty<>();
	
	
	public final ObjectProperty<Memo> memoProperty() {
		return this.memo;
	}
	
	public final Memo getMemo() {
		return this.memoProperty().get();
	}
	
	public final void setMemo(final Memo memo) {
		this.memoProperty().set(memo);
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
	
	public final StringProperty fonctionProperty() {
		return this.fonction;
	}
	
	public final String getFonction() {
		return this.fonctionProperty().get();
	}
	
	public final void setFonction(final String fonction) {
		this.fonctionProperty().set(fonction);
	}
	
	public final ObjectProperty<LocalDate> debutProperty() {
		return this.debut;
	}
	
	public final LocalDate getDebut() {
		return this.debutProperty().get();
	}
	
	public final void setDebut(final LocalDate debut) {
		this.debutProperty().set(debut);
	}

	@Override
	public int hashCode() {
		return Objects.hash(memo, personne);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agir other = (Agir) obj;
		return Objects.equals(memo, other.memo) && Objects.equals(personne, other.personne);
	}
	
	
}
