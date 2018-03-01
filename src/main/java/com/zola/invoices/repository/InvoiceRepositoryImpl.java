package com.zola.invoices.repository;

import com.zola.invoices.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class InvoiceRepositoryImpl implements InvoiceRepositoryCustom {
    @Autowired
    EntityManager em;

    @Transactional
    @SuppressWarnings("unchecked")
    @Override
    public List<Invoice> findByInvoiceNumber(String invoiceNumber, int offset, int limit) {
        String query = "SELECT * FROM invoices inv "
                + "WHERE inv.invoice_number = :invoiceNumber "
                + "ORDER BY created_at DESC";
        Query nativeQuery = em.createNativeQuery(query,Invoice.class);
        nativeQuery.setParameter("invoiceNumber", invoiceNumber);
        nativeQuery.setFirstResult(offset);
        nativeQuery.setMaxResults(limit);
        return nativeQuery.getResultList();
    }

    @Override
    public List<Invoice> findByPoNumber(String poNumber, int offset, int limit) {
        String query = "SELECT * FROM invoices inv "
                + "WHERE inv.po_number = :poNumber "
                + "ORDER BY created_at DESC";
        Query nativeQuery = em.createNativeQuery(query,Invoice.class);
        nativeQuery.setParameter("poNumber", poNumber);
        nativeQuery.setFirstResult(offset);
        nativeQuery.setMaxResults(limit);
        return nativeQuery.getResultList();
    }

    @Override
    public List<Invoice> findByInvoiceNumberAndPoNumber(String invoiceNumber, String poNumber, int offset, int limit) {
        String query = "SELECT * FROM invoices inv "
                + "WHERE inv.invoice_number = :invoiceNumber "
                + "AND inv.po_number = :poNumber "
                + "ORDER BY created_at DESC";
        Query nativeQuery = em.createNativeQuery(query,Invoice.class);
        nativeQuery.setParameter("invoiceNumber", invoiceNumber);
        nativeQuery.setParameter("poNumber", poNumber);
        nativeQuery.setFirstResult(offset);
        nativeQuery.setMaxResults(limit);
        return nativeQuery.getResultList();
    }

    @Transactional
    @SuppressWarnings("unchecked")
    @Override
    public List<Invoice> findAllWithOffsetAndLimit(int offset, int limit) {
        String query = "SELECT * FROM invoices inv ORDER BY created_at DESC";
        Query nativeQuery = em.createNativeQuery(query,Invoice.class);
        nativeQuery.setFirstResult(offset);
        nativeQuery.setMaxResults(limit);
        return nativeQuery.getResultList();
    }
}
