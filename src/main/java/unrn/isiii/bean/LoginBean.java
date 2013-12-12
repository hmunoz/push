package unrn.isiii.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("loginBean")
@Scope("session")
public class LoginBean {

	private static final Logger logger = LoggerFactory
			.getLogger(LoginBean.class);

	@Autowired
	private AsistenciaBean asistenciaBean;

	private String username;

	private String password;

	private Boolean inLogin = false;

	private int count;

	public void loginmobile(ActionEvent actionEvent) {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		PushContext pushContext = PushContextFactory.getDefault()
				.getPushContext();

		setInLogin(true);
		getAsistenciaBean().add(getUsername());

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Login",
						"Bienvenido " + getUsername()));

		pushContext.push("/asistencia", getUsername());

		pushContext.push("/usuario", getUsername());
	}

	public synchronized void votar(Boolean valor) {

		getAsistenciaBean().votar();
		if (valor) {
			getAsistenciaBean().votarSi();
		} else {
			getAsistenciaBean().votarNo();
		}

		PushContext pushContext = PushContextFactory.getDefault()
				.getPushContext();
		pushContext.push("/wait" + getUsername(), "");

		if (getAsistenciaBean().votaronTodos()){
			pushContext.push("/resultado", getAsistenciaBean().getCountSi() + "/" +getAsistenciaBean().getCountNo());
		}
			

	}

	public AsistenciaBean getAsistenciaBean() {
		return asistenciaBean;
	}

	public void setAsistenciaBean(AsistenciaBean asistenciaBean) {
		this.asistenciaBean = asistenciaBean;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getInLogin() {
		return inLogin;
	}

	public void setInLogin(Boolean inLogin) {
		this.inLogin = inLogin;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
