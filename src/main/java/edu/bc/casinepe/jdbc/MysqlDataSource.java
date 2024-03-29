package edu.bc.casinepe.jdbc;



/*import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;   */

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.mahout.cf.taste.impl.model.jdbc.ConnectionPoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/*
 Some code is from http://svn.apache.org/repos/asf/commons/proper/dbcp/branches/TEST_DBCP_1_3_BRANCH/doc/ManualPoolingDataSourceExample.java
 */
public class MysqlDataSource {
    private static Logger logger = LoggerFactory.getLogger(MysqlDataSource.class.getName());
    public static DataSource dataSource = null;

    private MysqlDataSource() {}

    /*public static DataSource getDataSource() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

            // First, we'll create a ConnectionFactory that the
        // pool will use to create Connections.
        // We'll use the DriverManagerConnectionFactory,
        // using the connect string passed in the command line
        // arguments.
        //
        ConnectionFactory connectionFactory =
                new DriverManagerConnectionFactory("jdbc:mysql://localhost/movie_recommender", null);

        //
        // Next we'll create the PoolableConnectionFactory, which wraps
        // the "real" Connections created by the ConnectionFactory with
        // the classes that implement the pooling functionality.
        //
        PoolableConnectionFactory poolableConnectionFactory =
                new PoolableConnectionFactory(connectionFactory, null);

        //
        // Now we'll need a ObjectPool that serves as the
        // actual pool of connections.
        //
        // We'll use a GenericObjectPool instance, although
        // any ObjectPool implementation will suffice.
        //
        ObjectPool<PoolableConnection> connectionPool =
                new GenericObjectPool<PoolableConnection>(poolableConnectionFactory);

        // Set the factory's pool property to the owning pool
        poolableConnectionFactory.setPool(connectionPool);

        //
        // Finally, we create the PoolingDriver itself,
        // passing in the object pool we created.
        //
        PoolingDataSource<PoolableConnection> dataSource =
                new PoolingDataSource<PoolableConnection>(connectionPool);

        return new ConnectionPoolDataSource(dataSource);
    }*/

    public static DataSource getDataSource() {
        if (dataSource == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // First, we'll need a ObjectPool that serves as the
            // actual pool of connections.
            //
            // We'll use a GenericObjectPool instance, although
            // any ObjectPool implementation will suffice.
            //
            GenericObjectPool.Config config = new GenericObjectPool.Config();
            config.maxActive = 150;
            config.maxIdle = 100;
            config.minIdle = 30;
            config.maxWait = 1000;

            ObjectPool connectionPool = new GenericObjectPool(null, config);

            //
            // Next, we'll create a ConnectionFactory that the
            // pool will use to create Connections.
            // We'll use the DriverManagerConnectionFactory,
            // using the connect string passed in the command line
            // arguments.
            //

            ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
                /*"jdbc:mysql://movie-recommender.cunjeovcml9v.us-east-1.rds.amazonaws.com:3306/movie_recommender", "pcasinelli", "alvarez2014"*/
                    "jdbc:mysql://localhost/movie_recommender", "root", "");


            //
            // Now we'll create the PoolableConnectionFactory, which wraps
            // the "real" Connections created by the ConnectionFactory with
            // the classes that implement the pooling functionality.
            //
            PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
                    connectionFactory, connectionPool, null, null, false, true);

            //
            // Finally, we create the PoolingDriver itself,
            // passing in the object pool we created.
            //
            PoolingDataSource poolingDataSource = new PoolingDataSource(connectionPool);

            dataSource = new ConnectionPoolDataSource(poolingDataSource);
        }

        return dataSource;
    }

}
