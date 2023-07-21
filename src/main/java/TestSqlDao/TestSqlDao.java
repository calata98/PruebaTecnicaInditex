package TestSqlDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

/**
 * Mejorar cada uno de los métodos a nivel SQL y código cuando sea necesario
 * Razonar cada una de las mejoras que se han implementado
 * No es necesario que el código implementado funcione
 */
public class TestSqlDao {

    private static TestSqlDao instance = new TestSqlDao();
    private Hashtable<Long, Long> maxOrderUser;

    private TestSqlDao() {

    }

    static TestSqlDao getInstance() {

        return instance;
    }

    /*
    En todas las querys se ha eliminado String.format para usar PreparedStatement ya que de esta manera se evita
    la inyección de SQL
    Además en todos los metodos se manejan las excepciones del tipo SQLException
     */
    /**
     * Obtiene el ID del último pedido para cada usuario
     */
    public Hashtable<Long, Long> getMaxUserOrderId(long idTienda) throws SQLException {

        try {
            /*
            Query mejorada para obtener directamente el mayor pedido de cada usuario eliminado
            */
            String query = "SELECT ID_USUARIO, MAX(ID_PEDIDO) AS MAX_ORDER_ID FROM PEDIDOS WHERE ID_TIENDA = ? GROUP BY ID_USUARIO";
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, idTienda);
            ResultSet rs = stmt.executeQuery();
            maxOrderUser = new Hashtable<Long, Long>();

            while (rs.next()) {
                long idUsuario = rs.getLong("ID_USUARIO");
                long maxOrderId = rs.getLong("MAX_ORDER_ID");
                maxOrderUser.put(idUsuario, maxOrderId);
            }

            return maxOrderUser;
        }catch (SQLException e) {
            //Codigo de manejo de excepcion
            throw e;
        }
    }



    /**
     * Copia todos los pedidos de un usuario a otro
     */
    public void copyUserOrders(long idUserOri, long idUserDes) throws SQLException {

        try {
            /*
            Se utiliza una consulta insert into select en vez de realizar una de seleccion y una de insercion en bucle
             */
            String query = "INSERT INTO PEDIDOS (ID_USUARIO, ID_TIENDA, FECHA, TOTAL, SUBTOTAL, DIRECCION) " +
                    "SELECT ?, ID_TIENDA, FECHA, TOTAL, SUBTOTAL, DIRECCION FROM PEDIDOS WHERE ID_USUARIO = ?";
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, idUserDes);
            stmt.setLong(2, idUserOri);
            stmt.executeUpdate();
        } catch (SQLException e) {
            //Codigo de manejo de excepcion
            throw e;
        }
    }


    /**
     * Obtiene los datos del usuario y pedido con el pedido de mayor importe para la tienda dada
     */
    /*
    Se eliminan los parametros que no se usan del metodo y se cambia el tipo que devuelve a String, para mostrar un output más claro
    */
    public String getUserMaxOrder(long idTienda) throws Exception {

        try {
        /*
        Se mejora la query de tal manera que se obtiene el usuario y el pedido con el mayor importe ordenando los resultados
        por el campo total en orden descendente y limitando el resultado a 1
        */
            String query = "SELECT U.ID_USUARIO, P.ID_PEDIDO, P.TOTAL, U.NOMBRE, U.DIRECCION FROM PEDIDOS AS P " +
                    "INNER JOIN USUARIOS AS U ON P.ID_USUARIO = U.ID_USUARIO WHERE P.ID_TIENDA = ? ORDER BY P.TOTAL DESC LIMIT 1";
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, idTienda);
            ResultSet rs = stmt.executeQuery();

            double total = 0;
            long userId = 0;
            long orderId = 0;
            String name = "";
            String address = "";
            String result = "";

            if (rs.next()) {
                total = rs.getLong("TOTAL");
                userId = rs.getLong("ID_USUARIO");
                orderId = rs.getLong("ID_PEDIDO");
                name = rs.getString("NOMBRE");
                address = rs.getString("DIRECCION");

                result = "MAX_ORDER FOR STORE " + idTienda +
                        "\nOrderId: " + orderId + " Total: " + total +
                        "\nUserId: " + userId + " Name: " + name + " Address: " + address;
            }else {
                result = "No orders for the store " + idTienda;
            }



            return result;
        } catch (SQLException e) {
            //Codigo de manejo de excepcion
            throw e;
        }
    }


    private Connection getConnection() throws SQLException {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda_db", "user", "password");
        }catch (SQLException e) {
            //Codigo de manejo de excepcion
            throw e;
        }

        return conn;
    }
}
