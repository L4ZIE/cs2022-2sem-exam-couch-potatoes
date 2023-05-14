package be;

import javafx.scene.text.Text;

public class Project {
    private String refNumber;
    private String customerName;
    private String customerEmail;
    private String customerLocation;
    private String note;
    private String drawing;
    private String creationDate;
    private String startDate;
    private String endDate;
    private Boolean approved;

    private Boolean privateProject;

    public Project (String refNumber, String customerName, String customerEmail,
                    String customerLocation, String note, String drawing, String creationDate,
                    String startDate, String endDate, Boolean approved, Boolean privateProject)
    {
        this.refNumber = refNumber;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerLocation = customerLocation;
        this.note = note;
        this.drawing = drawing;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.approved = approved;
        this.privateProject = privateProject;
    }

    public String getRefNumber() {
        return refNumber;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerEmail() {
        return customerEmail;
    }
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    public String getCustomerLocation() {
        return customerLocation;
    }
    public void setCustomerLocation(String customerLocation) {
        this.customerLocation = customerLocation;
    }
    public String  getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getDrawing() {
        return drawing;
    }
    public void setDrawing(String drawing) {
        this.drawing = drawing;
    }
    public String getCreationDate() {
        return creationDate;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public Boolean getApproved() {
        return approved;
    }
    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
    public void setPrivateProject (Boolean privateProject){this.privateProject = privateProject;}
    public Boolean getPrivateProject(){return privateProject;}
    @Override
    public String toString() {
        return refNumber + " " + customerName + " " + customerEmail+ " " + customerLocation + " " + note+ " " +
                drawing + " " + creationDate  + " " + startDate  + " " + endDate  + " " + approved;
    }
}
