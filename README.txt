title: Restful Spring Boot Application with JPA and Postgresql
author: Michael Barnes
email: michaelbarnesmail@gmail.com

description: This is a demo to retrieve and insert Invoices into a DB from a RESTful Web Api.
    You can access the Api through the springfox Swagger UI //localhost:8080/swagger-ui.html .
    There are some known bugs with the springfox Swagger UI with RequestParameters and URLTemplating
    enabled. If there are issues with swagger UI then hit the URL endpoint directly. You can also
    use the chrome tool Postman for HTTP Rq/Rs.

    To retrieve a list of invoices you hit the datasource at localhost:8080/v1/invoices
    and can append the request with URI template paramaters like localhost:8080/v1/invoices?id=8
    or localhost:8080/v1/invoices?invoiceNumber=1234&poNumber=ABC56

    Insert an Invoice with a POST to //localhost:8080/v1/invoices with a JSON in request body like
    {
        "amount_cents": 999999,
        "due_date": "2018-03-17",
        "invoice_number": "ABC12345",
        "po_number": "X1B23C4D5E"
     }

     And the response should be:

     {
       "id": 18,
       "invoice_number": "ABC12345",
       "po_number": "X1B23C4D5E",
       "amount_cents": 999999,
       "due_date": "2018-03-17",
       "created_at": "2018-02-28T08:59:36+0000"
     }


PostgreSQL DB Setup:
//localhost:5432/invoices

    CREATE TABLE invoices ( 
        id SERIAL,
         invoice_number VARCHAR(64) NOT NULL, 
        po_number VARCHAR(64) NOT NULL, 
        due_date DATE NOT NULL, 
        amount_cents BIGINT NOT NULL,
         created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
        );

    CREATE ROLE zola WITH LOGIN;
    GRANT ALL PRIVILEGES ON TABLE invoices TO zola;
    GRANT USAGE, SELECT ON SEQUENCE invoices_id_seq TO zola;

Spring Boot application properties:
src/main/resources/application.properties

    spring.datasource.url = jdbc:postgresql://localhost:5432/invoices
    spring.datasource.username = zola

    spring.jpa.database-platform = org.hibernate.dialect.PostgreSQL94Dialect
    spring.jpa.hibernate.ddl-auto = update

Swagger UI for clientside Api documentation and implementation:
    http://localhost:8080/swagger-ui.html

Api Endpoints:
    http://localhost:8080
        GET /v1/invoices{?offset,limit}
        GET /v1/invoices{?offset,limit,poNumber}
        GET /v1/invoices{?offset,limit,invoiceNumber}
        GET /v1/invoices{?offset,limit,invoiceNumber,poNumber}
        GET /v1/invoices{?id}
        POST /v1/invoices

Build App:
dir:/ZolaInvoices

    $ gradle build

Run App:
dir:/ZolaInvoices

    $ gradle bootRun