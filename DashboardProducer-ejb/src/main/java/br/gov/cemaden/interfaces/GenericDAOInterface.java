/**
 * 
 */
package br.gov.cemaden.interfaces;

import java.util.List;


/**
 * @author CTI
 * 
 */
public interface GenericDAOInterface<T> {

	/**
	 * Persiste um objeto no banco de dados imediatamente, com uma nova transacao
	 * @param object
	 * @author Alberto Barbosa
	 */
	public Object immediateSave(T object);

	/**
	 * Persiste um objeto no banco de dados
	 * @param object
	 * @author Anderson Carlos Ferreira da Silva
	 */
	public Object save(T object);

        /**
         * Persiste um componente no Banco de Dados
         * @param object
         * @author Marcus Costa
         */
        public Object saveComponente(T object); // persistencia tabela sgrp-item-pcd
        
	/**
	 * Remove registro referente ao objeto no banco de dados
	 * @param object
	 * @author Anderson Carlos Ferreira da Silva
	 */
	public void delete(Integer objectID);
        
        /**
         * Remove registro referente ao objeto no banco de dados
         * @param objectID 
         * @author Marcus Costa
         */
        public void delete(Long objectID);
        
        
	/**
	 * Altera o registro do objeto no banco de dados, na mesma transação.
	 * @param object
	 * @return object
	 */
	public T update(T object);

	/**
	 * Altera o registro do objeto no banco de dados, criando uma nova transação.
	 * @param object
	 * @return object
	 * @author Anderson Carlos Ferreira da Silva
	 */
	@Deprecated
	public T updade(T object);

	/**
	 * Retorna um objeto do banco de dados apartir do seu identificador
	 * @param object
	 * @return object
	 * @author Anderson Carlos Ferreira da Silva
	 */
	public T getObject(Integer objectID);

	/**
	 * Retorna um objeto do banco de dados apartir do seu identificador
	 * @param object
	 * @return object
	 * @author Anderson Carlos Ferreira da Silva
	 */
	public T getObject(String objectID);

	/**
	 * Retorna um objeto do banco de dados apartir do seu identificador
	 * @param object
	 * @return object
	 * @author Anderson Carlos Ferreira da Silva
	 */
	public T getObject(Long objectID);

	/**
	 * Retorna uma lista de objetos do banco de dados
	 * @param object
	 * @return object
	 * @author Anderson Carlos Ferreira da Silva
	 */
	public List<T> getObject();

}
