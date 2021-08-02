package com.apress.springboot2recipes.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

//@Repository
@Transactional
class JpaCustomerRepository implements CustomerRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Customer> findAll() {
	return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    @Override
    public Customer findById(long id) {
	return em.find(Customer.class, id);
    }

    @Override
    public Customer save(Customer customer) {
	em.persist(customer);
	return customer;
    }
}
