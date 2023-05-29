package contacts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.sql.DataSource;

import contacts.data.Compte;
import contacts.data.Memo;
import jakarta.inject.Inject;
import jfox.jdbc.UtilJdbc;


public class DaoAbonner {

	
	//-------
	// Champs
	//-------

	@Inject
	private DataSource		dataSource;

	@Inject
	private DaoCompte		daoCompte;
	//-------
	// Actions
	//-------

	public void mettreAJourPourMemo( Memo memo ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet			rs 		= null;
		String				sql;

		try {

			// Crée le Set des éléments à conserver ou à insérer
			var set = new HashSet<>();
			for ( var item : memo.getAbonnes() ) {
				set.add( item.getId()); 
			}

			// Crée le RésultSet des éléments présents dans la base 
			cn = dataSource.getConnection();
			sql = "SELECT * FROM abonner WHERE idMemo = ?";
			stmt = cn.prepareStatement( sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE );
			stmt.setObject( 1, memo.getId() );
			rs = stmt.executeQuery();
			
			// Parcourt les éléments du ResultSet
			// Supprime ceux qui ne sont pas dans la Map
			// Retire du Set ceux qui y sont trouvés
			// Ainsi, à la fin, le Set ne contient plus que les éléments à insérer
			while( rs.next() ) {
				var item = rs.getObject( "idcompte", Integer.class );
				if( ! set.contains( item ) ) {
					rs.deleteRow();
				} else {
					set.remove( item );
				}
			}
			
			// Insère les éléments encore présents dans le Set
			for ( var item : set ) {
				rs.moveToInsertRow();
				rs.updateObject( "idmemo", memo.getId() );
				rs.updateObject( "idcompte", item );
				rs.insertRow();
			}
			
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public List<Compte> listerPourMemo( Memo memo ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT c.* FROM abonner a JOIN compte c ON a.idcompte = c.idcompte WHERE a.idmemo = ? ORDER BY c.pseudo";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, memo.getId() );
			rs = stmt.executeQuery();

			List<Compte> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( daoCompte.construireCompte( rs, false ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	

}
