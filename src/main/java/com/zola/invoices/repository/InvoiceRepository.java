package com.zola.invoices.repository;

import com.zola.invoices.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface InvoiceRepository extends JpaRepository<Invoice, Long> , InvoiceRepositoryCustom {

    @Query(value = "SELECT * FROM invoices inv WHERE inv.invoice_number = :invoiceNumber ORDER BY inv.created_at DESC",
            nativeQuery = true)
    List<Invoice> findByInvoiceNumber(@Param("invoiceNumber") String invoiceNumber);

    @Query(value = "SELECT * FROM invoices inv WHERE inv.po_number = :poNumber ORDER BY inv.created_at DESC",
            nativeQuery = true)
    List<Invoice> findByPoNumber(@Param("poNumber") String poNumber);

    @Query(value = "SELECT * FROM invoices inv WHERE inv.invoice_number = :invoiceNumber " +
            "AND inv.po_number = :poNumber ORDER BY inv.created_at DESC",
            nativeQuery = true)
    List<Invoice> findByInvoiceNumberAndPoNumber(@Param("invoiceNumber") String invoiceNumber, @Param("poNumber") String poNumber);
}
