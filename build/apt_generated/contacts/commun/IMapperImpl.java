package contacts.commun;

import contacts.data.Agir;
import contacts.data.Categorie;
import contacts.data.Compte;
import contacts.data.Memo;
import contacts.data.Personne;
import contacts.data.Service;
import contacts.data.Telephone;
import javafx.collections.ObservableList;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-29T12:57:45+0200",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 1.3.1200.v20200916-0645, environment: Java 15.0.2 (Oracle Corporation)"
)
public class IMapperImpl implements IMapper {

    @Override
    public Compte update(Compte target, Compte source) {
        if ( source == null ) {
            return target;
        }

        target.setId( source.getId() );
        target.setPseudo( source.getPseudo() );
        target.setMotDePasse( source.getMotDePasse() );
        target.setEmail( source.getEmail() );
        if ( target.getRoles() != null ) {
            target.getRoles().clear();
            ObservableList<String> observableList = source.getRoles();
            if ( observableList != null ) {
                target.getRoles().addAll( observableList );
            }
        }

        return target;
    }

    @Override
    public Categorie update(Categorie target, Categorie source) {
        if ( source == null ) {
            return target;
        }

        target.setId( source.getId() );
        target.setLibelle( source.getLibelle() );
        target.setDebut( source.getDebut() );

        return target;
    }

    @Override
    public Service update(Service target, Service source) {
        if ( source == null ) {
            return target;
        }

        target.setId( source.getId() );
        target.setNom( source.getNom() );
        target.setAnneeCreation( source.getAnneeCreation() );
        target.setFlagSiege( source.getFlagSiege() );

        target.setPersonne( source.getPersonne() );

        return target;
    }

    @Override
    public Agir update(Agir target, Agir source) {
        if ( source == null ) {
            return target;
        }

        target.setFonction( source.getFonction() );
        target.setDebut( source.getDebut() );

        target.setMemo( source.getMemo() );
        target.setPersonne( source.getPersonne() );

        return target;
    }

    @Override
    public Memo update(Memo target, Memo source) {
        if ( source == null ) {
            return target;
        }

        target.setId( source.getId() );
        target.setTitre( source.getTitre() );
        target.setDescription( source.getDescription() );
        target.setFlagUrgent( source.getFlagUrgent() );
        target.setStatut( source.getStatut() );
        target.setEffectif( source.getEffectif() );
        target.setBudget( source.getBudget() );
        target.setEcheance( source.getEcheance() );
        target.setHeure( source.getHeure() );
        if ( target.getAbonnes() != null ) {
            target.getAbonnes().clear();
            ObservableList<Compte> observableList = source.getAbonnes();
            if ( observableList != null ) {
                target.getAbonnes().addAll( observableList );
            }
        }
        if ( target.getActeurs() != null ) {
            target.getActeurs().clear();
            ObservableList<Agir> observableList1 = source.getActeurs();
            if ( observableList1 != null ) {
                target.getActeurs().addAll( observableList1 );
            }
        }

        target.setCategorie( source.getCategorie() );

        return target;
    }

    @Override
    public Personne update(Personne target, Personne source) {
        if ( source == null ) {
            return target;
        }

        target.setId( source.getId() );
        target.setNom( source.getNom() );
        target.setPrenom( source.getPrenom() );
        if ( target.getTelephones() != null ) {
            target.getTelephones().clear();
            ObservableList<Telephone> observableList = source.getTelephones();
            if ( observableList != null ) {
                target.getTelephones().addAll( observableList );
            }
        }

        target.setCategorie( source.getCategorie() );

        return target;
    }
}
