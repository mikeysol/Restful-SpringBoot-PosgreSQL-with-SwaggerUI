package com.zola.invoices.repository;

import com.zola.invoices.model.Invoice;

import java.util.List;

public interface InvoiceRepositoryCustom {

    List<Invoice> findAllWithOffsetAndLimit(int offset, int limit);
    List<Invoice> findByInvoiceNumber(String invoiceNumber, int offset, int limit);
    List<Invoice> findByPoNumber(String poNumber, int offset, int limit);
    List<Invoice> findByInvoiceNumberAndPoNumber(String invoiceNumber, String poNumber, int offset, int limit);
}
