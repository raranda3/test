/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.daos;

import Dtos.ProfesorDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilidades.Conectar;
import utilidades.MiExcepcion;

/**
 *
 * @author ProfesorDAO
 * @author Rodrigo Aranda Fernandez
 */
public class ProfesorDAO {

    PreparedStatement pstmt = null;
    Connection cnn = null;
    ResultSet rs = null;

    public ProfesorDAO() {
        cnn = Conectar.getInstance();
    }

    /**
     * Este metodo permite insertar un nuevo registro profesor en la base de
     * datos
     *
     * @param newProfesor Un objeto que contiene los atributos de la entidad
     * Profesor
     * @return una cadena de confirmación de la acción realizada
     */
    public String crearProfesor(ProfesorDTO newProfesor) throws MiExcepcion {
        String salida = "";
        try {

            int resultado = 0;
            pstmt = cnn.prepareStatement("INSERT INTO profesores VALUES (null, ?, ?, ?)");
            pstmt.setString(1, newProfesor.getNombreCompleto());
            pstmt.setString(2, newProfesor.getCorreoElectronico());
            pstmt.setString(3, newProfesor.getUserName());
            resultado = pstmt.executeUpdate();

            if (resultado != 0) {
                salida = "El usuario a sido registrado exitosamente. " + resultado + "filas afectadas";
            } else {
                // salida = "Ha ocurrido un problema al crear el profesor. Contacte al administrador";
                throw new MiExcepcion("ha ocurrido un problema");
            }
        } catch (SQLException sqle) {
            salida = "Ocurrió la siguiente exception : " + sqle.getMessage();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex); // se deja por defecto en este caso
            }
        }

        return salida;

    }

    /**
     * Este método permite listar los profesores disponibles en la tabla
     *
     */
    public List<ProfesorDTO> getAll() throws MyException, SQLException {
        LinkedList<ProfesorDTO> listaProfes = new LinkedList<ProfesorDTO>();
        try {


            String query = "SELECT  clave_profesor as id, nombre, correoElectronico, user "
                    + " FROM profesores ";
            pstmt = cnn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProfesorDTO newProfe = new ProfesorDTO();
                newProfe.setClaveProfesor(rs.getInt("id"));
                newProfe.setNombreCompleto(rs.getString("nombre"));
                newProfe.setCorreoElectronico(rs.getString("correoElectronico"));
                newProfe.setUserName(rs.getString("user"));
                listaProfes.add(newProfe);
            }
        } catch (SQLException ex) {
            throw new MyException("Error al listar los elementos " + ex.getSQLState() + " - " + ex.getMessage());
        } finally {
            pstmt.close();
        }

        return listaProfes;
    }

    public String validarExistenciaNick(String campo) throws MyException {

        String salida = "";
        try {

            int resultado = 0;
            pstmt = cnn.prepareStatement("SELECT COUNT(*) AS total FROM profesores WHERE user = ?");
            pstmt.setString(1, campo);
            rs = pstmt.executeQuery();


            if (rs != null) {
                while (rs.next()) {
                    resultado = rs.getInt("total");
                    if (resultado != 0) {
                        salida = "El usuario no esta disponible " + resultado;
                    } else {
                        salida = "nombre de usuario disponible " + resultado;
                    }
                }
            }
        } catch (SQLException sqle) {
            throw new MyException("Ocurrió la siguiente exception : " + sqle.getMessage());
        } finally {
            try {
                pstmt.close();
            } catch (SQLException ex) {
                throw new MyException("Ocurrió la siguiente exception : " + ex.getMessage());
            }
        }

        return salida;

    }
}
