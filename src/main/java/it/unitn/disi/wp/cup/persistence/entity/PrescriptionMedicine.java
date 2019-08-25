package it.unitn.disi.wp.cup.persistence.entity;

import java.time.LocalDateTime;

/**
 * Entity Prescription Medicine
 *
 * @author Carlo Corradini
 */
public class PrescriptionMedicine {
    private Long id;
    private Long personId;
    private Long DoctorId;
    private LocalDateTime dateTime;
    private Medicine medicine;
    private Short quantity;
    private Boolean paid;

    /**
     * Return the id of the Prescription Medicine
     *
     * @return Prescription Medicine id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the Prescription Medicine id
     *
     * @param id Prescription Medicine id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Return the {@link Person person} id of the Prescription Medicine
     *
     * @return Prescription Medicine {@link Person person} id
     */
    public Long getPersonId() {
        return personId;
    }

    /**
     * Set the {@link Person person} id of the Prescription Medicine
     *
     * @param personId Prescription Medicine {@link Person person} id
     */
    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    /**
     * Return the {@link Doctor doctor} id of the Prescription Medicine
     *
     * @return Prescription Medicine {@link Doctor doctor} id
     */
    public Long getDoctorId() {
        return DoctorId;
    }

    /**
     * Set the {@link Doctor doctor} id of the Prescription Medicine
     *
     * @param doctorId Prescription Medicine {@link Doctor doctor} id
     */
    public void setDoctorId(Long doctorId) {
        DoctorId = doctorId;
    }

    /**
     * Return the Date and Time of the Prescription Medicine
     *
     * @return Prescription Medicine Date and Time
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Set the Date and Time of the Prescription Medicine
     *
     * @param dateTime Prescription Medicine Date and Time
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Return the medicine of the Prescription Exam
     *
     * @return Prescription Exam Medicine
     */
    public Medicine getMedicine() {
        return medicine;
    }

    /**
     * Set the medicine of the Prescription Exam
     *
     * @param medicine Prescription Exam Medicine
     */
    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    /**
     * Return the quantity of the Medicine
     *
     * @return Prescription Exam quantity
     */
    public Short getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity of the Prescription Exam
     *
     * @param quantity Prescription Exam quantity
     */
    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    /**
     * Return if the {@link PrescriptionMedicine prescription} has been paid
     *
     * @return True if paid, false otherwise
     */
    public Boolean getPaid() {
        return paid;
    }

    /**
     * Set if the {@link PrescriptionMedicine prescription} has been paid
     *
     * @param paid True if paid, false otherwise
     */
    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    /**
     * Return the amount of cents to pay for the {@link PrescriptionMedicine Prescription Medicine}.
     * The amount is calculated as: {@code quantity} * {@code medicine.getPrice}
     *
     * @return The amount of cents to pay
     */
    public Integer getTotalToPay() {
        if (quantity == null || medicine == null) {
            return 0;
        }
        return quantity * medicine.getPrice();
    }
}
