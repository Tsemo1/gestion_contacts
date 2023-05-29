package contacts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import contacts.data.Service;

import jakarta.inject.Inject;
import jfox.jdbc.UtilJdbc;

public class DaoService {
	//-------
		// Champs
		//-------

		@Inject
		private DataSource		dataSource;
		@Inject
		private DaoPersonne     daoPersonne;
		//-------
		// Actions
		//-------

		public void inserer( Service service ) {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			ResultSet 			rs		= null;
			String				sql;

			try {
				cn = dataSource.getConnection();
				sql = "INSERT INTO service ( nom, anneeCreation, flagsiege ,idpersonne) VALUES( ?, ?, ? ,?) ";
				stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
				stmt.setObject( 1, service.getNom() );
				stmt.setObject( 2, service.getAnneeCreation() );
				stmt.setObject( 3, service.getFlagSiege() );
				stmt.setObject( 4,
						service.getPersonne() == null ? null : service.getPersonne().getId() );
				stmt.executeUpdate();

				// Récupère l'identifiant généré par le SGBD
				rs = stmt.getGeneratedKeys();
				rs.next();
				service.setId( rs.getObject( 1, Integer.class) );
		
			} catch ( SQLException e ) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( rs, stmt, cn );
			}
		}


		public void modifier( Service Service ) {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			String				sql;

			try {
				cn = dataSource.getConnection();
				sql = "UPDATE Service SET nom = ?, anneeCreation = ? ,flagsiege = ?, idpersonne=?  WHERE idService =  ?";
				stmt = cn.prepareStatement( sql );
				stmt.setObject( 1, Service.getNom() );
				stmt.setObject( 2, Service.getAnneeCreation() );
				stmt.setObject( 3, Service.getFlagSiege() );
				stmt.setObject( 4,
						Service.getPersonne() == null ? null : Service.getPersonne().getId() );
				stmt.setObject( 5, Service.getId() );
				stmt.executeUpdate();

			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( stmt, cn );
			}
		}


		public void supprimer( int idService ) {

			Connection			cn 		= null;
			PreparedStatement	stmt 	= null;
			String				sql;

			try {
				cn = dataSource.getConnection();
				sql = "DELETE FROM Service WHERE idService = ? ";
				stmt = cn.prepareStatement( sql );
				stmt.setObject( 1, idService );
				stmt.executeUpdate();

			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( stmt, cn );
			}
		}

		
		public Service retrouver( int idService ) {

			Connection			cn 		= null;
			PreparedStatement	stmt	= null;
			ResultSet 			rs		= null;
			String				sql;

			try {
				cn = dataSource.getConnection();
				sql = "SELECT * FROM Service WHERE idService = ?";
				stmt = cn.prepareStatement( sql );
				stmt.setObject(1, idService);
				rs = stmt.executeQuery();

				if ( rs.next() ) {
					return construireService( rs );
				} else {
					return null;
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( rs, stmt, cn );
			}
		}


		public List<Service> listerTout() {

			Connection			cn 		= null;
			PreparedStatement	stmt 	= null;
			ResultSet 			rs		= null;
			String				sql;

			try {
				cn = dataSource.getConnection();
				sql = "SELECT * FROM Service ORDER BY nom";
				stmt = cn.prepareStatement( sql );
				rs = stmt.executeQuery();

				List<Service> liste = new ArrayList<>();
				while (rs.next()) {
					liste.add( construireService( rs ) );
				}
				return liste;

			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( rs, stmt, cn );
			}
		}
		  
		public int compterPourPersonne( int idservice) {
		    	
				Connection			cn		= null;
				PreparedStatement	stmt 	= null;
				ResultSet 			rs		= null;

				try {
					cn = dataSource.getConnection();
		            String sql = "SELECT COUNT(*) FROM Service WHERE idpersonne = ?";
		            stmt = cn.prepareStatement( sql );
		            stmt.setObject( 1, idservice );
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
		
		protected Service construireService( ResultSet rs ) throws SQLException {
			Service Service = new Service();
			Service.setId( rs.getObject( "idService", Integer.class ) );
			Service.setNom( rs.getObject( "nom", String.class ) );
			Service.setAnneeCreation( rs.getObject( "anneeCreation", Integer.class ) );
			Service.setFlagSiege( rs.getObject( "flagSiege", Boolean.class ) );
			var idPersonne = rs.getObject( "idpersonne", Integer.class );
			if ( idPersonne != null ) {
			Service.setPersonne( daoPersonne.retrouver(idPersonne) );
			}
			return Service;
		}

}
