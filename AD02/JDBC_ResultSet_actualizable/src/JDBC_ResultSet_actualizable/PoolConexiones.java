package JDBC_ResultSet_actualizable;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author jm
 */
public class PoolConexiones {
    
    private static BasicDataSource pool;
    
    public static BasicDataSource getDataSource() {
        
        if (pool == null) {
            pool = new BasicDataSource();
            //pool.setUrl(url);
            //pool.setUsername(username);
            //pool.setPassword(password);
            pool.setInitialSize(3);
            pool.setMinIdle(3);
            pool.setMaxIdle(8);
            pool.setMaxTotal(8);
            pool.setMaxOpenPreparedStatements(100);
        }
        return pool;
    }
    
    
    
}
