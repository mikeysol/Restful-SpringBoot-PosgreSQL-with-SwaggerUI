package com.zola.invoices.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "invoices")
@EntityListeners(AuditingEntityListener.class)
@ApiModel
@JsonIgnoreProperties(value = {"createdAt"},
        allowGetters = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Invoice {
    @Id
    @SequenceGenerator(name="invoices_id_seq",
            sequenceName="invoices_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="invoices_id_seq")
    private Long id;

    @ApiModelProperty(example = "ABC12345")
    @Column(nullable = false)
    private String invoiceNumber;

    @ApiModelProperty(example = "X1B23C4D5E")
    @Column(nullable = false)
    private String  poNumber;

    @ApiModelProperty(example = "100000")
    @Column(nullable = false)
    private BigInteger amountCents ;

    @ApiModelProperty(example = "2017-03-15")
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dueDate;

    @ApiModelProperty(example = "2017-04-15T01:02:03Z")
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
    @CreatedDate
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public BigInteger getAmountCents() {
        return amountCents;
    }

    public void setAmountCents(BigInteger amountCents) {
        this.amountCents = amountCents;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
