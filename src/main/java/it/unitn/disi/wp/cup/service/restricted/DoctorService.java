package it.unitn.disi.wp.cup.service.restricted;

import it.unitn.disi.wp.cup.config.AppConfig;
import it.unitn.disi.wp.cup.service.model.prescription.PrescriptionExamModel;
import it.unitn.disi.wp.cup.service.model.prescription.PrescriptionMedicineModel;
import it.unitn.disi.wp.cup.persistence.dao.*;
import it.unitn.disi.wp.cup.persistence.dao.exception.DAOException;
import it.unitn.disi.wp.cup.persistence.dao.exception.DAOFactoryException;
import it.unitn.disi.wp.cup.persistence.dao.factory.DAOFactory;
import it.unitn.disi.wp.cup.persistence.entity.*;
import it.unitn.disi.wp.cup.util.AuthUtil;
import it.unitn.disi.wp.cup.util.EmailUtil;
import it.unitn.disi.wp.cup.util.obj.JsonMessage;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Services for Authenticated Doctor
 *
 * @author Carlo Corradini
 */
@Path("doctor")
public class DoctorService {
    private static final Logger LOGGER = Logger.getLogger(DoctorService.class.getName());

    private Doctor doctor = null;
    private DoctorDAO doctorDAO = null;
    private PersonDAO personDAO = null;
    private MedicineDAO medicineDAO = null;
    private ExamDAO examDAO = null;
    private PrescriptionMedicineDAO prescriptionMedicineDAO = null;
    private PrescriptionExamDAO prescriptionExamDAO = null;

    @Context
    private HttpServletRequest request;

    @Context
    public void setServletContext(ServletContext servletContext) {
        if (servletContext != null) {
            try {
                doctor = AuthUtil.getAuthDoctor(request);
                doctorDAO = DAOFactory.getDAOFactory(servletContext).getDAO(DoctorDAO.class);
                personDAO = DAOFactory.getDAOFactory(servletContext).getDAO(PersonDAO.class);
                medicineDAO = DAOFactory.getDAOFactory(servletContext).getDAO(MedicineDAO.class);
                examDAO = DAOFactory.getDAOFactory(servletContext).getDAO(ExamDAO.class);
                prescriptionMedicineDAO = DAOFactory.getDAOFactory(servletContext).getDAO(PrescriptionMedicineDAO.class);
                prescriptionExamDAO = DAOFactory.getDAOFactory(servletContext).getDAO(PrescriptionExamDAO.class);
            } catch (DAOFactoryException ex) {
                LOGGER.log(Level.SEVERE, "Impossible to get dao factory for storage system", ex);
            }
        }
    }

    /**
     * Given a valid {@link PrescriptionMedicineModel} and the Authenticated {@link Doctor doctor}
     * add the Prescription Medicine to the Persistence System
     *
     * @param prescriptionMedicineModel The Medicine Prescription Model to add
     * @return A JSON @{code {@link JsonMessage message}}
     */
    @POST
    @Path("prescription_medicine")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String prescriptionMedicine(PrescriptionMedicineModel prescriptionMedicineModel) {
        JsonMessage message = new JsonMessage();
        PrescriptionMedicine prescriptionMedicine = new PrescriptionMedicine();
        Person patient;
        Medicine medicine;

        if (doctor != null && prescriptionMedicineModel.isValid()) {
            try {
                if (doctor.equals(doctorDAO.getDoctorByPatientId(prescriptionMedicineModel.getPatientId()))
                        && (patient = personDAO.getByPrimaryKey(prescriptionMedicineModel.getPatientId())) != null
                        && (medicine = medicineDAO.getByPrimaryKey(prescriptionMedicineModel.getMedicineId())) != null) {
                    // The Authenticated Doctor is the Doctor of the Patient
                    //  and The Patient exists
                    //  and The Medicine exists
                    prescriptionMedicine.setPersonId(patient.getId());
                    prescriptionMedicine.setDoctorId(doctor.getId());
                    prescriptionMedicine.setMedicine(medicine);
                    prescriptionMedicine.setQuantity(prescriptionMedicineModel.getQuantity());

                    // Add the new Prescription Medicine
                    if (prescriptionMedicineDAO.add(prescriptionMedicine) != null) {
                        // Added successfully
                        message.setError(JsonMessage.ERROR_NO_ERROR);
                        // Generate the html string for the email
                        String html =
                                "<h1 style=\"color: #5e9ca0;\">Nuova prescrizione <span style=\"color: #2b2301;\">farmaco</span>!</h1>" +
                                "<p>" +
                                    "Ciao <span style=\"color: #2b2301;\"><b>" + patient.getName() + "</b></span>!<br>" +
                                    "Il tuo medico di base ha aggiunto una prescrizione per te.<br>" +
                                    "Ecco qui un breve riassunto:<br>" +
                                    "<b>Farmaco</b>: <span style=\"color: #2e6c80;\">" + medicine.getName() + "</span><br>" +
                                    "<b>Quantità</b>: <span style=\"color: #2e6c80;\">" + prescriptionMedicineModel.getQuantity() + "</span><br>" +
                                    "<br>" +
                                "</p>";

                        EmailUtil.sendHTML(patient.getEmail(),
                                AppConfig.getName().toUpperCase() + " prescrizione farmaci",
                                html);
                    }
                }
            } catch (DAOException ex) {
                LOGGER.log(Level.SEVERE, "Unable to Prescribe a Medicine", ex);
            }
        }

        return message.toJsonString();
    }

    /**
     * Given a valid {@link PrescriptionExamModel} and the Authenticated {@link Doctor doctor}
     * add the Prescription Exam to the Persistence System
     *
     * @param prescriptionExamModel The Exam Prescription Model to add
     * @return A JSON @{code {@link JsonMessage message}}
     */
    @POST
    @Path("prescription_exam")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String prescriptionExam(PrescriptionExamModel prescriptionExamModel) {
        JsonMessage message = new JsonMessage();
        PrescriptionExam prescriptionExam = new PrescriptionExam();
        Person patient;
        Exam exam;

        if (doctor != null && prescriptionExamModel.isValid()) {
            try {
                if (doctor.equals(doctorDAO.getDoctorByPatientId(prescriptionExamModel.getPatientId()))
                        && (patient = personDAO.getByPrimaryKey(prescriptionExamModel.getPatientId())) != null
                        && (exam = examDAO.getByPrimaryKey(prescriptionExamModel.getExamId())) != null) {
                    // The Authenticated Doctor is the Doctor of the Patient
                    //  and The Patient exists
                    //  and The Exam exists
                    prescriptionExam.setPersonId(patient.getId());
                    prescriptionExam.setDoctorId(doctor.getId());
                    prescriptionExam.setExam(exam);
                    prescriptionExam.setPaid(prescriptionExamModel.isPaid());

                    // Add the new Prescription Exam
                    if (prescriptionExamDAO.add(prescriptionExam) != null) {
                        // Added successfully
                        message.setError(JsonMessage.ERROR_NO_ERROR);
                        // Set the string based on the fact that is payed or not
                        String strPagamento = "<font color=\"red\"><b>non</b> pagato</font>";
                        if(prescriptionExam.getPaid()){
                            strPagamento = "<font color=\"green\">pagato</font>";
                        }
                        // Generate the html string for the email
                        String html =
                                "<h1 style=\"color: #5e9ca0;\">Nuova prescrizione <span style=\"color: #2b2301;\">esame</span>!</h1>" +
                                "<p>" +
                                    "Ciao <span style=\"color: #2b2301;\"><b>" + patient.getName() + "</b></span>!<br>" +
                                    "Il tuo medico di base ha aggiunto una prescrizione per te.<br>" +
                                    "Ecco qui un breve riassunto:<br>" +
                                    "<b>Esame</b>: <span style=\"color: #2e6c80;\">" + exam.getName() + "</span><br>" +
                                    "<b>Stato del pagamento</b>: " + strPagamento + "<br>" +
                                    "<br>" +
                                    "La preghiamo di contattarci al <b>"+ AppConfig.getInfoPhone() + "</b> per accordare un appuntamento<br>" +
                                "</p>";

                        EmailUtil.sendHTML(patient.getEmail(),
                                AppConfig.getName().toUpperCase() + " prescrizione esame",
                                html);
                    }
                }
            } catch (DAOException ex) {
                LOGGER.log(Level.SEVERE, "Unable to Prescribe an Exam", ex);
            }
        }

        return message.toJsonString();
    }
}
