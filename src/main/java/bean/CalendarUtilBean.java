package bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import util.CalendarUtil;

/**
 * Permits the access to web pages of the Calendar Util
 *
 * @see CalendarUtil
 * @author Carlo Corradini
 */
@ManagedBean(name = "calendar")
@RequestScoped
public class CalendarUtilBean extends CalendarUtil implements Serializable {

    private static final long serialVersionUID = 3267733896872815748L;

    /**
     * Set the Calendar by the Client Locale after all dependencies and
     * resources has been loaded
     */
    @PostConstruct
    public void init() {
        super.setCalendarByLocale(FacesContext.getCurrentInstance().getExternalContext().getRequestLocale());
    }

}