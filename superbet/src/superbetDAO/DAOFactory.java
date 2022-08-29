package superbetDAO;

import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import superbetDAOImpl.AirtimeDAOImpl;
import superbetDAOImpl.CagnotteDAOImpl;
import superbetDAOImpl.CaissierDAOImpl;
import superbetDAOImpl.ConfigDAOImpl;
import superbetDAOImpl.EffChoicekDAOImpl;
import superbetDAOImpl.EffChoicepDAOImpl;
import superbetDAOImpl.GameCycleDAOImpl;
import superbetDAOImpl.KenoDAOImpl;
import superbetDAOImpl.MisekDAOImpl;
import superbetDAOImpl.Misek_tempDAOImpl;
import superbetDAOImpl.MisepDAOImpl;
import superbetDAOImpl.MisetDAOImpl;
import superbetDAOImpl.PartnerDAOImpl;
import superbetDAOImpl.SpinDAOImpl;
import superbetDAOImpl.UtilDAOImpl;
import superbetDAOImpl.VersementDAOImpl;

public class DAOFactory {
	private static DAOFactory     datasource;
    private BoneCP connectionPool;
    
    private static final String FICHIER_PROPERTIES = "/superbetDAO/dao.properties";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_NOM_UTILISATEUR = "username";
	private static final String PROPERTY_MOT_DE_PASSE = "password";
	
	private String url;
	private String username;
	private String password;

    private DAOFactory() {
    	 Properties properties = new Properties();
    	 ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
         InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );
         String url;
         String driver;
         String nomUtilisateur;
         String motDePasse;

         if ( fichierProperties == null ) {
             throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
         }

         try {
             properties.load( fichierProperties );
             url = properties.getProperty( PROPERTY_URL );
             driver = properties.getProperty( PROPERTY_DRIVER );
             nomUtilisateur = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
             motDePasse = properties.getProperty( PROPERTY_MOT_DE_PASSE );
             
         } catch ( FileNotFoundException e ) {
             throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.", e );
         } catch ( IOException e ) {
             throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
         }

        try {
            // load the database driver (make sure this is in your classpath!)
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
        	throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
        }

        try {
            // setup the connection pool using BoneCP Configuration
            BoneCPConfig config = new BoneCPConfig();
            // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
            config.setJdbcUrl(url);
            config.setUsername(nomUtilisateur);
            config.setPassword(motDePasse);
            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(10);
            config.setPartitionCount(1);
            // setup the connection pool
            connectionPool = new BoneCP(config);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    public static DAOFactory getInstance() throws DAOConfigurationException {
        if (datasource == null) {
        	datasource = new DAOFactory();
            return datasource;
        } else {
            return datasource;
        }
    }

    public Connection getConnection() throws SQLException {
        return this.connectionPool.getConnection();
    }
    
    public CaissierDAO getCaissierDao() {
		  return new CaissierDAOImpl( this );
	  }	
	  
	  public EffChoicekDAO getEffChoicekDao(){
		  return new EffChoicekDAOImpl(this);
	  }
	  
	  public KenoDAO getKenoDao(){
		  return new KenoDAOImpl(this);
	  }
	  
	  public MisekDAO getMisekDao(){
		  return new MisekDAOImpl(this);
	  }
	  
	  public MisetDAO getMisetDao(){
		  return new MisetDAOImpl(this);
	  }
	  
	  public VersementDAO getVersementDao(){
		  return new VersementDAOImpl(this);
	  }
	  
	  public UtilDAO getUtilDao(){
		  return new UtilDAOImpl(this);
	  }
	  
	  public Misek_tempDAO getMisektpDao(){
		  return new Misek_tempDAOImpl(this);
	  }
	  
	  public PartnerDAO getPartnerDao(){
		  return new PartnerDAOImpl(this);
	  }
	  
	  public ConfigDAO getConfigDao(){
		  return new ConfigDAOImpl(this);
	  }
	  
	  public MisepDAO getMisepDao(){
		  return new MisepDAOImpl(this);
	  }
	  
	  public EffChoicepDAO getEffChoicepDao(){
		  return new EffChoicepDAOImpl(this);
	  }
	  
	  public SpinDAO getSpinDao(){
		  return new SpinDAOImpl(this);
	  }

	  public AirtimeDAO getAirtimeDao() {
		// TODO Auto-generated method stub
		return new AirtimeDAOImpl(this);
	}
	  
	  public GameCycleDAO getGameCycleDao() {
			// TODO Auto-generated method stub
			return new GameCycleDAOImpl(this);
		}
	  
	  public CagnotteDAO getCagotteDao() {
		  return new CagnotteDAOImpl(this);
	  }

}
