package contacts.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import contacts.data.Agir;
import contacts.data.Memo;
import jakarta.inject.Inject;
import jfox.jdbc.UtilJdbc;


public class DaoAgir {

	
	//-------
	// Champs
	//-------

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoPersonne		daoPersonne;

	
	//-------
	// Actions
	//-------

	public void mettreAJourPourMemo( Memo memo ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet			rs 		= null;
		String				sql;

		try {

			// Crée la Map des éléments à modifier ou à insérer
			var map = new HashMap<Integer,Agir>();
			for ( var item : memo.getActeurs() ) {
				map.put( item.getPersonne().getId(), item ); 
			}

			// Crée le RésultSet des éléments présents dans la base 
			cn = dataSource.getConnection();
			sql = "SELECT * FROM agir WHERE idmemo = ?";
			stmt = cn.prepareStatement( sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE );
			stmt.setObject( 1, memo.getId() );
			rs = stmt.executeQuery();
			
			// Parcourt les éléments du ResultSet
			// Supprime ceux qui ne sont pas dans la Map
			// Modifier ceux qui y sont présent et les supprime de laMap
			// Ainsi, à la fin, la Map ne contient plus que les éléments à insérer
			while( rs.next() ) {
				var key = rs.getObject( "idpersonne", Integer.class );
				var item = map.get( key );
				if( item == null ) {
					rs.deleteRow();
				} else {
					update( rs, item );
					rs.updateRow();
					map.remove( key );
				}
			}

			// Insère les éléments encore présents dans la Map
			for ( var item : map.values() ) {
				rs.moveToInsertRow();
				update( rs, item );
				rs.insertRow();
			}
			
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public List<Agir> listerPourMemo( Memo memo ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT a.*, p.* FROM agir a JOIN personne p ON a.idpersonne = p.idpersonne WHERE a.idmemo = ? ORDER BY p.nom, p.prenom";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, memo.getId() );
			rs = stmt.executeQuery();

			List<Agir> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construireAgir( rs, memo ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
	//-------
	// Méthodes auxiliaires
	//-------
	
	private void update( ResultSet rs, Agir item ) throws SQLException {
		rs.updateObject( "idmemo", item.getMemo().getId() );
		rs.updateObject( "idpersonne", item.getPersonne().getId() );
		rs.updateObject( "fonction", item.getFonction() );
		rs.updateObject( "debut", item.getDebut() == null ? null : Date.valueOf( item.getDebut() ) );
	}
	
	protected Agir construireAgir( ResultSet rs, Memo memo ) throws SQLException {
		Agir agir = new Agir();
		agir.setMemo(memo);
		agir.setPersonne( daoPersonne.construirePersonne( rs, false, false ) );
		agir.setFonction( rs.getObject( "fonction", String.class ) );
		agir.setDebut( rs.getObject( "debut", LocalDate.class ) );
		return agir;
	}
	
}
