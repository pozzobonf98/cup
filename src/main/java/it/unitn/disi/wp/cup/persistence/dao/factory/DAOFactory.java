package it.unitn.disi.wp.cup.persistence.dao.factory;

import it.unitn.disi.wp.cup.persistence.dao.DAO;
import it.unitn.disi.wp.cup.persistence.dao.PersonDAO;
import it.unitn.disi.wp.cup.persistence.dao.exception.DAOFactoryException;
import it.unitn.disi.wp.cup.persistence.dao.factory.jdbc.JDBCDAOFactory;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * This interface must be implemented by all the concrete
 * {@code DAOFactor(y)}ies
 *
 * @author Carlo Corradini
 */
public interface DAOFactory {

    /**
     * The name of the Dao Factory
     */
    String DAO_FACTORY = "daoFactory";

    /**
     * Shutdowns the connection to the storage system
     */
    void shutdown();

    /**
     * Returns the concrete {@link DAO dao} which type is the class passed as
     * parameter
     *
     * @param <DAO_CLASS>  The class name of the {@code dao} to get
     * @param daoInterface The class instance of the {@code dao} to get
     * @return The concrete {@code dao} which type is the class passed as
     * parameter
     * @throws DAOFactoryException If an error occurred during the operation
     */
    <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoInterface) throws DAOFactoryException;

    /**
     * Get the DAOFactory in the Context
     *
     * @return The DAOFactory instance
     * @throws DAOFactoryException If DAOFactory is null
     */
    static DAOFactory getDAOFactory() throws DAOFactoryException {
        DAOFactory daoFactory = (DAOFactory) ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getAttribute(DAO_FACTORY);
        if (daoFactory == null)
            throw new DAOFactoryException("Impossible to get dao factory for storage system");

        return daoFactory;
    }
}
