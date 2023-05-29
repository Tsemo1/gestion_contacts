package contacts.view;

import contacts.commun.Roles;
import contacts.view.categorie.ViewCategorieList;
import contacts.view.compte.ViewCompteCombo;
import contacts.view.memo.ViewMemoList;
import contacts.view.personne.ViewPersonneList;
import contacts.view.service.ViewServiceList;
import contacts.view.systeme.ModelConnexion;
import contacts.view.systeme.ViewAbout;
import contacts.view.systeme.ViewConnexion;
import contacts.view.test.ViewTestDaoCategorie;
import contacts.view.test.ViewTestDaoCompte;
import contacts.view.test.ViewTestDaoMemo;
import contacts.view.test.ViewTestDaoPersonne;
import contacts.view.test.ViewTestDaoService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Menu;
import jfox.context.Dependent;
import jfox.javafx.control.MenuBarAbstract;


@Dependent
public class MenuBarAppli extends MenuBarAbstract {

	
	//-------
	// Champs
	//-------
	
	private final BooleanProperty flagConnexion	= new SimpleBooleanProperty();
	private final BooleanProperty flagRoleUtil	= new SimpleBooleanProperty();
	private final BooleanProperty flagRoleAdmin	= new SimpleBooleanProperty();
	
	@Inject
	private ManagerGui 		managerGui;
	@Inject
	private ModelConnexion	modelConnexion;	
	
	
	//-------
	// Initialisation
	//-------
	
	@PostConstruct
	public void init() {
		
		// Variables de travail
		Menu menu;
		
		
		// Menu Système
		
		menu = addMenu( "Système" );

		addMenuItem( "Se déconnecter", menu, flagConnexion,
				e -> managerGui.showView( ViewConnexion.class ) );

		addMenuItem( "Quitter", menu, 
				e -> managerGui.exit() );
		

		
		// Menu Données
		
		menu = addMenu( "Donnees", flagRoleUtil.or(flagRoleAdmin) );
		
		addMenuItem( "Personnes", menu,
				e -> managerGui.showView( ViewPersonneList.class ) );
		addMenuItem( "Memos", menu,
				e -> managerGui.showView( ViewMemoList.class) );
		addMenuItem( "services", menu,
				e -> managerGui.showView( ViewServiceList.class) );
		
		addMenuItem( "Catégories", menu, flagRoleAdmin, 
				e -> managerGui.showView( ViewCategorieList.class ) );
		
		addMenuItem( "Comptes ", menu, flagRoleAdmin, 
				e -> managerGui.showView( ViewCompteCombo.class ) );

		
		// Menu Tests
		
		menu = addMenu( "Test", flagRoleAdmin );
		
		addMenuItem( "DaoCategorie", menu,
				e -> managerGui.showView( ViewTestDaoCategorie.class ) );
		
		addMenuItem( "DaoPersonne", menu,
				e -> managerGui.showView( ViewTestDaoPersonne.class ) );
		
		addMenuItem( "DaoCompte", menu,
				e -> managerGui.showView( ViewTestDaoCompte.class ) );
		
		addMenuItem( "DaoMemo", menu,
				e -> managerGui.showView( ViewTestDaoMemo.class ) );
		addMenuItem( "DaoService", menu,
				e -> managerGui.showView( ViewTestDaoService.class ) );

		
		// Menu Aide
		
		menu = addMenu( "?" );
		
		addMenuItem( "A propos", menu,
				e -> managerGui.showDialog( ViewAbout.class ) );

		
		// Gestion des droits d'accès
		
		final var compteActif = modelConnexion.compteActifProperty();
		flagConnexion.bind( compteActif.isNotNull() );
		flagRoleUtil.bind( Bindings.createBooleanBinding( () -> flagConnexion.get() && compteActif.get().isInRole(Roles.UTILISATEUR), compteActif ) );
		flagRoleAdmin.bind( Bindings.createBooleanBinding( () -> flagConnexion.get() && compteActif.get().isInRole(Roles.ADMINISTRATEUR), compteActif ) );
		
	}
}
