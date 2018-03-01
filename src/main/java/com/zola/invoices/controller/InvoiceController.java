package com.zola.invoices.controller;

import com.zola.invoices.model.Invoice;
import com.zola.invoices.repository.InvoiceRepository;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(basePath = "/v1/invoices", value = "Invoice", description = "API for Invoices", produces = "application/json")
@RestController
@RequestMapping(value = "/v1/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
public class InvoiceController {
    @Autowired
    InvoiceRepository invoiceRepository;

    /*
        A possible enhancement could've been done here by refactoring this getInvoices method to handle more parameters
        instead of separate getter methods for the invoice table based on specific parameters.

        This controller implementation prefers URI params but it could easily be the common URL paths like:
        v1/invoices/list?page=2
        v1/invoices/{id}/
        v1/invoices/invoiceNumber/{invoiceNumber}

        This seems nice as it naturally expresses what the api is doing and keeps services responsible for
        one purpose. But it would be better to do something like this:

        v1/invoices?invoiceNumber=1234&dueDate<=2018-09-12&groupBy=poNumber

        Querydsl would be better because for any complex enough API searching/filtering your resources by very
        simple fields is simply not enough. A query language is more flexible, and allows you to filter down to
        exactly the resources you need. This could also help avoid the code duplication of getter method based on
        property especially when we add new columns to a table in the future.
     */
    @GetMapping()
    @ApiOperation(value = "Get Invoices", notes = "Fetch List of Invoices")
    public List<Invoice> getInvoices(@ApiParam(name = "offset") @RequestParam(value ="offset",required = false) Optional<Integer> offset,
                                      @ApiParam(name = "limit") @RequestParam(value = "limit",required = false)  Optional<Integer> limit){
        if(offset.isPresent() || limit.isPresent()){
            return invoiceRepository.findAllWithOffsetAndLimit(offset.orElse(0),limit.orElse(20));
        }
        return invoiceRepository.findAll().stream().sorted(
                Comparator.comparing(Invoice::getCreatedAt).reversed()
        ).collect(Collectors.toList());
    }

    @GetMapping(params = "id")
    @ApiOperation(value = "Get Invoice by id", notes = "Fetch an Invoice by id")
    public Invoice getInvoiceById(@ApiParam(name = "id", value = "id") @RequestParam String id){
        return invoiceRepository.findOne(Long.parseLong(id));
    }

    @GetMapping(params = "invoiceNumber")
    @ApiOperation(value = "Get Invoice by invoice_number", notes = "Fetch List of Invoices with invoice_number")
    public List<Invoice> getInvoiceByInvoiceNumber(@ApiParam(name = "invoiceNumber", value = "invoice_number")@RequestParam String invoiceNumber,
                                                   @ApiParam(name = "offset") @RequestParam(value ="offset",required = false) Optional<Integer> offset,
                                                   @ApiParam(name = "limit") @RequestParam(value = "limit",required = false)  Optional<Integer> limit){
        if(offset.isPresent() || limit.isPresent()){
            return invoiceRepository.findByInvoiceNumber(invoiceNumber,offset.orElse(0),limit.orElse(20));
        }
        return invoiceRepository.findByInvoiceNumber(invoiceNumber);
    }

    @GetMapping(params = "poNumber")
    @ApiOperation(value = "Get Invoice by po_number", notes = "Fetch List of Invoices by po_number")
    public List<Invoice> getInvoiceByPoNumber(@ApiParam(name = "poNumber", value = "po_number") @RequestParam String poNumber,
                                              @ApiParam(name = "offset") @RequestParam(value ="offset",required = false) Optional<Integer> offset,
                                              @ApiParam(name = "limit") @RequestParam(value = "limit",required = false)  Optional<Integer> limit){
        if(offset.isPresent() || limit.isPresent()){
            return invoiceRepository.findByPoNumber(poNumber,offset.orElse(0),limit.orElse(20));
        }
        return invoiceRepository.findByPoNumber(poNumber);
    }

    @GetMapping(params = {"invoiceNumber","poNumber"})
    @ApiOperation(value = "Get Invoice by invoice_number and po_number", notes = "Fetch List of Invoices with invoice_number and po_number")
    public List<Invoice> getInvoiceByInvoiceNumberAndPoNumber(@ApiParam(name = "invoiceNumber", value = "invoice_number")@RequestParam String invoiceNumber,
                                                              @ApiParam(name = "poNumber", value = "po_number") @RequestParam String poNumber,
                                                              @ApiParam(name = "offset") @RequestParam(value ="offset",required = false) Optional<Integer> offset,
                                                              @ApiParam(name = "limit") @RequestParam(value = "limit",required = false)  Optional<Integer> limit){
        if(offset.isPresent() || limit.isPresent()){
            return invoiceRepository.findByInvoiceNumberAndPoNumber(invoiceNumber,poNumber,offset.orElse(0),limit.orElse(20));
        }
        return invoiceRepository.findByInvoiceNumberAndPoNumber(invoiceNumber,poNumber);
    }

    @PostMapping()
    @ApiOperation(value = "Add an Invoice", notes = "Make a POST like the following JSON and properties:\n" +
            "{\n" +
            "  \"amount_cents\": 100000,\n" +
            "  \"due_date\": \"2017-03-15\",\n" +
            "  \"invoice_number\": \"ABC12345\",\n" +
            "  \"po_number\": \"X1B23C4D5E\"\n" +
            "}")
    public Invoice createInvoice(@ApiParam(name = "invoice", value = "Invoice") @Valid @RequestBody Invoice invoice) {
        return invoiceRepository.save(invoice);
    }
}
