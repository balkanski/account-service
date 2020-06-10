package com.example.cc2.service.model;


import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Date;
import java.util.UUID;

public class Account {

    private UUID uuid;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Date dob;

    public Account(UUID uuid, String firstName, String lastName, String emailAddress, Date dob) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.dob = dob;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Date getDob() {
        return dob;
    }

    public static class Builder{
        private UUID uuid;
        private String firstName;
        private String lastName;
        private String emailAddress;
        private Date dob;

        public Builder withUUID(UUID uuid){
            this.uuid = uuid;
            return this;
        }

        public Builder withFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder withEmailAddress(String emailAddress) throws AddressException {

            if(isValidEmailAddress(emailAddress)) {

                this.emailAddress = emailAddress;
                return this;
            } else throw new AddressException("Invalid Email Address");
        }

        public Builder withDOB(Date dob){
            this.dob = dob;
            return this;
        }

        public Account build(){
            return new Account(uuid, firstName, lastName, emailAddress, dob);
        }

        private boolean isValidEmailAddress(String email) {
            boolean result = true;
            try {
                InternetAddress emailAddr = new InternetAddress(email);
                emailAddr.validate();
            } catch (AddressException ex) {
                result = false;
            }
            return result;
        }
    }


}
