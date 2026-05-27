package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import constants.JpaConst;

public class DBUtil {
    
    private static EntityManagerFactory emf;
    
    public static EntityManager creatEntityManager() {
        return _getEntityManagerFactory().createEntityManager();
    }
    
    private static EntityManagerFactory _getEntityManagerFactory() {
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory(JpaConst.PERSISTENCE_UNIT_NAME);
        }
        
        return emf;
    }
    
}
