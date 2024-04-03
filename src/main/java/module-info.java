module com.carhiring.carhiring {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires javafx.media;
    requires log4j;
    requires java.persistence;
    requires jakarta.persistence;


    opens com.carhiring.carhiring.data.entities to org.hibernate.orm.core;
    exports com.carhiring.carhiring.data.entities;
    exports com.carhiring.carhiring.presentation.controllers;
    opens com.carhiring.carhiring.presentation.controllers to javafx.fxml;
    exports com.carhiring.carhiring;
    opens com.carhiring.carhiring to javafx.fxml;
}