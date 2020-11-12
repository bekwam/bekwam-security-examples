package us.bekwam.security.examples.scheduler;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Form {

    private Long id;

    private String mydata;

    private FormStateType formStateType;

    private LocalDateTime receivedOn;

    private LocalDateTime processedOn;

    public Form() {
    }

    public Form(String mydata) {
        this.mydata = mydata;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMydata() {
        return mydata;
    }

    public void setMydata(String mydata) {
        this.mydata = mydata;
    }

    @Enumerated(EnumType.STRING)
    public FormStateType getFormStateType() {
        return formStateType;
    }

    public void setFormStateType(FormStateType formStateType) {
        this.formStateType = formStateType;
    }

    public LocalDateTime getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(LocalDateTime receivedOn) {
        this.receivedOn = receivedOn;
    }

    public LocalDateTime getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(LocalDateTime processedOn) {
        this.processedOn = processedOn;
    }
}
