package contacts.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import contacts.data.Memo;
import jakarta.inject.Inject;
import jfox.jdbc.UtilJdbc;

public class DaoMemo {

	//-------
	// Champs
	//-------

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoCategorie     daoCategorie;
	@Inject
	private DaoAbonner    daoAbonner;
	@Inject
	private DaoAgir    daoAgir;
	//-------
	// Actions
	//-------

	public void inserer( Memo memo ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO memo ( titre, description, flagurgent ,idcategorie,statut,effectif,budget,echeance,heure) VALUES( ?, ?, ? ,?, ?, ?, ?, ?,?) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, memo.getTitre() );
			stmt.setObject( 2, memo.getDescription() );
			stmt.setObject( 3, memo.getFlagUrgent() );
			stmt.setObject( 4,memo.getCategorie() == null ? null : memo.getCategorie().getId() );
			stmt.setObject( 5, memo.getStatut() );
			stmt.setObject( 6, memo.getEffectif() );
			stmt.setObject( 7, memo.getBudget() );
			stmt.setObject( 8, memo.getEcheance() );
			stmt.setObject( 9, memo.getHeure());
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			memo.setId( rs.getObject( 1, Integer.class) );
			daoAbonner.mettreAJourPourMemo(memo);
	        daoAgir.mettreAJourPourMemo(memo);
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public void modifier( Memo Memo ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE Memo SET titre = ?, description = ? ,flagurgent = ?, idcategorie=?, statut=?,effectif=?,budget=?,echeance=?,heure=?  WHERE idMemo =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, Memo.getTitre() );
			stmt.setObject( 2, Memo.getDescription() );
			stmt.setObject( 3, Memo.getFlagUrgent() );
		
			stmt.setObject( 4,Memo.getCategorie() == null ? null : Memo.getCategorie().getId() );
			stmt.setObject( 5, Memo.getStatut() );
			stmt.setObject( 6, Memo.getEffectif() );
			stmt.setObject( 7, Memo.getBudget() );
			stmt.setObject( 8, Memo.getEcheance() );
			stmt.setObject( 9, Memo.getHeure());
			stmt.setObject( 10, Memo.getId() );
			stmt.executeUpdate();
			daoAbonner.mettreAJourPourMemo(Memo);
			daoAgir.mettreAJourPourMemo(Memo);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}


	public void supprimer( int idMemo ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM Memo WHERE idMemo = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, idMemo );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Memo retrouver( int idMemo ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM Memo WHERE idMemo = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, idMemo);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireMemo( rs, true );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public List<Memo> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM Memo ORDER BY titre";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Memo> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construireMemo( rs,false ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	  
	public int compterPourCategorie( int idmemo) {
	    	
			Connection			cn		= null;
			PreparedStatement	stmt 	= null;
			ResultSet 			rs		= null;

			try {
				cn = dataSource.getConnection();
	            String sql = "SELECT COUNT(*) FROM Memo WHERE idcategorie = ?";
	            stmt = cn.prepareStatement( sql );
	            stmt.setObject( 1, idmemo );
	            rs = stmt.executeQuery();

	            rs.next();
	            return rs.getInt( 1 );

			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( rs, stmt, cn );
			}
	    }
	
	//-------
	// Méthodes auxiliaires
	//-------
	
	protected Memo construireMemo( ResultSet rs ,Boolean flagComplet) throws SQLException {
		Memo Memo = new Memo();
		Memo.setId( rs.getObject( "idMemo", Integer.class ) );
		Memo.setTitre( rs.getObject( "titre", String.class ) );
		Memo.setDescription( rs.getObject( "description", String.class ) );
		Memo.setFlagUrgent( rs.getObject( "flagUrgent", Boolean.class ) );
		Memo.setStatut( rs.getObject( "statut", String.class ) );
		Memo.setEffectif( rs.getObject( "effectif", Integer.class ) );
		Memo.setBudget( rs.getObject( "budget", BigDecimal.class) );
		Memo.setEcheance( rs.getObject( "echeance", LocalDate.class ) );
		Memo.setHeure( rs.getObject( "heure", LocalTime.class ) );
		
	
		if ( flagComplet ) {
			
			var idCategorie = rs.getObject( "idcategorie", Integer.class );
			
			if ( idCategorie != null ) {
				Memo.setCategorie( daoCategorie.retrouver( idCategorie ) );
				}
				Memo.getAbonnes().setAll( daoAbonner.listerPourMemo( Memo ) );
				Memo.getActeurs().setAll( daoAgir.listerPourMemo( Memo ) );
			}
		return Memo;
	}

}
