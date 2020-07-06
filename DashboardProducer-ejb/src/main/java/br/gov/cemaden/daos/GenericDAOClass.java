package br.gov.cemaden.daos;

import  br.gov.cemaden.interfaces.GenericDAOInterface;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Marcus.costa
 * 
 */
public abstract class GenericDAOClass<T> implements GenericDAOInterface<T> {

	
        @PersistenceContext
	private EntityManager entityManager;
        
        private Class<T> entityClass;

	public GenericDAOClass() {						
	}

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * @return the entityClass
	 */
	public Class<T> getEntityClass() {
		return this.entityClass;
	}

	/**
	 * @param entityClass the entityClass to set
	 */
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public GenericDAOClass(Class<T> entityClass) {	// Construtor
		super();
		this.entityClass = entityClass;
	}
        
	@Override
	public Object save(T object) { 			// Salva
		this.entityManager.persist(object);
		return object;
	}
        
        
        @Override
        public Object saveComponente(T object){
            this.entityManager.merge(object);
            return object;
        }
      
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Object immediateSave(T object) { 				// Salva
		this.entityManager.persist(object);
		return object;
	}

	@Override
	public void delete(Integer objectID) { 					// Remove
		this.entityManager.remove(this.getObject(objectID));
	}
        
        @Override
	public void delete(Long objectID) { 					// Remove
		this.entityManager.remove(this.getObject(objectID));
	}
        
        

	@Override
	public T update(T object) {						// Atualiza
		return this.entityManager.merge(object);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Deprecated
	public T updade(T object) {						// Atualiza
		return this.entityManager.merge(object);
	}

	@Override
	public T getObject(Integer objectID) { // Retorna o objeto do tipo T identificado por objectID
		return this.entityManager.find(this.entityClass, objectID);
	}

	@Override
	public T getObject(Long objectID) { // Retorna o objeto do tipo T identificado por objectID
		return this.entityManager.find(this.entityClass, objectID);
	}

	@Override
	public T getObject(String objectID) { // Retorna o objeto do tipo T identificado por objectID
		return this.entityManager.find(this.entityClass, objectID);
	}

	@Override
	public List<T> getObject() { // Retorna uma lista com todos os objetos persistentes do tipo T
		CriteriaQuery<T> criteriaQuery = this.getCriteriaQuery();
		Root<T> root = criteriaQuery.from(this.entityClass);
		criteriaQuery.select(root);
		TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);//createQuery retorna String
		return typedQuery.getResultList();
	}

	public CriteriaQuery<T> getCriteriaQuery() {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		return criteriaBuilder.createQuery(this.entityClass);
	}

}