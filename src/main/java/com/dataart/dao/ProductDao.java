package com.dataart.dao;

import com.dataart.entity.Product;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProductDao extends BaseDao {

    public List<Product> getProductsPage(final Long groupId, final Long pageIndex, final Long pageSize, final String sortColumnName, final String sortDirection){

        return getJpaTemplate().executeFind(new JpaCallback<Object>() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
                String sortString = " order by " + sortColumnName + " " + sortDirection;

                Query query = em.createQuery("from Product p where p.groupId=:groupId " + sortString);

                query.setParameter("groupId", groupId);
                query.setFirstResult((pageIndex.equals(1L) ? 0 : (pageIndex.intValue() - 1) * pageSize.intValue()));
                query.setMaxResults(pageSize.intValue() + 1);
                return query.getResultList();
            }
        });
    }

}
